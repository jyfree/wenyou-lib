package com.wenyou.http.rxjava

/**
 * @description
 * @date: 2021/12/16 14:34
 * @author: jy
 */
class ApiException(val code: String, message: String) : Exception(message, Throwable(code))