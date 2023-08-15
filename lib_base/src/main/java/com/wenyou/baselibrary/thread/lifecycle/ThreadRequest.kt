package com.wenyou.baselibrary.thread.lifecycle

import androidx.lifecycle.LifecycleOwner
import com.wenyou.baselibrary.utils.YHandlerUtils
import com.wenyou.baselibrary.utils.YLogUtils


/**
 * @description
 * @date: 2021/12/16 13:36
 * @author: jy
 */
interface ThreadRequest {
    fun <T> requestThread(
        threadResultCallback: ThreadResultCallback<T>,
        lifecycleOwner: LifecycleOwner,
        block: () -> T?
    ) {

        executeThreadWithLifecycle(lifecycleOwner) {
            try {
                val t = block.invoke()
                YHandlerUtils.runOnUiThread(Runnable {
                    threadResultCallback.forResult(t)
                })
            } catch (ex: Exception) {
                YLogUtils.e("ThreadRequest request  error : ${ex.message}")
                threadResultCallback.forResult(null)
            }
        }
    }

    fun <T> requestThread(block: () -> T?) {
        executeThread {
            block.invoke()
        }
    }

}