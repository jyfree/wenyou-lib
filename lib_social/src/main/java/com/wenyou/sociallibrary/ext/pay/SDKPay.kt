package com.wenyou.sociallibrary.ext.pay

/**
 * @description
 * @date: 2021/12/16 14:00
 * @author: jy
 */
class SDKPay private constructor() {
    val sdkPayManager: SDKPayManager = SDKPayManager()

    private object Holder {
        val instance = SDKPay()
    }

    companion object {

        val instance: SDKPay
            get() = Holder.instance
    }
}
