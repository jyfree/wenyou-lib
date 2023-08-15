package com.wenyou.baselibrary.thread.lifecycle


import androidx.lifecycle.LifecycleOwner
import com.wenyou.baselibrary.thread.ThreadManage
import com.wenyou.baselibrary.utils.YLogUtils

/**
 * @description 绑定生命周期的线程
 * @date: 2021/12/16 13:34
 * @author: jy
 */
fun <T> executeThreadWithLifecycle(lifecycleOwner: LifecycleOwner, block: () -> T) {

    var life: LifecycleThreadListener? = null
    val thread = object : Thread() {

        override fun run() {
            try {
                if (life?.isDestroy == false) {
                    block()
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
                YLogUtils.e("executeThread--InterruptedException", e.message)
            }
        }
    }
    life = LifecycleThreadListener(thread)
    lifecycleOwner.lifecycle.addObserver(life)

    submit(thread)


}

fun <T> executeThread(block: () -> T) {

    val thread = object : Thread() {

        override fun run() {
            block()
        }
    }
    submit(thread)
}


fun submit(task: Thread) {
    ThreadManage.getInstance().loaderEngine.submit(task)
}