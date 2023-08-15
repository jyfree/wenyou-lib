package com.wenyou.baselibrary.utils

import android.os.Handler
import android.os.Looper

/**
 * @description handler工具类
 * @date: 2021/12/16 13:42
 * @author: jy
 */
object YHandlerUtils {
    private var sHandler: Handler? = null

    private fun getHandler(): Handler {
        if (sHandler == null) {
            synchronized(YHandlerUtils::class.java) {
                if (sHandler == null) {
                    sHandler = Handler(Looper.getMainLooper())
                }
            }
        }
        return sHandler!!
    }

    fun runOnBackThread(runnable: Runnable, delay: Int) {
        getHandler().postDelayed({
            val thread = Thread(runnable)
            thread.start()
        }, delay * 1000L)
    }

    fun runOnBackThread(runnable: Runnable) {
        getHandler().post {
            val thread = Thread(runnable)
            thread.start()
        }
    }

    fun runOnUiThread(runnable: Runnable) {
        getHandler().post(runnable)
    }

    fun runOnUiThread(runnable: Runnable, delay: Int) {
        getHandler().postDelayed(runnable, delay * 1000L)
    }

    fun runOnUiThread(runnable: Runnable, delay: Long) {
        getHandler().postDelayed(runnable, delay)
    }
}