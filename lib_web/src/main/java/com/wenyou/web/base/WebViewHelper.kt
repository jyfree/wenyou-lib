package com.wenyou.web.base

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.wenyou.web.utils.WebLogUtils

class WebViewHelper private constructor(
    parent: ViewGroup,
    private val shouldOverrideUrlLoading: ((view: WebView, url: String) -> Boolean)? = null
) {
    companion object {
        fun with(
            parent: ViewGroup,
            shouldOverrideUrlLoading: ((view: WebView, url: String) -> Boolean)? = null
        ): WebViewHelper {
            return WebViewHelper(parent, shouldOverrideUrlLoading)
        }
    }

    val webView = WebViewManager.obtain(parent.context)

    private var onPageChangedListener: OnPageChangedListener? = null

    private var originalUrl = "about:blank"

    init {
        parent.addView(
            webView, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request?.url
                } else {
                    null
                }
                if (view != null && uri != null) {
                    if (!("http" == uri.scheme || "https" == uri.scheme)) {
                        try {
                            view.context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        return true
                    } else {
                        overrideUrlLoading(view, uri.toString())
                    }
                }
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                WebLogUtils.i("webView", "onPageStarted--url${url}")
                onPageChangedListener?.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                WebLogUtils.i("webView", "onPageFinished--url${url}")
                onPageChangedListener?.onPageFinished(view, url)
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                onPageChangedListener?.onLoadResource(view, url)
            }
        }

        webView.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                onPageChangedListener?.onProgressChanged(view, newProgress)
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                onPageChangedListener?.onReceivedTitle(view, title)
            }

        }

        webView.setDownloadListener { url, _, _, _, _ ->
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                webView.context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        webView.setOnLongClickListener {
            val result = webView.hitTestResult
            when (result.type) {
                WebView.HitTestResult.IMAGE_TYPE, WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE -> {
                    println(result.extra)
                    true
                }
                else -> false
            }
        }
    }

    private fun overrideUrlLoading(view: WebView, url: String): Boolean {
        return shouldOverrideUrlLoading?.invoke(view, url) ?: false
    }

    fun setOnPageChangedListener(onPageChangedListener: OnPageChangedListener?): WebViewHelper {
        this.onPageChangedListener = onPageChangedListener
        return this
    }

    fun loadUrl(url: String) {
        webView.loadUrl(url)
        originalUrl = url
    }

    fun canGoBack(): Boolean {
        val canBack = webView.canGoBack()
        if (canBack) webView.goBack()
        val backForwardList = webView.copyBackForwardList()
        val currentIndex = backForwardList.currentIndex
        if (currentIndex == 0) {
            val currentUrl = backForwardList.currentItem?.url
            val currentHost = Uri.parse(currentUrl).host
            //栈底不是链接则直接返回
            if (currentHost.isNullOrBlank()) return false
            //栈底链接不是原始链接则直接返回
            if (originalUrl != currentUrl) return false
        }
        return canBack
    }

    fun canGoForward(): Boolean {
        val canForward = webView.canGoForward()
        if (canForward) webView.goForward()
        return canForward
    }

    fun reload() {
        webView.reload()
    }


    fun onResume() {
        webView.onResume()
    }

    fun onPause() {
        webView.onPause()
    }

    fun onDestroyView() {
        WebViewManager.recycle(webView)
    }
}