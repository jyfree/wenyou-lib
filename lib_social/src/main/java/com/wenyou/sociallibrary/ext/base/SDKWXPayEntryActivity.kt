package com.wenyou.sociallibrary.ext.base

import com.wenyou.sociallibrary.ext.SDKConstants
import com.wenyou.sociallibrary.ext.data.StatusBean
import com.wenyou.sociallibrary.ext.data.StatusLiveData
import com.wenyou.sociallibrary.utils.SDKLogUtils
import com.wenyou.sociallibrary.wx.WXPayBaseEntryActivity

/**
 * @description 实现微信支付需要继承此类
 * @date: 2021/12/16 13:59
 * @author: jy
 */
abstract class SDKWXPayEntryActivity : WXPayBaseEntryActivity() {

    override fun paySucceed() {
        //TODO 支付成功，发送RxBus
        SDKLogUtils.i("微信支付--成功")
        val statusBean = StatusBean();
        statusBean.status = SDKConstants.PayStatus.WX_PAY_SUCCESS
        StatusLiveData.getInstance().value = statusBean;
    }

    override fun payCancel() {
        //TODO 取消支付
        SDKLogUtils.i("微信支付--取消")
        val statusBean = StatusBean();
        statusBean.status = SDKConstants.PayStatus.WX_PAY_CANCEL
        StatusLiveData.getInstance().value = statusBean;
    }

    override fun payFail(errCode: Int) {
        //TODO 支付失败 errCode
        SDKLogUtils.e("微信支付--失败--errCode", errCode)
        val statusBean = StatusBean();
        statusBean.errCode = errCode
        statusBean.status = SDKConstants.PayStatus.WX_PAY_FAIL
        StatusLiveData.getInstance().value = statusBean;
    }


}