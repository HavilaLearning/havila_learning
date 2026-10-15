package com.havila.tv

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

/**
 * Full-screen TV player. The player deliberately contains no WebView/browser UI,
 * channel website, download button, subscribe button or desktop-page chrome.
 * The selected channel is rendered as a small TV-style information overlay.
 */
class PlayerActivity : ComponentActivity() {
    private var player: ExoPlayer? = null
    private var playerView: PlayerView? = null
    private var channelIndex = -1
    private var liveChannels: List<Channel> = emptyList()
    private lateinit var root: FrameLayout
    private lateinit var info: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterImmersiveTvMode()

        val initialUrl = intent.getStringExtra("streamUrl")
        val initialName = intent.getStringExtra("channelName") ?: "Canal"
        val channelId = intent.getStringExtra("channelId")
        liveChannels = ChannelRepository.channels.filter { it.isLive && !it.streamUrl.isNullOrBlank() }

        // Resolve the selected channel by its repository ID rather than by the
        // position of the dashboard card. This keeps D-pad channel switching correct.
        channelIndex = channelId?.let { id -> liveChannels.indexOfFirst { it.id == id } } ?: -1

        if (initialUrl.isNullOrBlank()) {
            showUnavailable(initialName)
            return
        }

        // A website URL is intentionally NOT loaded here. Only direct media URLs
        // (HLS/DASH/other Media3-supported streams) are allowed into the TV player.
        if (!isDirectMediaUrl(initialUrl)) {
            showUnavailable(initialName)
            return
        }

        buildPlayer(initialUrl, initialName)
    }

    private fun buildPlayer(url: String, name: String) {
        root = FrameLayout(this).apply { setBackgroundColor(Color.BLACK) }

        playerView = PlayerView(this).apply {
            useController = false
            keepScreenOn = true
            setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
            isFocusable = false
            player = ExoPlayer.Builder(this@PlayerActivity).build().also { exo ->
                exo.setMediaItem(MediaItem.fromUri(Uri.parse(url)))
                exo.playWhenReady = true
                exo.prepare()
            }
        }
        player = playerView?.player as? ExoPlayer
        root.addView(playerView, FrameLayout.LayoutParams(-1, -1))

        // Discreet TV identification; no header/banner/navigation from the main UI.
        info = TextView(this).apply {
            text = "HAVILA TV  |  $name"
            setTextColor(Color.WHITE)
            textSize = 14f
            setBackgroundColor(Color.argb(150, 0, 0, 0))
            setPadding(20, 10, 20, 10)
            alpha = 0.86f
        }
        val infoParams = FrameLayout.LayoutParams(-2, -2).apply {
            leftMargin = 28
            topMargin = 28
        }
        root.addView(info, infoParams)
        setContentView(root)
    }

    private fun showUnavailable(name: String) {
        root = FrameLayout(this).apply { setBackgroundColor(Color.BLACK) }
        val message = TextView(this).apply {
            text = "HAVILA TV\n\n$name\n\nTransmissão direta indisponível para este canal."
            setTextColor(Color.WHITE)
            textSize = 18f
            gravity = android.view.Gravity.CENTER
            setPadding(48, 48, 48, 48)
        }
        root.addView(message, FrameLayout.LayoutParams(-1, -1))
        setContentView(root)
    }

    private fun isDirectMediaUrl(url: String): Boolean {
        val clean = url.substringBefore('?').lowercase()
        return clean.endsWith(".m3u8") || clean.endsWith(".mpd") || clean.endsWith(".mp4") || clean.endsWith(".ts")
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
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    finish()
                    return true
                }
                KeyEvent.KEYCODE_DPAD_LEFT,
                KeyEvent.KEYCODE_DPAD_DOWN,
                KeyEvent.KEYCODE_CHANNEL_DOWN -> {
                    switchChannel(-1)
                    return true
                }
                KeyEvent.KEYCODE_DPAD_RIGHT,
                KeyEvent.KEYCODE_DPAD_UP,
                KeyEvent.KEYCODE_CHANNEL_UP -> {
                    switchChannel(1)
                    return true
                }
                KeyEvent.KEYCODE_DPAD_CENTER,
                KeyEvent.KEYCODE_ENTER -> {
                    // OK does not expose a player UI; it simply keeps playback active.
                    player?.play()
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

        // Skip entries that point to websites instead of direct media streams.
        var attempts = 0
        while (attempts < liveChannels.size && !isDirectMediaUrl(liveChannels[next].streamUrl.orEmpty())) {
            next = next + direction
            if (next < 0) next = liveChannels.lastIndex
            if (next > liveChannels.lastIndex) next = 0
            attempts++
        }
        if (attempts >= liveChannels.size) return

        channelIndex = next
        val channel = liveChannels[next]
        val url = channel.streamUrl ?: return
        player?.release()
        player = ExoPlayer.Builder(this).build().also { exo ->
            exo.setMediaItem(MediaItem.fromUri(Uri.parse(url)))
            exo.playWhenReady = true
            exo.prepare()
            playerView?.player = exo
        }
        if (::info.isInitialized) info.text = "HAVILA TV  |  ${channel.name}"
    }

    override fun onStop() {
        super.onStop()
        player?.pause()
    }

    override fun onDestroy() {
        player?.release()
        player = null
        super.onDestroy()
    }
}
