package com.wenyou.baselibrary.thread.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


/**
 * @description 线程监听器，绑定生命周期
 * @date: 2021/12/16 13:35
 * @author: jy
 */
open class LifecycleThreadListener(private val thread: Thread, private val cancelEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY) :
    LifecycleObserver {

    var isDestroy = false

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() = handleEvent(Lifecycle.Event.ON_PAUSE)

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() = handleEvent(Lifecycle.Event.ON_STOP)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() = handleEvent(Lifecycle.Event.ON_DESTROY)

    private fun handleEvent(e: Lifecycle.Event) {

        if (e == cancelEvent) {
            isDestroy = true
            thread.interrupt()
        }
    }
}