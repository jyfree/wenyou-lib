package com.wenyou.loadsir.simple

import com.wenyou.loadsir.framework.callback.Callback

/**
 * @description LoadSir加载页(覆盖targetView)
 * @date: 2021/10/22 16:52
 * @author: jy
 */
class LoadingCoverCallback : Callback() {
    override fun onCreateView(): Int = LoadSirSimpleLayout.get().loadingLayout()

    //是否在显示Callback视图的时候显示原始图(SuccessView)，返回true显示，false隐藏
    override fun getSuccessVisible(): Boolean {
        return false
    }
}