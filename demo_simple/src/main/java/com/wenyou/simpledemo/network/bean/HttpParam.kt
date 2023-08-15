package com.wenyou.simpledemo.network.bean

import com.wenyou.baselibrary.utils.BaseUtils

/**
 * @description
 * @date: 2021/12/16 14:33
 * @author: jy
 */
class HttpParam private constructor() : HashMap<String, Any>() {
    companion object {
        fun obtain(): HttpParam = HttpParam()

        fun obtain(key: String, value: Any): HttpParam {
            val httpParam = HttpParam()
            httpParam[key] = value
            return httpParam
        }
    }

    operator fun plus(pair: Pair<String, Any>): HttpParam {
        this[pair.first] = pair.second
        return this
    }
}

fun defaultRequestMultipleParams(): HttpParam {
    return HttpParam.obtain("packager", BaseUtils.getApp().packageName)
}