package com.wenyou.uidemo.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wenyou.uidemo.R


/**
 * @description ShapeTextView示例
 * @date: 2021/2/5 14:44
 * @author: jy
 */
class ShapeTextViewSimpleActivity : AppCompatActivity() {

    companion object {
        fun startAct(context: Context) {
            val intent = Intent()
            intent.setClass(context, ShapeTextViewSimpleActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shape_tv_demo)
    }

}