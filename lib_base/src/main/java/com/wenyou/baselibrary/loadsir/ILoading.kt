package com.wenyou.baselibrary.loadsir

/**
 * @description 菊花接口
 * @date: 2021/12/16 12:00
 * @author: jy
 */
open interface ILoading {

    /**
     * 获取加载服务
     */
    fun getLoad(): Any?

    /**
     * 注册目标view
     */
    fun register(target: Any)

    /**
     * 注册目标view
     */
    fun register(target: Any, onLoadSirListener: OnLoadSirListener)

    /**
     * 显示|隐藏菊花（不覆盖目标view）
     */
    fun showLoadingView(show: Boolean)

    /**
     * 显示|隐藏菊花（覆盖目标view）
     */
    fun showLoadingCoverView(show: Boolean)

    /**
     * 显示动画view
     */
    fun showAnimateView()

    /**
     * 显示错误view
     */
    fun showErrorView()

    /**
     * 显示空view
     */
    fun showEmptyView()
}