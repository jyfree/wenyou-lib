package com.wenyou.loadsir

import com.wenyou.baselibrary.loadsir.ILoading
import com.wenyou.baselibrary.loadsir.OnLoadSirListener
import com.wenyou.loadsir.framework.callback.SuccessCallback
import com.wenyou.loadsir.framework.core.LoadService
import com.wenyou.loadsir.framework.core.LoadSir
import com.wenyou.loadsir.simple.*

/**
 * @description 菊花实现
 * TODO 注意！！若注册target为fragment的rootView，则rootView设置margin无效，需要使用padding来代替
 * @date: 2021/12/16 12:01
 * @author: jy
 */
class ILoadingImpl : ILoading {

    private var loadService: LoadService<*>? = null

    override fun getLoad(): LoadService<*>? {
        return loadService
    }

    override fun register(target: Any) {
        loadService = LoadSir.getDefault().register(target)
    }

    override fun register(
        target: Any,
        onLoadSirListener: OnLoadSirListener
    ) {
        loadService = LoadSir.getDefault().register(
            target
        ) {
            onLoadSirListener.onReload(it)
        }
    }

    override fun showLoadingView(show: Boolean) {
        loadService?.showCallback(if (show) LoadingCallback::class.java else SuccessCallback::class.java)
    }

    override fun showLoadingCoverView(show: Boolean) {
        loadService?.showCallback(if (show) LoadingCoverCallback::class.java else SuccessCallback::class.java)
    }

    override fun showAnimateView() {
        loadService?.showCallback(AnimateCallback::class.java)
    }

    override fun showErrorView() {
        loadService?.showCallback(ErrorCallback::class.java)
    }

    override fun showEmptyView() {
        loadService?.showCallback(EmptyCallback::class.java)
    }

}