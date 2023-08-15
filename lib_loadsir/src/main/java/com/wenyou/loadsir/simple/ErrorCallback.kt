package com.wenyou.loadsir.simple

import com.wenyou.loadsir.framework.callback.Callback

/**
 * @description LoadSir错误页
 * @date: 2021/12/16 11:59
 * @author: jy
 */
class ErrorCallback : Callback() {
    override fun onCreateView(): Int = LoadSirSimpleLayout.get().errorLayout()
}