package com.wenyou.sociallibrary.ext.share

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.wenyou.sociallibrary.SDKThreadManager


/**
 * @description
 * @date: 2021/12/16 14:01
 * @author: jy
 */
class SDKShareActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //不接受触摸屏事件
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        SDKShare.instance.sdkShareManager.behavior(this, savedInstanceState)
        initCompleteTime()
    }

    private fun initCompleteTime() {
        window.decorView.post {
            SDKThreadManager.getMainHandler().post {
                SDKShare.instance.sdkShareManager.checkShare(this@SDKShareActivity, intent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        SDKShare.instance.sdkShareManager.onActivityResult(requestCode, resultCode, data)
    }
}