package com.wenyou.baselibrary

import android.app.Application
import com.wenyou.baselibrary.sp.SharedPreferencesConfigUtils
import com.wenyou.baselibrary.thread.LoaderConfiguration
import com.wenyou.baselibrary.thread.ThreadManage
import com.wenyou.baselibrary.thread.lifecycle.executeThread
import com.wenyou.baselibrary.utils.BaseUtils


/**
 * @description 本库公共配置
 * @date: 2021/12/16 13:44
 * @author: jy
 */
object BaseLibraryConfig {

    /**
     * 必须在全局Application onCreate调用
     * @param application Application
     * @param threadLoaderConfig 线程池配置
     */
    fun init(
        application: Application,
        threadLoaderConfig: LoaderConfiguration
    ) {
        //初始化基础工具类
        BaseUtils.init(application)
        //初始化全局线程池
        ThreadManage.getInstance().loaderEngine.init(threadLoaderConfig)
        //预加载sp
        executeThread {
            SharedPreferencesConfigUtils.getInstance().loadAll()
        }
    }
}