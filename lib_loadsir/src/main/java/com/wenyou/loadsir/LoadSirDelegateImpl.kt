package com.wenyou.loadsir

import com.wenyou.baselibrary.loadsir.ILoading
import com.wenyou.baselibrary.loadsir.LoadSirDelegateInterface

/**
 * 菊花代理实现
 */
class LoadSirDelegateImpl : LoadSirDelegateInterface {
    override fun initLoading(): ILoading? {
        return ILoadingImpl()
    }
}