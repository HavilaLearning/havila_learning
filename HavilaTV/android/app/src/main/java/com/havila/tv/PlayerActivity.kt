package com.havila.tv

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/** Full-screen TV player. Stream errors are contained and never close the application. */
class PlayerActivity : ComponentActivity() {
    private var player: ExoPlayer? = null
    private var playerView: PlayerView? = null
    private var channelIndex = -1
    private var liveChannels: List<Channel> = emptyList()
    private var resolveJob: Job? = null
    private val playerScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var playbackFailed = false
    private var changingChannel = false
    private var currentChannelName = "Canal"
    private var currentStreamUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterImmersiveTvMode()
        liveChannels = ChannelRepository.channels.filter(::hasPlayableSource)
        openRequestedChannel(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent == null || isFinishing || isDestroyed) return
        setIntent(intent)
        openRequestedChannel(intent)
    }

    private fun openRequestedChannel(request: Intent) {
        val channelId = request.getStringExtra("channelId")?.trim()
        val initialUrl = request.getStringExtra("streamUrl")?.trim()
        val initialName = request.getStringExtra("channelName")?.trim().takeUnless { it.isNullOrBlank() } ?: "Canal"
        val index = channelId?.let { id -> liveChannels.indexOfFirst { it.id == id } } ?: -1

        when {
            index >= 0 -> {
                channelIndex = index
                resolveAndPlay(liveChannels[index])
            }
            !initialUrl.isNullOrBlank() && PlaylistResolver.isDirectMediaUrl(initialUrl) -> {
                channelIndex = -1
                currentChannelName = initialName
                currentStreamUrl = initialUrl
                resolveJob?.cancel()
                buildPlayer(initialUrl, initialName)
            }
            else -> {
                channelIndex = -1
                resolveJob?.cancel()
                showUnavailable(initialName, "Canal não encontrado.")
            }
        }
    }

    private fun resolveAndPlay(channel: Channel) {
        if (isFinishing || isDestroyed) return
        playbackFailed = false
        changingChannel = true
        currentChannelName = channel.name
        currentStreamUrl = null
        showLoading(channel.name)
        resolveJob?.cancel()
        resolveJob = playerScope.launch {
            try {
                val direct = PlaylistResolver.resolve(channel)
                if (isFinishing || isDestroyed) return@launch
                if (direct != null && PlaylistResolver.isDirectMediaUrl(direct)) {
                    currentStreamUrl = direct
                    buildPlayer(direct, channel.name)
                } else {
                    showUnavailable(channel.name, "Não foi encontrada uma transmissão disponível para este canal.")
                }
            } catch (_: Throwable) {
                if (!isFinishing && !isDestroyed) showUnavailable(channel.name, "Não foi possível preparar este canal.")
            } finally {
                changingChannel = false
            }
        }
    }

    private fun buildPlayer(url: String, name: String) {
        val safeUrl = url.trim()
        if (!PlaylistResolver.isDirectMediaUrl(safeUrl) || isFinishing || isDestroyed) {
            showUnavailable(name, "O endereço da transmissão não é válido.")
            return
        }
        try {
            playbackFailed = false
            changingChannel = false
            releasePlayerOnly()
            val dataSourceFactory = DefaultHttpDataSource.Factory()
                .setConnectTimeoutMs(12_000)
                .setReadTimeoutMs(20_000)
                .setUserAgent("HavilaTV/1.0 AndroidTV")
            val uri = Uri.parse(safeUrl)
            val mediaItem = MediaItem.Builder().setUri(uri).apply {
                when (detectMimeType(safeUrl)) {
                    MimeTypes.APPLICATION_M3U8 -> setMimeType(MimeTypes.APPLICATION_M3U8)
                    MimeTypes.APPLICATION_MPD -> setMimeType(MimeTypes.APPLICATION_MPD)
                }
            }.build()
            val newPlayer = ExoPlayer.Builder(this).build()
            newPlayer.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_READY) playbackFailed = false
                }
                override fun onPlayerError(error: PlaybackException) {
                    if (!isFinishing && !isDestroyed) showPlaybackError(name)
                }
            })
            newPlayer.setMediaSource(createMediaSource(mediaItem, dataSourceFactory))
            newPlayer.playWhenReady = true

            val newPlayerView = PlayerView(this).apply {
                useController = false
                keepScreenOn = true
                setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
                isFocusable = false
                resizeMode = androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT
                player = newPlayer
            }
            val root = FrameLayout(this).apply { setBackgroundColor(Color.BLACK) }
            root.addView(newPlayerView, FrameLayout.LayoutParams(-1, -1))
            val info = TextView(this).apply {
                text = "HAVILA TV  |  $name"
                setTextColor(Color.WHITE)
                textSize = 14f
                setBackgroundColor(Color.argb(150, 0, 0, 0))
                setPadding(20, 10, 20, 10)
            }
            root.addView(info, FrameLayout.LayoutParams(-2, -2).apply { leftMargin = 28; topMargin = 28 })
            player = newPlayer
            playerView = newPlayerView
            setContentView(root)
            enterImmersiveTvMode()
            newPlayer.prepare()
        } catch (_: Throwable) {
            releasePlayerOnly()
            if (!isFinishing && !isDestroyed) showPlaybackError(name)
        }
    }

    private fun detectMimeType(url: String): String? {
        val clean = url.lowercase().substringBefore('?').substringBefore('#')
        return when {
            clean.endsWith(".m3u8") -> MimeTypes.APPLICATION_M3U8
            clean.endsWith(".mpd") -> MimeTypes.APPLICATION_MPD
            else -> null
        }
    }

    private fun createMediaSource(item: MediaItem, factory: DefaultHttpDataSource.Factory): MediaSource {
        return when (item.mimeType) {
            MimeTypes.APPLICATION_M3U8 -> HlsMediaSource.Factory(factory).createMediaSource(item)
            MimeTypes.APPLICATION_MPD -> DashMediaSource.Factory(factory).createMediaSource(item)
            else -> androidx.media3.exoplayer.source.DefaultMediaSourceFactory(factory).createMediaSource(item)
        }
    }

    private fun showLoading(name: String) {
        releasePlayerOnly()
        showMessage("HAVILA TV\n\n$name\n\nA procurar a transmissão...", false)
    }

    private fun showUnavailable(name: String, reason: String) {
        changingChannel = false
        playbackFailed = true
        showMessage("HAVILA TV\n\n$name\n\n$reason\n\nOK  Tentar novamente\nBACK  Voltar", true)
    }

    private fun showPlaybackError(name: String) {
        releasePlayerOnly()
        changingChannel = false
        playbackFailed = true
        showMessage("HAVILA TV\n\n$name\n\nNão foi possível reproduzir este canal.\n\nOK  Tentar novamente\nBACK  Voltar", true)
    }

    private fun showMessage(text: String, focusable: Boolean) {
        val message = TextView(this).apply {
            this.text = text
            setTextColor(Color.WHITE)
            textSize = 18f
            gravity = Gravity.CENTER
            setPadding(48, 48, 48, 48)
            isFocusable = focusable
            isFocusableInTouchMode = focusable
            if (focusable) post { requestFocus() }
        }
        val root = FrameLayout(this).apply { setBackgroundColor(Color.BLACK) }
        root.addView(message, FrameLayout.LayoutParams(-1, -1))
        setContentView(root)
        enterImmersiveTvMode()
    }

    private fun releasePlayerOnly() {
        player?.release()
        player = null
        playerView = null
    }

    private fun enterImmersiveTvMode() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action != KeyEvent.ACTION_DOWN || event.repeatCount != 0) return super.dispatchKeyEvent(event)
        when (event.keyCode) {
            KeyEvent.KEYCODE_BACK -> { finish(); return true }
            KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_CHANNEL_DOWN -> {
                if (!changingChannel && !playbackFailed) switchChannel(-1) else if (playbackFailed) finish()
                return true
            }
            KeyEvent.KEYCODE_DPAD_RIGHT, KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_CHANNEL_UP -> {
                if (!changingChannel && !playbackFailed) switchChannel(1) else if (playbackFailed) finish()
                return true
            }
            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                if (playbackFailed && channelIndex in liveChannels.indices) resolveAndPlay(liveChannels[channelIndex])
                else if (!changingChannel) player?.play()
                return true
            }
        }
        return super.dispatchKeyEvent(event)
    }

    private fun switchChannel(direction: Int) {
        if (liveChannels.isEmpty() || changingChannel) return
        var next = if (channelIndex < 0) 0 else channelIndex + direction
        if (next < 0) next = liveChannels.lastIndex
        if (next > liveChannels.lastIndex) next = 0
        channelIndex = next
        resolveAndPlay(liveChannels[next])
    }

    private fun hasPlayableSource(channel: Channel): Boolean =
        channel.streamUrl?.let(PlaylistResolver::isDirectMediaUrl) == true || channel.playlistUrls.isNotEmpty()

    override fun onStop() {
        super.onStop()
        if (!isChangingConfigurations) player?.pause()
    }

    override fun onDestroy() {
        resolveJob?.cancel()
        playerScope.cancel()
        releasePlayerOnly()
        super.onDestroy()
    }
}