package com.wenyou.baselibrary.ext.livedata

/**
 * @description 请求状态
 * @date: 2021/12/21 17:44
 * @author: jy
 */
sealed class RequestState<out T> {
    data class Loading(val show: Boolean) : RequestState<Nothing>()
    data class Success<out T>(val data: T?) : RequestState<T>()
    data class Error(val errorMsg: String? = null, val exception: Exception? = null) :
        RequestState<Nothing>()
}

