package com.wenyou.baselibrary.channel

/**
 * @description 渠道参数，用于后续的渠道拦截，如修改马甲包UI样式等
 * @date: 2021/9/15 18:41
 * @author: jy
 */
class ChannelParams private constructor() {
    var array: MutableList<Any?>? = null

    @Synchronized
    fun add(vararg params: Any?) {
        if (array == null) {
            array = mutableListOf()
        }
        params.forEach {
            array?.add(it)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(index: Int): T? {
        val get = this.array?.get(index)
        return if (get == null) null else (get as T)
    }

    companion object {
        fun obtain(vararg params: Any?): ChannelParams = ChannelParams().apply {
            this.add(*params)
        }
    }

}