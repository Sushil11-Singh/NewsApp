package com.diagnal.newsapp.view.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.diagnal.newsapp.R


class NewsDetailActivity : AppCompatActivity() {
    private lateinit var ivBack: ImageView
    private lateinit var wvDetail: WebView
    private lateinit var progressBar: ProgressBar
    private var detailData : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        detailData = intent.getStringExtra("loadUrl")
        intLayout()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun intLayout() {
        ivBack = findViewById(R.id.ivBack)
        progressBar = findViewById(R.id.progressBar)
        wvDetail = findViewById(R.id.wvDetail)
        val webSettings: WebSettings = wvDetail.settings
        webSettings.javaScriptEnabled = true
        progressBar.visibility = ProgressBar.VISIBLE
        wvDetail.webViewClient = object : WebViewClient() {
            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                // Handle SSL errors (e.g., allow insecure connections)
                handler?.proceed()
            }
            override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                // Handle WebView errors
                Log.e("WebView", "Error loading URL: $failingUrl. Error code: $errorCode. Description: $description")
            }
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // Show progress bar when page starts loading
                progressBar.visibility = ProgressBar.VISIBLE
                wvDetail.visibility = ProgressBar.GONE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Hide progress bar when page finishes loading
                progressBar.visibility = ProgressBar.GONE
                wvDetail.visibility = ProgressBar.VISIBLE
            }
        }

        wvDetail.loadUrl(detailData!!)
        backClick()
    }

    private fun backClick() {
        ivBack.setOnClickListener {
            finish()
        }
    }
}