package com.havila.tv

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/** Full-screen TV player. A bad/offline stream must never close the application. */
class PlayerActivity : ComponentActivity() {
    private var player: ExoPlayer? = null
    private var playerView: PlayerView? = null
    private var channelIndex = -1
    private var liveChannels: List<Channel> = emptyList()
    private lateinit var root: FrameLayout
    private var resolveJob: Job? = null
    private val playerScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var currentChannelName = "Canal"
    private var playbackFailed = false
    private var changingChannel = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterImmersiveTvMode()
        liveChannels = ChannelRepository.channels.filter { it.isLive }
        openRequestedChannel(intent)
    }

    override fun onNewIntent(intent: android.content.Intent?) {
        super.onNewIntent(intent)
        if (intent == null) return
        setIntent(intent)
        openRequestedChannel(intent)
    }

    private fun openRequestedChannel(request: android.content.Intent) {
        val channelId = request.getStringExtra("channelId")
        val initialUrl = request.getStringExtra("streamUrl")
        val initialName = request.getStringExtra("channelName") ?: "Canal"

        val index = channelId?.let { id -> liveChannels.indexOfFirst { it.id == id } } ?: -1
        if (index >= 0) {
            channelIndex = index
            resolveAndPlay(liveChannels[index])
        } else if (!initialUrl.isNullOrBlank() && isSafeMediaUrl(initialUrl)) {
            channelIndex = -1
            resolveJob?.cancel()
            buildPlayer(initialUrl, initialName)
        } else {
            channelIndex = -1
            resolveJob?.cancel()
            showUnavailable(initialName, "Canal não encontrado.")
        }
    }

    private fun resolveAndPlay(channel: Channel) {
        currentChannelName = channel.name
        playbackFailed = false
        changingChannel = true
        showLoading(channel.name)
        resolveJob?.cancel()
        resolveJob = playerScope.launch {
            try {
                val direct = PlaylistResolver.resolve(channel)
                if (isFinishing || isDestroyed) return@launch
                if (direct != null && isSafeMediaUrl(direct)) {
                    buildPlayer(direct, channel.name)
                } else {
                    showUnavailable(channel.name, "Não foi encontrada uma transmissão disponível para este canal.")
                }
            } catch (_: Throwable) {
                if (!isFinishing && !isDestroyed) {
                    showUnavailable(channel.name, "Não foi possível preparar este canal.")
                }
            } finally {
                changingChannel = false
            }
        }
    }

    private fun buildPlayer(url: String, name: String) {
        if (!isSafeMediaUrl(url)) {
            showUnavailable(name, "O endereço da transmissão não é válido.")
            return
        }

        try {
            playbackFailed = false
            player?.release()
            player = null
            playerView = null

            root = FrameLayout(this).apply { setBackgroundColor(Color.BLACK) }
            val newPlayer = ExoPlayer.Builder(this).build()
            newPlayer.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_READY) playbackFailed = false
                }

                override fun onPlayerError(error: PlaybackException) {
                    if (!isFinishing && !isDestroyed) {
                        playbackFailed = true
                        showPlaybackError(name)
                    }
                }
            })
            newPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(url)))
            newPlayer.playWhenReady = true
            player = newPlayer

            playerView = PlayerView(this).apply {
                useController = false
                keepScreenOn = true
                setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
                isFocusable = false
                player = newPlayer
            }
            root.addView(playerView, FrameLayout.LayoutParams(-1, -1))

            val info = TextView(this).apply {
                text = "HAVILA TV  |  $name"
                setTextColor(Color.WHITE)
                textSize = 14f
                setBackgroundColor(Color.argb(150, 0, 0, 0))
                setPadding(20, 10, 20, 10)
                alpha = 0.86f
            }
            root.addView(info, FrameLayout.LayoutParams(-2, -2).apply {
                leftMargin = 28
                topMargin = 28
            })

            setContentView(root)
            enterImmersiveTvMode()
            newPlayer.prepare()
        } catch (_: Throwable) {
            player?.release()
            player = null
            playerView = null
            if (!isFinishing && !isDestroyed) showPlaybackError(name)
        }
    }

    private fun showLoading(name: String) {
        releasePlayerOnly()
        root = FrameLayout(this).apply { setBackgroundColor(Color.BLACK) }
        val message = TextView(this).apply {
            text = "HAVILA TV\n\n$name\n\nA procurar a transmissão..."
            setTextColor(Color.WHITE)
            textSize = 18f
            gravity = Gravity.CENTER
            setPadding(48, 48, 48, 48)
            isFocusable = true
            isFocusableInTouchMode = true
            requestFocus()
        }
        root.addView(message, FrameLayout.LayoutParams(-1, -1))
        setContentView(root)
        enterImmersiveTvMode()
    }

    private fun showUnavailable(name: String, reason: String) {
        releasePlayerOnly()
        playbackFailed = true
        root = FrameLayout(this).apply { setBackgroundColor(Color.BLACK) }
        val message = TextView(this).apply {
            text = "HAVILA TV\n\n$name\n\n$reason\n\nOK  Tentar novamente\nBACK  Voltar"
            setTextColor(Color.WHITE)
            textSize = 18f
            gravity = Gravity.CENTER
            setPadding(48, 48, 48, 48)
            isFocusable = true
            isFocusableInTouchMode = true
            requestFocus()
        }
        root.addView(message, FrameLayout.LayoutParams(-1, -1))
        setContentView(root)
        enterImmersiveTvMode()
    }

    private fun showPlaybackError(name: String) {
        releasePlayerOnly()
        playbackFailed = true
        root = FrameLayout(this).apply { setBackgroundColor(Color.BLACK) }
        val message = TextView(this).apply {
            text = "HAVILA TV\n\n$name\n\nNão foi possível reproduzir este canal.\n\nOK  Tentar novamente\nBACK  Voltar"
            setTextColor(Color.WHITE)
            textSize = 18f
            gravity = Gravity.CENTER
            setPadding(48, 48, 48, 48)
            isFocusable = true
            isFocusableInTouchMode = true
            requestFocus()
        }
        root.addView(message, FrameLayout.LayoutParams(-1, -1))
        setContentView(root)
        enterImmersiveTvMode()
    }

    private fun isSafeMediaUrl(url: String): Boolean {
        return try {
            val uri = Uri.parse(url.trim())
            val scheme = uri.scheme?.lowercase()
            val path = uri.path?.lowercase().orEmpty()
            val clean = url.substringBefore('?').lowercase()
            (scheme == "http" || scheme == "https") &&
                uri.host != null &&
                (path.endsWith(".m3u8") || path.endsWith(".mpd") || path.endsWith(".mp4") || path.endsWith(".ts") || clean.endsWith(".m3u8") || clean.endsWith(".mpd"))
        } catch (_: Throwable) {
            false
        }
    }

    private fun releasePlayerOnly() {
        player?.release()
        player = null
        playerView = null
    }

    private fun enterImmersiveTvMode() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN && event.repeatCount == 0) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    finish()
                    return true
                }
                KeyEvent.KEYCODE_DPAD_LEFT,
                KeyEvent.KEYCODE_DPAD_DOWN,
                KeyEvent.KEYCODE_CHANNEL_DOWN -> {
                    if (!playbackFailed && !changingChannel) switchChannel(-1) else if (playbackFailed) finish()
                    return true
                }
                KeyEvent.KEYCODE_DPAD_RIGHT,
                KeyEvent.KEYCODE_DPAD_UP,
                KeyEvent.KEYCODE_CHANNEL_UP -> {
                    if (!playbackFailed && !changingChannel) switchChannel(1) else if (playbackFailed) finish()
                    return true
                }
                KeyEvent.KEYCODE_DPAD_CENTER,
                KeyEvent.KEYCODE_ENTER -> {
                    if (playbackFailed && channelIndex in liveChannels.indices) {
                        resolveAndPlay(liveChannels[channelIndex])
                    } else {
                        player?.play()
                    }
                    return true
                }
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
