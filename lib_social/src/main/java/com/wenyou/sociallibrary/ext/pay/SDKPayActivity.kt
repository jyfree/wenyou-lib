package com.wenyou.sociallibrary.ext.pay

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.wenyou.sociallibrary.SDKThreadManager


/**
 * @description
 * @date: 2021/12/16 14:00
 * @author: jy
 */
class SDKPayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //不接受触摸屏事件
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        SDKPay.instance.sdkPayManager.behavior(this, savedInstanceState)
        initCompleteTime()
    }

    private fun initCompleteTime() {
        window.decorView.post {
            SDKThreadManager.getMainHandler().post {
                SDKPay.instance.sdkPayManager.checkPay(this@SDKPayActivity, intent)
            }
        }
    }

}