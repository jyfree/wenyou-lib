package com.wenyou.uidemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.wenyou.uidemo.activity.*

class MainActivity : AppCompatActivity() {

    companion object {
        fun startAct(context: Context) {
            val intent = Intent()
            intent.setClass(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_demo)
    }

    fun btnClick(view: View) {
        when (view.id) {
            R.id.btnSelectorSimple -> SelectorSimpleActivity.startAct(this)
            R.id.btnShapeTVSimple -> ShapeTextViewSimpleActivity.startAct(this)
            R.id.btnTouchIVSimple -> TouchImageViewSimpleActivity.startAct(this)
            R.id.btnBannerSimple -> BannerSimpleActivity.startAct(this)
            R.id.btnAlphaViewSimple -> AlphaViewSimpleActivity.startAct(this)
            R.id.btnCountDownViewSimple -> CountDownSimpleActivity.startAct(this)
            R.id.btnShadowSimple -> ShadowSimpleActivity.startAct(this)
            R.id.btnGoodViewSimple -> GoodViewSimpleActivity.startAct(this)
            R.id.btnVerifyCodeSimple -> VerifyCodeEditTextSimpleActivity.startAct(this)
            R.id.btnFlowLayoutSimple -> FlowLayoutSimpleActivity.startAct(this)
            R.id.btnTitleBarSimple -> TitleBarSimpleActivity.startAct(this)
        }
    }
}
