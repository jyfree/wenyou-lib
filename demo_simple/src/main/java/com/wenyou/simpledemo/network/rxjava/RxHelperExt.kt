package com.wenyou.simpledemo.network.rxjava

import com.wenyou.http.rxjava.RxHelper
import io.reactivex.rxjava3.core.ObservableTransformer

object RxHelperExt {
    /**
     * 扩展响应结果
     */
    fun <T> handleResult(): ObservableTransformer<RxBaseBean<T>, T> {
        return RxHelper.handleResult()
    }
}