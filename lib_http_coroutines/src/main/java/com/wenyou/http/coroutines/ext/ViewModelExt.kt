package com.wenyou.http.coroutines.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wenyou.http.coroutines.GlobalCoroutineExceptionHandler
import kotlinx.coroutines.*

/**
 * @description
 * @date: 2021/12/16 14:31
 * @author: jy
 */

fun ViewModel.requestMain(
    errCode: Int = -1, errMsg: String = "", report: Boolean = false,
    block: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch(
        GlobalCoroutineExceptionHandler(
            errCode,
            errMsg,
            report
        )
    ) {
        block.invoke(this)
    }
}

fun ViewModel.requestIO(
    errCode: Int = -1, errMsg: String = "", report: Boolean = false,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return viewModelScope.launch(
        Dispatchers.IO + GlobalCoroutineExceptionHandler(
            errCode,
            errMsg,
            report
        )
    ) {
        block.invoke(this)
    }
}

fun ViewModel.delayMain(
    errCode: Int = -1, errMsg: String = "", report: Boolean = false, delayTime: Long,
    block: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch(
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