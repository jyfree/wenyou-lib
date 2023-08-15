package com.wenyou.loadsir.simple

import com.wenyou.loadsir.framework.callback.Callback


/**
 * @description LoadSir空页面
 * @date: 2021/12/16 11:59
 * @author: jy
 */
class EmptyCallback : Callback() {
    override fun onCreateView(): Int = LoadSirSimpleLayout.get().emptyLayout()
}