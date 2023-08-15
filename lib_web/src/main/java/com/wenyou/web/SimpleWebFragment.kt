package com.wenyou.web

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.wenyou.web.base.OnPageChangedListener
import com.wenyou.web.base.WebViewHelper
import com.wenyou.web.utils.WebLogUtils

/**
 * webView实现
 * 注意！！
 * 若继承此类，view对应的id必需为：
 * webContainer =>R.id.webViewContainer
 * progressBar => R.id.webViewProgressBar
 */
open class SimpleWebFragment : Fragment() {
    companion object {
        private const val TAG = "WebFragment"
    }

    lateinit var jsInterface: JavaScriptInterface
    var url: String? = null
    var webViewHelper: WebViewHelper? = null
    var webContainer: FrameLayout? = null
    var progressBar: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view, savedInstanceState)
    }

    open fun getLayoutId(): Int {
        return R.layout.simple_fragment_webview
    }

    open fun initUI(view: View?, savedInstanceState: Bundle?) {
        webContainer = view?.findViewById(R.id.webViewContainer)
        progressBar = view?.findViewById(R.id.webViewProgressBar)
        if (webContainer == null || progressBar == null) {
            throw Exception("webContainer and progressBar id have to R.id.webViewContainer and R.id.webViewProgressBar")
        }
        webViewHelper = WebViewHelper.with(webContainer!!)
            .setOnPageChangedListener(object : OnPageChangedListener {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                }

                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    if (newProgress == 100) {
                        progressBar?.visibility = View.GONE
                    } else {
                        progressBar?.visibility = View.VISIBLE
                        progressBar?.progress = newProgress
                    }
                }

                override fun onReceivedTitle(view: WebView?, title: String?) {
                    onWebViewReceivedTitle(view, title)
                }

                override fun onLoadResource(view: WebView?, url: String?) {

                }

            })

        url = arguments?.getString("url")
        url?.let {
            addJsInterface()
            webViewHelper?.loadUrl(Uri.decode(it))
        }
        if (url == null) {
            throw Exception("arguments url is null")
        }
        WebLogUtils.i(TAG, "url = $url")
    }

    open fun onWebViewReceivedTitle(view: WebView?, title: String?) {

    }

    fun onBackPress(): Boolean {
        return webViewHelper?.canGoBack() ?: false
    }

    override fun onResume() {
        super.onResume()
        webViewHelper?.onResume()
    }

    override fun onPause() {
        super.onPause()
        webViewHelper?.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        webViewHelper?.onDestroyView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    open fun addJsInterface() {
        webViewHelper?.let {
            //js接口
            jsInterface = JavaScriptInterface(it.webView)
            it.webView.addJavascriptInterface(jsInterface, "AndroidJSInterface")
            it.webView.settings.javaScriptEnabled = true//支持javaScript
        }
    }
}