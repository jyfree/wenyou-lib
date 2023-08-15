package com.wenyou.http.coroutines.bean

import androidx.annotation.Keep

/**
 * @description
 * @date: 2021/12/16 14:33
 * @author: jy
 */
@Keep
abstract class SuperBaseBean<T> {

    abstract fun data(): T?

    abstract fun success(): Boolean

    abstract fun code(): String

    abstract fun msg(): String
}