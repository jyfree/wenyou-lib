package com.wenyou.baselibrary.thread.lifecycle

/**
 * @description
 * @date: 2021/12/16 13:36
 * @author: jy
 */
open class ThreadResultCallback<T>(val doNext: (T?) -> Unit) {
    fun forResult(t: T?) {
        doNext.invoke(t)
    }
}