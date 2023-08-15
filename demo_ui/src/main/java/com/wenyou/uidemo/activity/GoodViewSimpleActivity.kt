package com.wenyou.uidemo.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.wenyou.uidemo.R
import com.wenyou.yuilibrary.widget.good.GoodView
import com.wenyou.yuilibrary.widget.good.IGoodView
import kotlinx.android.synthetic.main.activity_good_demo.*


/**
 * @description 点赞view
 * @date: 2021/2/18 14:13
 * @author: jy
 */
class GoodViewSimpleActivity : AppCompatActivity() {

    private var mGoodView: IGoodView? = null

    companion object {
        fun startAct(context: Context) {
            val intent = Intent()
            intent.setClass(context, GoodViewSimpleActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_good_demo)
        mGoodView = GoodView(this)
    }

    fun btnClick(view: View) {
        when (view.id) {
            R.id.btn_reset -> {
                reset()
            }
            R.id.iv1 -> {
                iv1.setImageResource(R.drawable.demo_ic_good_checked)
                mGoodView!!.setText("+1")
                    .setTextColor(Color.parseColor("#f66467"))
                    .setTextSize(12)
                    .show(view)
            }
            R.id.iv2 -> {
                iv2.setImageResource(R.drawable.demo_ic_good_checked)
                mGoodView!!.setImageResource(R.drawable.demo_ic_good_checked)
                    .show(view)
            }
            R.id.iv3 -> {
                iv3.setImageResource(R.drawable.demo_ic_collection_checked)
                mGoodView!!.setTextInfo("收藏成功", Color.parseColor("#f66467"), 12)
                    .show(view)
            }
            R.id.iv4 -> {
                iv4.setImageResource(R.drawable.demo_ic_bookmark_checked)
                mGoodView!!.setTextInfo("收藏成功", Color.parseColor("#ff941A"), 12)
                    .show(view)
            }
        }
    }

    private fun reset() {
        iv1.setImageResource(R.drawable.demo_ic_good)
        iv2.setImageResource(R.drawable.demo_ic_good)
        iv3.setImageResource(R.drawable.demo_ic_collection)
        iv4.setImageResource(R.drawable.demo_ic_bookmark)
        mGoodView?.reset()
    }
}