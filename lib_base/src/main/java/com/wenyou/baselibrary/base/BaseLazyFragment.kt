package com.wenyou.baselibrary.base

import androidx.viewbinding.ViewBinding

/**
 * @description ViewBinding 懒加载
 * @date: 2021/4/16 11:52
 * @author: jy
 */
abstract class BaseLazyFragment<VB : ViewBinding> : BaseFragment<VB>() {
    private var isFirstLoad = true
    override fun onResume() {
        super.onResume()
        if (isFirstLoad) {
            lazyLoad()
            isFirstLoad = false
        }
        visibleToUser()
    }

    /**
     * 只有在Fragment第一次创建且第一次对用户可见
     */
    protected abstract fun lazyLoad()

    /**
     * 每次在Fragment与用户可见
     */
    protected abstract fun visibleToUser()
}