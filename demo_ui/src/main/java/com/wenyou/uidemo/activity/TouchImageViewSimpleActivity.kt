package com.wenyou.uidemo.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.wenyou.uidemo.DemoDataProvider
import com.wenyou.uidemo.R
import com.wenyou.yuilibrary.strategy.AlignEnum
import com.wenyou.yuilibrary.strategy.ImageLoader
import kotlinx.android.synthetic.main.activity_touch_iv_demo.*


/**
 * @description TouchImageView示例
 * @date: 2021/2/5 14:44
 * @author: jy
 */
class TouchImageViewSimpleActivity : AppCompatActivity() {

    companion object {
        fun startAct(context: Context) {
            val intent = Intent()
            intent.setClass(context, TouchImageViewSimpleActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch_iv_demo)
        initUI(savedInstanceState)
    }


    private fun initUI(savedInstanceState: Bundle?) {

        iv_touch.setOnClickListener {
            Log.d("YLog", "点我了")
        }
        ImageLoader.get().loadImage(
            iv_touch,
            DemoDataProvider.urls[0],
            align = AlignEnum.CENTER_CROP
        )

        ImageLoader.get().loadImage(
            iv_1,
            DemoDataProvider.urls[1]
        )

        ImageLoader.get().loadImage(
            iv_2,
            DemoDataProvider.urls[2],
            circle = 30
        )
        ImageLoader.get().loadImage(
            iv_3,
            DemoDataProvider.urls[3],
            circle = 360
        )
        ImageLoader.get().loadImage(
            iv_4,
            DemoDataProvider.urls[4],
            strokeWidth = 5f,
            strokeColor = Color.RED
        )
    }
}