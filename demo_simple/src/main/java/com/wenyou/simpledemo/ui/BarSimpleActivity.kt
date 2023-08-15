package com.wenyou.simpledemo.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.wenyou.baselibrary.base.BaseActivity
import com.wenyou.baselibrary.channel.ChannelToolbarStyle
import com.wenyou.baselibrary.utils.ActivityUtils
import com.wenyou.baselibrary.utils.BarUtils
import com.wenyou.simpledemo.R
import com.wenyou.simpledemo.databinding.SimpleBarActivityBinding


/**
 * @description bar示例
 * @date: 2021/12/16 16:56
 * @author: jy
 */
class BarSimpleActivity : BaseActivity<SimpleBarActivityBinding>() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, BarSimpleActivity::class.java)
        }
    }

    private var statusBar: View? = null

    override fun initUI(savedInstanceState: Bundle?) {

    }

    /**
     * 若非ChannelToolbarStyle.TRANSLATE，则setStatusBarColor设置isDecor=true，否则修改setStatusBarColor无效
     */
    override fun getToolBarStyle(): ChannelToolbarStyle {
        return ChannelToolbarStyle.TRANSLATE
    }

    fun onClickBar(view: View) {
        when (view.id) {
            R.id.btn_full -> {
                statusBar?.visibility = View.GONE
                BarUtils.setStatusBarFullTransparent(this)
                BarUtils.setStatusBarColor(this, Color.TRANSPARENT)
            }
            R.id.btn_white -> {
                statusBar?.visibility = View.GONE
                BarUtils.setStatusBarColor(this, Color.WHITE)
                BarUtils.setDarkMode(this)
            }
            R.id.btn_black -> {
                statusBar?.visibility = View.GONE
                BarUtils.setStatusBarColor(this, Color.parseColor("#9938e4"))
                BarUtils.setLightMode(this)
            }
            R.id.btn_gradual -> {
                if (statusBar == null) {
                    statusBar = BarUtils.getView(this, R.drawable.simple_gradual_bg)
                    val contentView = findViewById<ViewGroup>(android.R.id.content)
                    contentView.addView(statusBar)
                }
                BarUtils.setStatusBarCustom(statusBar!!)
            }
            R.id.btn_dialog -> {
                DialogSimple(this, R.style.Theme_AppCompat_Dialog).show()
            }
        }
    }
}