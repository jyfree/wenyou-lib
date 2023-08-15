package com.wenyou.uidemo.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wenyou.uidemo.R
import com.wenyou.yuilibrary.widget.actionbar.TitleBar

/**
 * @description 标题栏示例
 * @date: 2021/9/15 14:58
 * @author: jy
 */
class TitleBarSimpleActivity : AppCompatActivity() {
    companion object {
        fun startAct(context: Context) {
            val intent = Intent()
            intent.setClass(context, TitleBarSimpleActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title_bar_demo)

        val titlebar = findViewById<TitleBar>(R.id.titlebar)
        titlebar.setLeftClickListener {
            Toast.makeText(this, "点击返回", Toast.LENGTH_SHORT).show()
        }.setCenterClickListener {
            Toast.makeText(this, "点击标题", Toast.LENGTH_SHORT).show()
        }.addAction(object : TitleBar.ImageAction(R.drawable.demo_ic_add_white) {
            override fun performAction(view: View?) {
                Toast.makeText(this@TitleBarSimpleActivity, "点击更多", Toast.LENGTH_SHORT).show()
            }
        })

        val titlebar1 = findViewById<TitleBar>(R.id.titlebar1)
        titlebar1.setLeftClickListener {
            Toast.makeText(this, "点击返回", Toast.LENGTH_SHORT).show()
        }.addAction(object : TitleBar.TextAction("更多") {
            override fun performAction(view: View?) {
                Toast.makeText(this@TitleBarSimpleActivity, "点击更多", Toast.LENGTH_SHORT).show()
            }
        })

        val titlebar2 = findViewById<TitleBar>(R.id.titlebar2)
        titlebar2.disableLeftView()
            .addAction(object : TitleBar.ImageAction(R.drawable.demo_ic_navigation_more) {
                override fun performAction(view: View?) {
                    Toast.makeText(this@TitleBarSimpleActivity, "点击菜单", Toast.LENGTH_SHORT).show()
                }

            })
    }
}