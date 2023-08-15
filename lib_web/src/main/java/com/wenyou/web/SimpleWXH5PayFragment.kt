package com.wenyou.web

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.wenyou.web.base.WXH5PayHelper
import com.wenyou.web.utils.WebLogUtils


/**
 * 微信H5支付的封装
 * 注意！！
 * 若继承此类，view对应的id必需为：
 * webContainer =>R.id.webViewContainer
 *
 * 调用pay方法即可实现微信H5支付
 */
open class SimpleWXH5PayFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(wxReferer: String, redirectUrl: String): SimpleWXH5PayFragment {
            val fragment = SimpleWXH5PayFragment()
            val bundle = Bundle()
            bundle.putString("wxReferer", wxReferer)
            bundle.putString("redirectUrl", redirectUrl)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var webContainer: FrameLayout? = null
    var wxH5PayHelper: WXH5PayHelper? = null

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
        return R.layout.simple_fragment_wx_h5_pay
    }

    open fun initUI(view: View?, savedInstanceState: Bundle?) {
        webContainer = view?.findViewById(R.id.webViewContainer)
        val wxReferer = arguments?.getString("wxReferer")
        val redirectUrl = arguments?.getString("redirectUrl")
        if (webContainer == null) {
            throw Exception("webContainer is null")
        }
        if (wxReferer == null || redirectUrl == null) {
            throw Exception("arguments wxReferer is null or redirectUrl is null")
        }
        WebLogUtils.i("初始化微信H5支付--wxReferer：${wxReferer}，---redirectUrl：${redirectUrl}")
        wxH5PayHelper = WXH5PayHelper.with(webContainer!!, wxReferer, redirectUrl)
        addJsInterface()
    }

    open fun addJsInterface() {
        wxH5PayHelper?.webViewHelper?.let {
            //js接口
            it.webView.addJavascriptInterface(JavaScriptInterface(it.webView), "AndroidJSInterface")
        }
    }

    fun pay(payUrl: String) {
        WebLogUtils.i("调起微信H5支付--payUrl：${payUrl}")
        wxH5PayHelper?.pay(payUrl)
    }

    override fun onResume() {
        super.onResume()
        wxH5PayHelper?.onResume()
    }


    override fun onPause() {
        super.onPause()
        wxH5PayHelper?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        wxH5PayHelper?.onDestroyView()
    }
}