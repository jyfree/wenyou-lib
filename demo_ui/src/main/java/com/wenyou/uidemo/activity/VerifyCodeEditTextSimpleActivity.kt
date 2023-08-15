package com.wenyou.uidemo.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wenyou.uidemo.R
import com.wenyou.yuilibrary.utils.YUILogUtils
import com.wenyou.yuilibrary.widget.verify.VerifyCodeEditText
import kotlinx.android.synthetic.main.activity_verify_code_demo.*


/**
 * @description VerifyCode示例
 * @date: 2021/2/5 14:44
 * @author: jy
 */
class VerifyCodeEditTextSimpleActivity : AppCompatActivity(), VerifyCodeEditText.OnInputListener {

    companion object {
        fun startAct(context: Context) {
            val intent = Intent()
            intent.setClass(context, VerifyCodeEditTextSimpleActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_code_demo)
        vci_1.setOnInputListener(this)
        vci_2.setOnInputListener(this)
        vci_3.setOnInputListener(this)
        vci_4.setOnInputListener(this)
        btn_clear.setOnClickListener {
            vci_1.clearInputValue()
            vci_2.clearInputValue()
            vci_3.clearInputValue()
            vci_4.clearInputValue()
        }
    }

    override fun onChange(input: String?) {
        YUILogUtils.i("onChange", input)
    }

    override fun onComplete(input: String?) {
        YUILogUtils.i("onComplete", input)
    }

    override fun onClear() {
        YUILogUtils.i("onClear")

    }


}