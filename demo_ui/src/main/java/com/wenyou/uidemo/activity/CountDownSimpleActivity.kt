package com.wenyou.uidemo.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wenyou.uidemo.R
import com.wenyou.yuilibrary.selector.YSelector
import kotlinx.android.synthetic.main.activity_count_down_demo.*


/**
 * @description 倒计时View示例
 * @date: 2021/2/5 14:44
 * @author: jy
 */
class CountDownSimpleActivity : AppCompatActivity() {

    companion object {
        fun startAct(context: Context) {
            val intent = Intent()
            intent.setClass(context, CountDownSimpleActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down_demo)

        YSelector.colorSelector()
            .defaultColor("#03DAC5")
            .pressedColor("#cccccc")
            .disabledColor("#cccccc")
            .into(cdTV)
        cdTV.setOnClickListener {
            cdTV.startCountDown()
        }
        YSelector.shapeSelector()
            .defaultBgColor("#03DAC5")
            .pressedBgColor("#cccccc")
            .disabledBgColor("#cccccc")
            .selectorColor(
                YSelector.colorSelector()
                    .defaultColor("#ffffff")
                    .pressedColor("#ff0000")
                    .disabledColor("#ff0000").build()
            )
            .radius(20f)
            .into(cdBtn)
        cdBtn.setOnClickListener {
            cdBtn.startCountDown()
        }
    }


}