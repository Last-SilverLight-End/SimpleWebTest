package com.halfwing.simplewebtest

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton

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
    }

    companion object{
        private const val DEFAULT_URL = "https://www.google.com"
    }

}



