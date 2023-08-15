package com.wenyou.simpledemo.network.rxjava

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.wenyou.http.rxjava.bean.SuperSingleBaseBean

/**
 * @description
 * @date: 2021/12/16 14:33
 * @author: jy
 */
@Keep
open class RxSingleBaseBean : SuperSingleBaseBean() {
    @SerializedName("requestId")
    var requestId: String = ""

    @SerializedName("success")
    var success: Boolean = false

    @SerializedName("code")
    var code: String = ""

    @SerializedName("msg")
    var msg: String = ""

    @SerializedName("hintMsg")
    var hintMsg: String = ""

    override fun success(): Boolean {
        return success
    }

    override fun code(): String {
        return code
    }

    override fun msg(): String {
        return msg
    }

    override fun toString(): String {
        return "RxSingleBaseBean(requestId='$requestId', success=$success, code='$code', msg='$msg', hintMsg='$hintMsg')"
    }


}