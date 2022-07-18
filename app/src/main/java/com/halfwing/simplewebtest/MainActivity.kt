package com.halfwing.simplewebtest

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private val webView: WebView by lazy {
        findViewById(R.id.webPageView)
    }
    private val addressBar: EditText by lazy {
        findViewById(R.id.addressPasteBar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initViews()
        bindViews()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews() {
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://www.google.com/")
    }

    private fun bindViews() {
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
    }

}



