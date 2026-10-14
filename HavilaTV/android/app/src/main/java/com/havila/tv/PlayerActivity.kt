package com.havila.tv

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity

class PlayerActivity : ComponentActivity() {
    private var webView: WebView? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = intent.getStringExtra("streamUrl")
        val channel = intent.getStringExtra("channelName") ?: "Havila TV"

        if (url.isNullOrBlank()) {
            finish()
            return
        }

        webView = WebView(this).apply {
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.mediaPlaybackRequiresUserGesture = false
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            settings.userAgentString = WebSettings.getDefaultUserAgent(this@PlayerActivity)
            contentDescription = channel
            loadUrl(url)
        }
        setContentView(webView)
    }

    override fun onBackPressed() {
        if (webView?.canGoBack() == true) webView?.goBack() else super.onBackPressed()
    }

    override fun onDestroy() {
        webView?.destroy()
        webView = null
        super.onDestroy()
    }
}
