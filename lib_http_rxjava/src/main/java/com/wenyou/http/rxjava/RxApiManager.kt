package com.wenyou.http.rxjava

import io.reactivex.rxjava3.disposables.Disposable
import java.util.*

class RxApiManager private constructor() : RxActionManager<Any> {

    private val maps: HashMap<Any, Disposable> = HashMap()

    companion object {
        private var sInstance: RxApiManager? = null

        fun get(): RxApiManager {
            if (sInstance == null) {
                synchronized(RxApiManager::class.java) {
                    if (sInstance == null) {
                        sInstance = RxApiManager()
                    }
                }
            }
            return sInstance!!
        }
    }


    override fun add(
        tag: Any,
        disposable: Disposable
    ) {
        val key = "$tag:${disposable.hashCode()}"
        maps[key] = disposable
    }

    override fun remove(tag: Any) {
        val iterator = maps.iterator()
        while (iterator.hasNext()) {
            val key = iterator.next().key
            if (key.toString().contains(tag.toString())) {
                iterator.remove()
            }
        }
    }

    override fun removeAll() {
        maps.clear()
    }

    override fun cancel(tag: Any) {
        if (maps.isEmpty()) {
            return
        }
        val iterator = maps.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            val key = item.key
            val value = item.value
            if (key.toString().contains(tag.toString())) {
                if (!value.isDisposed) {
                    value.dispose()
                }
                iterator.remove()
            }
        }
    }

    override fun cancelAll() {
        if (maps.isEmpty()) {
            return
        }
        val iterator = maps.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            val value = item.value
            if (!value.isDisposed) {
                value.dispose()
            }
        }
        maps.clear()
    }

}