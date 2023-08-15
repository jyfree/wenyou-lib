package com.wenyou.simpledemo.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import com.wenyou.baselibrary.base.BaseActivity
import com.wenyou.baselibrary.helper.CountDownTimerHelper
import com.wenyou.baselibrary.utils.ActivityUtils
import com.wenyou.simpledemo.R
import com.wenyou.simpledemo.databinding.SimpleTimerActivityBinding

/**
 * @description 倒计时示例
 * @date: 2021/8/23 15:59
 * @author: jy
 */
class TimerSimpleActivity : BaseActivity<SimpleTimerActivityBinding>() {

    companion object {
        fun startAct(context: Context) {
            ActivityUtils.startActivity(context, TimerSimpleActivity::class.java)
        }
    }


    override fun initUI(savedInstanceState: Bundle?) {
        viewBinding.btnStart1.isEnabled = true
        viewBinding.btnStart2.isEnabled = true
    }

    fun onClickTimer(view: View) {

        when (view.id) {
            R.id.btn_start1 -> startTimer1()
            R.id.btn_start2 -> startTimer2()
        }
    }

    private fun startTimer1() {
        viewBinding.btnStart1.isEnabled = false
        CountDownTimerHelper.beginBuilder()
            .register(this)
            .showTickLog(true)
            .interval(10000, 100)
            .onTickCallback {
                viewBinding.tvTimer1.text = (it / 100).toString()
            }
            .onFinishCallback {
                viewBinding.tvTimer1.text = "倒计时结束"
                viewBinding.btnStart1.isEnabled = true
            }
            .build()
    }

    private fun startTimer2() {
        viewBinding.btnStart2.isEnabled = false
        CountDownTimerHelper.beginBuilder()
            .register(this)
            .showTickLog(true)
            .interval(20000, 1000)
            .onTickCallback {
                viewBinding.tvTimer2.text = (it / 1000).toString()
            }
            .onFinishCallback {
                viewBinding.tvTimer2.text = "倒计时结束"
                viewBinding.btnStart2.isEnabled = true
            }
            .build()
    }
}