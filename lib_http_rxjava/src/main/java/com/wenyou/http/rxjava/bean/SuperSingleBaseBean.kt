package com.wenyou.http.rxjava.bean

import androidx.annotation.Keep

/**
 * @description
 * @date: 2021/12/16 14:33
 * @author: jy
 */
@Keep
abstract class SuperSingleBaseBean {
    abstract fun success(): Boolean

    abstract fun code(): String

    abstract fun msg(): String
}