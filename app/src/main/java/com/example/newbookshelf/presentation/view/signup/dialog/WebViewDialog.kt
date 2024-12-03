package com.example.newbookshelf.presentation.view.signup.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import com.example.newbookshelf.R
import com.example.newbookshelf.databinding.FragmentWebViewDialogBinding

class WebViewDialog(private val link: String) : DialogFragment() {
    private lateinit var binding: FragmentWebViewDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_web_view_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWebViewDialogBinding.bind(view)

        init()
        loadWebView(link)
    }

    private fun init() = with(binding){
        val window = dialog?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = Color.WHITE
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebView(url:String) = with(binding){
        webView.apply {
            webViewClient = WebViewClient()
            settings.domStorageEnabled = true
            scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_INSET
            settings.allowFileAccess = true
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.setSupportZoom(true)
            settings.builtInZoomControls = false

            settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            settings.setEnableSmoothTransition(true)
            settings.cacheMode = WebSettings.LOAD_NO_CACHE

            setLayerType(View.LAYER_TYPE_HARDWARE, null)

            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    return false
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    pgWebLoding.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    pgWebLoding.visibility = View.GONE
                }
            }

            loadUrl(url)
        }
    }
}