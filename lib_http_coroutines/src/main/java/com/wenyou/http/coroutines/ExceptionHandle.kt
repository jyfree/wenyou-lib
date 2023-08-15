package com.wenyou.http.coroutines

import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @description 异常处理
 * @date: 2021/12/16 14:35
 * @author: jy
 */
object ExceptionHandle {

    fun handleException(e: Throwable): ApiException {
        e.printStackTrace()
        val ex: ApiException
        if (e is ApiException) {
            ex = e
        } else if (e is UnknownHostException) {
            ex = getApiException(ERROR.UNKNOWN_HOST)
        } else if (e is HttpException) {
            ex = if ("HTTP 404 Not Found" == e.message
                || "HTTP 502 Fiddler - DNS Lookup Failed" == e.message
            ) { // 404 not found
                ApiException(e.code().toString(), "连接不上服务器...")
            } else {
                ApiException(e.code().toString(), e.message())
            }
        } else if (e is SocketTimeoutException) {
            ex = getApiException(ERROR.TIMEOUT)
        } else if (e is ConnectException) {
            ex = getApiException(ERROR.CONNECT_EXCEPTION)
        } else {
            ex = if (e.message.isNullOrBlank()) {
                getApiException(ERROR.REQUEST_FAIL)
            } else {
                ApiException(ERROR.REQUEST_FAIL.getCode(), e.message!!)
            }
        }
        return ex
    }

    private fun getApiException(error: ERROR): ApiException {
        return ApiException(error.getCode(), error.getMessage())
    }
}