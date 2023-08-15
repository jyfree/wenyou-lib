package com.wenyou.http.rxjava

/**
 * @description 自定义http错误码
 * @date: 2021/12/23 16:51
 * @author: jy
 */
enum class ERROR(private val code: String, private val err: String) {
    TIMEOUT("1000", "请求超时，请稍后再试..."),
    CONNECT_EXCEPTION("1001", "网络连接有误，请稍后再试..."),
    UNKNOWN_HOST("1002", "连接不上服务器..."),
    REQUEST_FAIL("1003", "请求失败，请稍后再试...");

    fun getMessage(): String {
        return err
    }

    fun getCode(): String {
        return code
    }
}