package com.wenyou.sociallibrary.ext.data

import androidx.lifecycle.MutableLiveData

/**
 * @description
 * @date: 2021/12/16 13:59
 * @author: jy
 */
object StatusLiveData : MutableLiveData<StatusBean>() {

    fun getInstance(): StatusLiveData = Holder.INSTANCE

    private object Holder {
        val INSTANCE: StatusLiveData = StatusLiveData
    }
}