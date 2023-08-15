package com.wenyou.loadsir

import com.wenyou.baselibrary.loadsir.LoadSirManager
import com.wenyou.loadsir.framework.callback.SuccessCallback
import com.wenyou.loadsir.framework.core.LoadSir
import com.wenyou.loadsir.simple.*

/**
 * 菊花扩展类
 * 用于初始化菊花，application调用
 */
object LoadSirExt {

    /**
     * 初始化
     * @param loadSirLayoutInterface 实现接口可自定义callback布局
     */
    fun init(loadSirLayoutInterface: LoadSirLayoutInterface? = null) {
        configLoadSir()
        LoadSirSimpleLayout.get().init(loadSirLayoutInterface)
        LoadSirManager.get().init(LoadSirDelegateImpl())
    }

    /**
     * 配置LoadSir
     */
    private fun configLoadSir() {
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(EmptyCallback())
            .addCallback(AnimateCallback())
            .addCallback(LoadingCallback())
            .addCallback(LoadingCoverCallback())
            .setDefaultCallback(SuccessCallback::class.java)
            .commit()
    }
}