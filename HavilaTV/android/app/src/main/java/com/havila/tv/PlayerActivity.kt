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

/** Full-screen TV player. Only direct media streams are played. */
class PlayerActivity : ComponentActivity() {
    private var player: ExoPlayer? = null
    private var playerView: PlayerView? = null
    private var channelIndex = -1
    private var liveChannels: List<Channel> = emptyList()
    private lateinit var root: FrameLayout
    private var resolveJob: Job? = null
    private val playerScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var currentChannelName = "Canal"
    private var currentUrl: String? = null
    private var playbackFailed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterImmersiveTvMode()

        val channelId = intent.getStringExtra("channelId")
        val initialUrl = intent.getStringExtra("streamUrl")
        val initialName = intent.getStringExtra("channelName") ?: "Canal"
        liveChannels = ChannelRepository.channels.filter { it.isLive }
        channelIndex = channelId?.let { id -> liveChannels.indexOfFirst { it.id == id } } ?: -1

        val channel = channelId?.let { id -> liveChannels.firstOrNull { it.id == id } }
        if (channel != null) {
            resolveAndPlay(channel)
        } else if (!initialUrl.isNullOrBlank() && PlaylistResolver.isDirectMediaUrl(initialUrl)) {
            buildPlayer(initialUrl, initialName)
        } else {
            showUnavailable(initialName, "Canal não encontrado.")
        }
    }

    private fun resolveAndPlay(channel: Channel) {
        currentChannelName = channel.name
        playbackFailed = false
        showLoading(channel.name)
        resolveJob?.cancel()
        resolveJob = playerScope.launch {
            try {
                val direct = PlaylistResolver.resolve(channel)
                if (isFinishing || isDestroyed) return@launch
                if (direct != null) {
                    buildPlayer(direct, channel.name)
                } else {
                    showUnavailable(channel.name, "Não foi encontrada uma transmissão direta disponível.")
                }
            } catch (_: Exception) {
                if (!isFinishing && !isDestroyed) {
                    showUnavailable(channel.name, "Não foi possível preparar este canal.")
                }
            }
        }
    }

    private fun buildPlayer(url: String, name: String) {
        if (!isSafeMediaUrl(url)) {
            showUnavailable(name, "O endereço da transmissão não é válido.")
            return
        }

        try {
            currentUrl = url
            playbackFailed = false
            player?.release()
            player = null

            root = FrameLayout(this).apply { setBackgroundColor(Color.BLACK) }
            val newPlayer = ExoPlayer.Builder(this).build()
            newPlayer.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_READY) {
                        playbackFailed = false
                    }
                }

                override fun onPlayerError(error: PlaybackException) {
                    playbackFailed = true
                    showPlaybackError(name)
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
        } catch (_: Exception) {
            player?.release()
            player = null
            showPlaybackError(name)
        }
    }

    private fun showLoading(name: String) {
        root = FrameLayout(this).apply { setBackgroundColor(Color.BLACK) }
        val message = TextView(this).apply {
            text = "HAVILA TV\n\n$name\n\nA procurar a transmissão..."
            setTextColor(Color.WHITE)
            textSize = 18f
            gravity = Gravity.CENTER
            setPadding(48, 48, 48, 48)
        }
        root.addView(message, FrameLayout.LayoutParams(-1, -1))
        setContentView(root)
        enterImmersiveTvMode()
    }

    private fun showUnavailable(name: String, reason: String) {
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
            val uri = Uri.parse(url)
            val scheme = uri.scheme?.lowercase()
            (scheme == "http" || scheme == "https") && PlaylistResolver.isDirectMediaUrl(url)
        } catch (_: Exception) {
            false
        }
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
                    if (!playbackFailed) switchChannel(-1) else finish()
                    return true
                }
                KeyEvent.KEYCODE_DPAD_RIGHT,
                KeyEvent.KEYCODE_DPAD_UP,
                KeyEvent.KEYCODE_CHANNEL_UP -> {
                    if (!playbackFailed) switchChannel(1) else finish()
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
        if (liveChannels.isEmpty()) return
        var next = if (channelIndex < 0) 0 else channelIndex + direction
        if (next < 0) next = liveChannels.lastIndex
        if (next > liveChannels.lastIndex) next = 0
        channelIndex = next
        resolveAndPlay(liveChannels[next])
    }

    override fun onStop() {
        super.onStop()
        player?.pause()
    }

    override fun onDestroy() {
        resolveJob?.cancel()
        playerScope.cancel()
        player?.release()
        player = null
        playerView = null
        super.onDestroy()
    }
}
