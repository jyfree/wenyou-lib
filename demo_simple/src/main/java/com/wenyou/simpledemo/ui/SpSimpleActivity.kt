package com.wenyou.simpledemo.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import com.wenyou.baselibrary.base.BaseActivity
import com.wenyou.baselibrary.sp.SharedPreferencesConfigUtils
import com.wenyou.baselibrary.utils.ActivityUtils
import com.wenyou.baselibrary.utils.YLogUtils
import com.wenyou.simpledemo.R
import com.wenyou.simpledemo.databinding.SimpleSpActivityBinding


/**
 * @description sp示例
 * @date: 2021/8/23 16:02
 * @author: jy
 */
class SpSimpleActivity : BaseActivity<SimpleSpActivityBinding>() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, SpSimpleActivity::class.java)
        }
    }


    override fun initUI(savedInstanceState: Bundle?) {

    }

    fun onClickSp(view: View) {
        when (view.id) {
            R.id.sp_put -> putTest()
            R.id.sp_get -> getTest()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SharedPreferencesConfigUtils.getInstance().apply()
    }

    private fun putTest() {
        val startTime = System.currentTimeMillis()
        val count = 10000
        for (i in 1..count) {
            SharedPreferencesConfigUtils.getInstance().add(i.toString(), i)
        }
        YLogUtils.e(
            "执行SP--put操作--${count}次--耗时：${System.currentTimeMillis() - startTime} ms"
        )
    }

    private fun getTest() {
        val startTime = System.currentTimeMillis()
        val count = 10000
        for (i in 1..count) {
            SharedPreferencesConfigUtils.getInstance().getInt(i.toString(), 0)
        }
        YLogUtils.e(
            "执行SP--get操作--${count}次--耗时：${System.currentTimeMillis() - startTime} ms"
        )
    }
}