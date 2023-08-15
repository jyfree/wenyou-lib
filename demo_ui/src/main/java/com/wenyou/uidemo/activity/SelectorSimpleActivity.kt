package com.wenyou.uidemo.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wenyou.uidemo.R
import com.wenyou.uidemo.utils.setBgColor
import com.wenyou.uidemo.utils.setBgDrawable
import com.wenyou.uidemo.utils.setCompoundDrawable
import com.wenyou.uidemo.utils.setTextColor
import com.wenyou.yuilibrary.selector.selector.CompoundDrawableSelector
import kotlinx.android.synthetic.main.activity_selector_demo.*


/**
 * @description Selector示例
 * @date: 2021/2/5 14:44
 * @author: jy
 */
class SelectorSimpleActivity : AppCompatActivity() {

    companion object {
        fun startAct(context: Context) {
            val intent = Intent()
            intent.setClass(context, SelectorSimpleActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selector_demo)
        initUI(savedInstanceState)

    }


    private fun initUI(savedInstanceState: Bundle?) {
        tv_BgColor.setBgColor(
            "#03DAC5",
            "#6200EE"
        )
        tv_TextColor.setTextColor(
            "#03DAC5",
            "#6200EE"
        )
        iv_BgDrawable.setBgDrawable(
            R.drawable.demo_icon_up,
            R.drawable.demo_icon_down
        )
        tv_CDrawable.setCompoundDrawable(
            R.drawable.demo_icon_up,
            R.drawable.demo_icon_down,
            CompoundDrawableSelector.TOP
        )
    }
}