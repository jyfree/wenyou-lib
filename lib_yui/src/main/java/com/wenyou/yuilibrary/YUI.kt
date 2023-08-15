package com.wenyou.yuilibrary

import android.app.Application
import android.content.Context
import com.wenyou.yuilibrary.strategy.IImageLoadStrategy
import com.wenyou.yuilibrary.strategy.ImageLoader
import com.wenyou.yuilibrary.utils.YUILogUtils

/**
 * @description
 * @date: 2021/12/16 14:20
 * @author: jy
 */
object YUI {
    private var sContext: Application? = null
    /**
     * 必须在全局Application先调用，获取context上下文
     * @param app Application
     */
    fun init(app: Application, strategy: IImageLoadStrategy, showLog: Boolean = false) {
        sContext = app
        ImageLoader.get().init(strategy)
        YUILogUtils.SHOW_LOG = showLog
    }

    /**
     * 获取上下文
     */
    fun getContext(): Context {
        initialize()
        return sContext!!
    }

    /**
     * 检测是否调用初始化方法
     */
    private fun initialize() {
        if (sContext == null) {
            throw ExceptionInInitializerError("请先在全局Application中调用YUI.init()初始化！")
        }
    }

}