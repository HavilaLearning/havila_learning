package com.havila.tv

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class PlayerActivity : ComponentActivity() {
    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = intent.getStringExtra("streamUrl")
        val channel = intent.getStringExtra("channelName") ?: "Havila TV"
        if (url.isNullOrBlank()) {
            finish()
            return
        }

        val playerView = PlayerView(this).apply {
            useController = true
            controllerShowTimeoutMs = 3500
            controllerHideOnTouch = true
            contentDescription = channel
            setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
            keepScreenOn = true
            systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }

        player = ExoPlayer.Builder(this).build().also { exo ->
            exo.setMediaItem(MediaItem.fromUri(Uri.parse(url)))
            exo.playWhenReady = true
            exo.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_ENDED) exo.seekTo(0)
                }
            })
            playerView.player = exo
            exo.prepare()
        }
        setContentView(playerView)
    }

    override fun onStop() {
        super.onStop()
        player?.pause()
    }

    override fun onDestroy() {
        playerViewCleanup()
        super.onDestroy()
    }

    private fun playerViewCleanup() {
        player?.release()
        player = null
    }
}
