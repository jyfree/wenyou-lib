package com.wenyou.uidemo.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.wenyou.uidemo.R
import com.wenyou.uidemo.fragment.FlexBoxLayoutFragment
import com.wenyou.uidemo.fragment.FlowTagLayoutFragment
import com.wenyou.uidemo.fragment.NormalFlowLayoutFragment
import kotlinx.android.synthetic.main.activity_flow_layout_demo.*


/**
 * @description 弹性布局示例
 * @date: 2021/2/5 14:44
 * @author: jy
 */
class FlowLayoutSimpleActivity : AppCompatActivity() {

    companion object {
        fun startAct(context: Context) {
            val intent = Intent()
            intent.setClass(context, FlowLayoutSimpleActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow_layout_demo)
        initUI(savedInstanceState)
    }


    private fun initUI(savedInstanceState: Bundle?) {
        btn_flowTagLayout.setOnClickListener {
            changeFragment(FlowTagLayoutFragment.newInstance())
        }
        btn_flowLayout.setOnClickListener {
            changeFragment(NormalFlowLayoutFragment.newInstance())
        }
        btn_flexBox.setOnClickListener {
            changeFragment(FlexBoxLayoutFragment.newInstance())
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun changeFragment(fragment: Fragment) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frameLayout, fragment)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.addToBackStack(null)
        ft.commitAllowingStateLoss()
    }
}