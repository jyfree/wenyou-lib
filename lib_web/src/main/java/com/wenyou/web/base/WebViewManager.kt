package com.wenyou.web.base

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView

class WebViewManager private constructor() {
    companion object {
        @Volatile
        private var INSTANCE: WebViewManager? = null

        private fun instance() = INSTANCE ?: synchronized(this) {
            INSTANCE ?: WebViewManager().also { INSTANCE = it }
        }

        fun obtain(context: Context): WebView {
            return instance().obtain(context)
        }

        fun recycle(webView: WebView) {
            instance().recycle(webView)
        }

        fun destroy() {
            instance().destroy()
        }
    }

//    private val webViewCache: MutableList<WebView> = ArrayList(1)

    private fun obtain(context: Context): WebView {
//        if (webViewCache.isEmpty()) {
//            webViewCache.add(create(context))
//        }
//        val webView = webViewCache.removeAt(0)
//        webView.clearHistory()
        return create(context)
    }

    private fun create(context: Context): WebView {
        val webView = WebView(context)
        val webSetting = webView.settings


        webView.setBackgroundColor(Color.TRANSPARENT)
        webView.overScrollMode = WebView.OVER_SCROLL_NEVER

        webSetting.setAppCacheEnabled(true)
        webSetting.cacheMode = WebSettings.LOAD_DEFAULT
        webSetting.setGeolocationEnabled(true)
        webSetting.builtInZoomControls = false//隐藏缩放工具
        webSetting.defaultTextEncodingName = "utf-8"//内容编码
        webSetting.loadWithOverviewMode = true//自适应屏幕
        webSetting.useWideViewPort = true//设置此属性，可任意比例缩放
        webSetting.setSupportZoom(false) //设置可以支持缩放
        webSetting.textZoom = 100//适配系统字体大小
        //解决http和HTTPS混合URL问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        webSetting.domStorageEnabled = true//开启本地存储

        //以下为webView漏洞
        webSetting.allowFileAccess = false //是否需要使用 file 协议
        webSetting.allowFileAccessFromFileURLs = false
        webSetting.allowUniversalAccessFromFileURLs = false
        //关闭webview保存密码功能
        webSetting.savePassword = false
        //移除WebView组件系统隐藏接口(没有移除其中内置的searchBoxJavaBridge_，accessibility和accessibilityTraversal等导出接口, 可能导致远程代码任意执行
        webView.removeJavascriptInterface("searchBoxJavaBridge_")
        webView.removeJavascriptInterface("accessibility")
        webView.removeJavascriptInterface("accessibilityTraversal")

        return webView
    }

    private fun recycle(webView: WebView) {
        try {
            val parent = webView.parent
            if (parent != null) {
                (parent as ViewGroup).removeView(webView)
            }
            webView.stopLoading()
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.settings.javaScriptEnabled = false
            webView.clearHistory()
            webView.clearView()
            webView.removeAllViews()
            webView.destroy()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
//            if (!webViewCache.contains(webView)) {
//                webViewCache.add(webView)
//            }
        }
    }

    private fun destroy() {
        try {
//            webViewCache.forEach {
//                it.removeAllViews()
//                it.destroy()
//                webViewCache.remove(it)
//            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}