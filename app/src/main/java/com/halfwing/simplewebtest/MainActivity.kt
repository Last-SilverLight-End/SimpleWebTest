package com.halfwing.simplewebtest

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    private val webView: WebView by lazy {
        findViewById(R.id.webPageView)
    }
    private val addressBar: EditText by lazy {
        findViewById(R.id.addressPasteBar)
    }

    private val goHomeButton : ImageButton by lazy {
        findViewById(R.id.homeButton)
    }
    private val goBackButton : ImageButton by lazy {
        findViewById(R.id.backButton)
    }
    private val goFrontButton : ImageButton by lazy {
        findViewById(R.id.forwardButton)
    }

    private val refreshLayout : SwipeRefreshLayout by lazy {
        findViewById(R.id.refreshLayout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initViews()
        bindViews()
    }

    override fun onBackPressed() {
        if(webView.canGoBack())
        {
            webView.goBack()
        }else{
            super.onBackPressed()
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews() {
        webView.apply{
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(DEFAULT_URL)
        }
    }



    private fun bindViews() {
        goHomeButton.setOnClickListener {
            webView.loadUrl(DEFAULT_URL)
        }

        var addressPlus = ""
        addressBar.setOnEditorActionListener { addressInput, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                if (addressInput.text.toString().startsWith("https://www.")) {

                    if (addressInput.text.toString().endsWith(".com")) {
                        addressPlus = addressInput.text.toString()
                    } else {
                        addressPlus = addressInput.text.toString() + ".com"
                    }
                } else {
                    if (addressInput.text.toString().endsWith(".com")) {
                        addressPlus = "https://www." + addressInput.text.toString()
                    } else {
                        addressPlus = "https://www." + addressInput.text.toString() + ".com"
                    }
                }

                webView.loadUrl(addressPlus)
            }

            return@setOnEditorActionListener false
        }

        goBackButton.setOnClickListener{
            webView.goBack()
        }
        goFrontButton.setOnClickListener{
            webView.goForward()
        }

        refreshLayout.setOnRefreshListener {

            webView.reload()

        }
    }

    // 로딩 시작
    inner class WebViewClient : android.webkit.WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            if(refreshLayout.isRefreshing) {
                Toast.makeText(this@MainActivity, "로딩시작", Toast.LENGTH_SHORT).show()
            }
        }
        // 로딩 중일때 + 다른 url 넘어갈때
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {

            return super.shouldOverrideUrlLoading(view, request)
        }
        // 로딩 끝날시
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)


            refreshLayout.isRefreshing = false
        }
    }

    companion object{
        private const val DEFAULT_URL = "https://www.google.com"
    }

}



