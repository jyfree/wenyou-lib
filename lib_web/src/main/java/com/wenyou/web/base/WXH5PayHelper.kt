package com.wenyou.web.base

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.wenyou.web.WebConstants
import com.wenyou.web.utils.WebLogUtils

/**
 * 微信H5支付
 * @param webContainer 装载webView的view容器
 * @param wxReferer h5支付的Referer头
 * @param redirectUrl h5支付回调地址
 * @param wxWebPayUrl shouldOverrideUrlLoading判断该URL是否需要重置loadUrl，默认为：WebConstants.IS_WX_WEB_PAY_URL
 */
@SuppressLint("SetJavaScriptEnabled")
class WXH5PayHelper(
    webContainer: ViewGroup,
    private val wxReferer: String,
    private val redirectUrl: String,
    private val wxWebPayUrl: String
) {
    companion object {
        fun with(
            webContainer: ViewGroup,
            wxReferer: String,
            redirectUrl: String,
            wxWebPayUrl: String = WebConstants.IS_WX_WEB_PAY_URL
        ): WXH5PayHelper {
            return WXH5PayHelper(webContainer, wxReferer, redirectUrl, wxWebPayUrl)
        }
    }

    var webViewHelper: WebViewHelper? = null
    private var mUrl: String? = null

    init {
        webViewHelper = WebViewHelper.with(
            webContainer,
            shouldOverrideUrlLoading = { view: WebView, url: String ->
                if (url.contains(wxWebPayUrl)) {
                    val extraHeaders: MutableMap<String, String> = HashMap()
                    extraHeaders["Referer"] = wxReferer
                    view.loadUrl(url, extraHeaders)
                    WebLogUtils.i("shouldOverrideUrlLoading--加载url（微信）", url)
                } else if (mUrl != url) {
                    mUrl = url
                    view.loadUrl(url)
                    WebLogUtils.i("shouldOverrideUrlLoading--加载url", url)
                }
                true
            })
            .setOnPageChangedListener(object : OnPageChangedListener {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                }

                override fun onProgressChanged(view: WebView?, newProgress: Int) {

                }

                override fun onReceivedTitle(view: WebView?, title: String?) {
                }

                override fun onLoadResource(view: WebView?, url: String?) {

                }

            })
        webViewHelper?.webView?.apply {
            visibility = View.GONE
            settings?.javaScriptEnabled = true//支持javaScript
            settings?.setSupportMultipleWindows(true)
        }
    }

    /**
     * 注意！！payUrl参数为：微信H5下单接口返回的url
     * 即通过app下单后，服务器返回订单url，最后利用webView调起微信H5
     * @param payUrl 支付订单url
     */
    fun pay(payUrl: String) {
        webViewHelper?.webView?.loadData(
            "<script type=\"text/javascript\">\n" +
                    "    window.location.href=\"${payUrl}&redirect_url=${redirectUrl}\";\n" +
                    "</script>", "text/html", "utf-8"
        )
    }

    fun onResume() {
        webViewHelper?.onResume()
    }

    fun onPause() {
        webViewHelper?.onPause()
    }

    fun onDestroyView() {
        webViewHelper?.onDestroyView()
    }
}