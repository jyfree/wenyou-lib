package com.wenyou.http.coroutines

import com.wenyou.http.coroutines.utils.CrLogUtils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

/**
 * @description
 * @date: 2021/12/16 14:31
 * @author: jy
 */
class GlobalCoroutineExceptionHandler(
    private val errCode: Int,
    private val errMsg: String = "",
    private val report: Boolean = false
) : CoroutineExceptionHandler {
    override val key: CoroutineContext.Key<*>
        get() = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        val msg = exception.message
        CrLogUtils.e("errCode:${errCode}，errMsg:${errMsg}，GlobalCoroutineExceptionHandler:$msg")
    }
}
