package com.wenyou.http.rxjava

import io.reactivex.rxjava3.disposables.Disposable

/**
 * @description
 * @date: 2023/02/16 14:35
 * @author: jy
 * rx行为管理器
 */
interface RxActionManager<T> {
    fun add(tag: T, disposable: Disposable)
    fun remove(tag: T)
    fun removeAll()
    fun cancel(tag: T)
    fun cancelAll()
}