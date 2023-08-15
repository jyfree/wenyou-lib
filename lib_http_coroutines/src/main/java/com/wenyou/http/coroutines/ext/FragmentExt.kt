package com.wenyou.http.coroutines.ext

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.wenyou.http.coroutines.GlobalCoroutineExceptionHandler
import kotlinx.coroutines.*

/**
 * @description
 * @date: 2021/12/16 14:31
 * @author: jy
 */

fun Fragment.requestMain(
    errCode: Int = -1, errMsg: String = "", report: Boolean = false,
    block: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launch(
        GlobalCoroutineExceptionHandler(
            errCode,
            errMsg,
            report
        )
    ) {
        block.invoke(this)
    }
}

fun Fragment.requestIO(
    errCode: Int = -1, errMsg: String = "", report: Boolean = false,
    block: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launch(
        Dispatchers.IO + GlobalCoroutineExceptionHandler(
            errCode,
            errMsg,
            report
        )
    ) {
        block.invoke(this)
    }
}

fun Fragment.delayMain(
    errCode: Int = -1, errMsg: String = "", report: Boolean = false, delayTime: Long,
    block: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launch(
        GlobalCoroutineExceptionHandler(
            errCode,
            errMsg,
            report
        )
    ) {
        withContext(Dispatchers.IO) {
            delay(delayTime)
        }
        block.invoke(this)
    }
}