package com.wenyou.simpledemo.network.coroutines

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.wenyou.http.coroutines.bean.SuperBaseBean

/**
 * @description
 * @date: 2021/12/16 14:33
 * @author: jy
 */
@Keep
open class CrBaseBean<T> : SuperBaseBean<T>() {
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

    @SerializedName("data")
    var data: T? = null

    override fun data(): T? {
        return data
    }

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
        return "CrBaseBean(requestId='$requestId', success=$success, code='$code', msg='$msg', hintMsg='$hintMsg', data=$data)"
    }


}