package com.wenyou.simpledemo.ui

import android.content.Context
import android.os.Bundle
import com.wenyou.baselibrary.base.BaseActivity
import com.wenyou.baselibrary.utils.ActivityUtils
import com.wenyou.simpledemo.Constants
import com.wenyou.simpledemo.R
import com.wenyou.simpledemo.databinding.SimpleWxH5PayActivityBinding
import com.wenyou.web.SimpleWXH5PayFragment

/**
 * 微信H5支付
 */
class WXH5PaySimpleActivity : BaseActivity<SimpleWxH5PayActivityBinding>() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, WXH5PaySimpleActivity::class.java)
        }
    }

    private var fragment: SimpleWXH5PayFragment? = null

    override fun initUI(savedInstanceState: Bundle?) {
        initH5Fragment()
        viewBinding.wxH5Pay.setOnClickListener {
            fragment?.pay("https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx29142732733785d0f1c09f6990703c0000&package=652449833")
        }
    }

    private fun initH5Fragment() {
        fragment = SimpleWXH5PayFragment.newInstance(
            Constants.WXH5Pay.REFERER,
            Constants.WXH5Pay.REDIRECT_URL
        )
        supportFragmentManager.beginTransaction().add(R.id.fl_content, fragment!!)
            .commitAllowingStateLoss()
    }
}