package com.wenyou.simpledemo.ui.social

import android.content.Context
import android.os.Bundle
import android.view.View
import com.wenyou.baselibrary.base.BaseActivity
import com.wenyou.baselibrary.utils.ActivityUtils
import com.wenyou.baselibrary.utils.YLogUtils
import com.wenyou.simpledemo.R
import com.wenyou.simpledemo.databinding.SimpleLoginActivityBinding
import com.wenyou.sociallibrary.constant.SDKLoginType
import com.wenyou.sociallibrary.ext.login.SDKLogin
import com.wenyou.sociallibrary.ext.login.SDKLoginManager
import com.wenyou.sociallibrary.listener.OnSocialSdkLoginListener
import com.wenyou.sociallibrary.wx.WXListener


/**
 * @description 登录示例（接口方式）
 * @date: 2021/8/23 16:28
 * @author: jy
 */
class LoginSimpleActivity : BaseActivity<SimpleLoginActivityBinding>() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, LoginSimpleActivity::class.java)
        }
    }

    var sdkLoginManager: SDKLoginManager? = null


    override fun initUI(savedInstanceState: Bundle?) {
    }


    fun onClickLogin(view: View) {
        when (view.id) {
            R.id.login_2qq -> request(SDKLoginType.TYPE_QQ)
            R.id.login_2wx -> request(SDKLoginType.TYPE_WX)
            R.id.login_2wb -> request(SDKLoginType.TYPE_WB)
        }
    }

    private fun request(loginType: Int) {
        if (sdkLoginManager == null) {
            sdkLoginManager = SDKLogin.instance.sdkLoginManager.setLoginListener(object :
                OnSocialSdkLoginListener {
                override fun loginAuthSuccess(type: Int, token: String?, info: String?) {
                    YLogUtils.i("登录授权成功--类型：", type, "token", token, "info", info)
                }

                override fun loginFail(type: Int, error: String?) {
                    YLogUtils.e("登录授权失败--类型：", type, "error", error)
                }

                override fun loginCancel(type: Int) {
                    YLogUtils.i("取消登录--类型：", type)
                }
            }).setWXListener(object : WXListener {
                override fun startWX(isSucceed: Boolean) {
                    YLogUtils.e("启动微信成功？", isSucceed)
                }

                override fun installWXAPP() {
                    YLogUtils.e("未安装微信")
                }

            }).registerObserve(this)
        }
        sdkLoginManager?.request(this, loginType)
    }
}