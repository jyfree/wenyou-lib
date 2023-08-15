package com.wenyou.http.coroutines.ext

import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.wenyou.http.coroutines.GlobalCoroutineExceptionHandler
import kotlinx.coroutines.*

/**
 * @description
 * @date: 2021/12/16 14:31
 * @author: jy
 */
fun DialogFragment.requestMain(
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

fun DialogFragment.requestIO(
    errCode: Int = -1, errMsg: String = "", report: Boolean = false,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleScope.launch(
        Dispatchers.IO + GlobalCoroutineExceptionHandler(
            errCode,
            errMsg,
            report
        )
    ) {
        block.invoke(this)
    }
}

fun DialogFragment.delayMain(
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