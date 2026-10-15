package com.havila.tv

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class PlayerActivity : ComponentActivity() {
    private var player: ExoPlayer? = null
    private var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        val url = intent.getStringExtra("streamUrl")
        val name = intent.getStringExtra("channelName") ?: "Canal"
        if (url.isNullOrBlank()) { finish(); return }

        // Direct HLS/DASH files use the native TV player. Official live pages use an
        // immersive WebView without browser chrome, so the user never sees a desktop browser.
        if (url.contains(".m3u8", true) || url.contains(".mpd", true)) {
            openNativeStream(url)
        } else {
            openOfficialLivePage(url, name)
        }
    }

    private fun openNativeStream(url: String) {
        val playerView = PlayerView(this).apply {
            useController = false
            keepScreenOn = true
            setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
            isFocusable = false
        }
        player = ExoPlayer.Builder(this).build().also { exo ->
            exo.setMediaItem(MediaItem.fromUri(Uri.parse(url)))
            exo.playWhenReady = true
            playerView.player = exo
            exo.prepare()
        }
        setContentView(playerView)
    }

    private fun openOfficialLivePage(url: String, name: String) {
        val root = FrameLayout(this).apply { setBackgroundColor(Color.BLACK) }
        val view = WebView(this).apply {
            setBackgroundColor(Color.BLACK)
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.mediaPlaybackRequiresUserGesture = false
            settings.loadsImagesAutomatically = true
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            settings.userAgentString = "Mozilla/5.0 (Linux; Android TV) AppleWebKit/537.36 Chrome/120 Safari/537.36"
            isFocusable = true
            isFocusableInTouchMode = true
        }
        webView = view
        root.addView(view, FrameLayout.LayoutParams(-1, -1))
        setContentView(root)
        view.loadUrl(url)
    }

    override fun onBackPressed() {
        if (webView?.canGoBack() == true) webView?.goBack() else finish()
    }

    override fun onStop() {
        super.onStop()
        player?.pause()
    }

    override fun onDestroy() {
        webView?.apply { stopLoading(); destroy() }
        webView = null
        player?.release()
        player = null
        super.onDestroy()
    }
}
