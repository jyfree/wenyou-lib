package com.wenyou.simpledemo

import android.os.Bundle
import android.view.View
import com.wenyou.baselibrary.base.BaseActivity
import com.wenyou.simpledemo.databinding.SimpleMainActivityBinding
import com.wenyou.simpledemo.ui.*
import com.wenyou.simpledemo.ui.http.ApiRxJavaSimpleActivity
import com.wenyou.simpledemo.ui.http.ApiSimpleActivity
import com.wenyou.simpledemo.ui.sharedata.LazySimpleActivity
import com.wenyou.simpledemo.ui.social.LoginSimpleActivity
import com.wenyou.simpledemo.ui.social.PaySimpleActivity
import com.wenyou.simpledemo.ui.social.ShareSimpleActivity
import com.wenyou.simpledemo.ui.social.common.LoginSimpleCommonActivity
import com.wenyou.simpledemo.ui.social.common.PaySimpleCommonActivity
import com.wenyou.simpledemo.ui.social.common.ShareSimpleCommonActivity

class MainActivity : BaseActivity<SimpleMainActivityBinding>() {

    override fun initUI(savedInstanceState: Bundle?) {

    }


    fun go2Act(v: View) {
        when (v.id) {
            R.id.go_permission -> PermissionSimpleActivity.startAct(this)
            R.id.go_bar -> BarSimpleActivity.startAct(this)
            R.id.go_pic -> PicSimpleActivity.startAct(this)
            R.id.go_timer -> TimerSimpleActivity.startAct(this)
            R.id.go_sp -> SpSimpleActivity.startAct(this)
            R.id.go_login -> LoginSimpleActivity.startAct(this)
            R.id.go_loginCommon -> LoginSimpleCommonActivity.startAct(this)
            R.id.go_pay -> PaySimpleActivity.startAct(this)
            R.id.go_payCommon -> PaySimpleCommonActivity.startAct(this)
            R.id.go_share -> ShareSimpleActivity.startAct(this)
            R.id.go_shareCommon -> ShareSimpleCommonActivity.startAct(this)
            R.id.go_api -> ApiSimpleActivity.startAct(this)
            R.id.go_api_rx -> ApiRxJavaSimpleActivity.startAct(this)
            R.id.go_lazy -> LazySimpleActivity.startAct(this)
            R.id.go_supply -> SupplySimpleActivity.startAct(this)
            R.id.go_wxH5Pay -> WXH5PaySimpleActivity.startAct(this)
        }
    }
}
