package com.wenyou.baselibrary.loadsir

import com.wenyou.baselibrary.utils.YLogUtils

/**
 * 菊花经理
 */
class LoadSirManager : LoadSirDelegateInterface {

    private var delegateInterface: LoadSirDelegateInterface? = null

    companion object {
        @Volatile
        private var sInstance: LoadSirManager? = null

        fun get(): LoadSirManager {
            if (sInstance == null) {
                synchronized(LoadSirManager::class.java) {
                    if (sInstance == null) {
                        sInstance = LoadSirManager()
                    }
                }
            }
            return sInstance!!
        }
    }

    fun init(delegateInterface: LoadSirDelegateInterface) {
        if (this.delegateInterface != null) {
            YLogUtils.w("多次初始化菊花!!--请检查代码")
            return
        }
        this.delegateInterface = delegateInterface
    }

    override fun initLoading(): ILoading? {
        if (delegateInterface == null) {
            YLogUtils.w("未初始化菊花--请实现LoadSirDelegateInterface接口，并调用LoadSirManager.init进行初始化")
        }
        return delegateInterface?.initLoading()
    }

}