package com.wenyou.baselibrary.base.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import com.wenyou.baselibrary.utils.ScreenResolutionUtils

/**
 * @description Dialog基类
 * @date: 2021/12/16 11:53
 * @author: jy
 */
abstract class BaseDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {

    /**
     * @param gravity 位置
     * Width：屏幕宽度
     * height：自适应
     */
    protected fun initMatchWidthContentView(gravity: Int) {

        val pair = ScreenResolutionUtils.getResolution(context)
        initCustomWidthContentView(gravity, pair.first)
    }

    /**
     * @param gravity 位置
     * Width：自定义
     * height：自适应
     */
    protected fun initCustomWidthContentView(gravity: Int, width: Int) {
        setContentView(getLayoutResId())
        window?.setGravity(gravity)
        val lp = window?.attributes
        lp?.width = width
        window?.attributes = lp
        setDialogCancelable(true)
    }

    /**
     * @param gravity 位置
     * Width：自定义
     * height：自定义
     */
    protected fun initCustomWidthAndHeightContentView(gravity: Int, width: Int, height: Int) {
        setContentView(getLayoutResId())
        window?.setGravity(gravity)
        val lp = window?.attributes
        lp?.width = width
        lp?.height = height
        window?.attributes = lp
        setDialogCancelable(true)
    }

    /**
     *
     * Width：屏幕宽度
     * height：屏幕高度
     */
    protected fun initFullScreenContentView() {
        setContentView(getLayoutResId())
        val lp = window?.attributes
        lp?.gravity = Gravity.BOTTOM
        lp?.width = ViewGroup.LayoutParams.MATCH_PARENT
        lp?.height = ViewGroup.LayoutParams.MATCH_PARENT
        window?.attributes = lp
        setDialogCancelable(true)
    }

    protected open fun initCustomWidthAndYContentView(
        gravity: Int,
        width: Int,
        y: Int
    ) {
        setContentView(getLayoutResId())
        window?.setGravity(gravity)
        val lp = window?.attributes
        lp?.width = width
        lp?.y = y
        window?.attributes = lp
        setDialogCancelable(true)
    }

    protected open fun initDefaultContentView(layout: Int, gravity: Int) {
        setContentView(getLayoutResId())
        window?.setGravity(gravity)
        val lp = window?.attributes
        window?.attributes = lp
        setDialogCancelable(true)
    }

    /**
     * @param cancelable 可否触控取消dialog
     */
    protected fun setDialogCancelable(cancelable: Boolean) {
        this.setCanceledOnTouchOutside(cancelable)
        this.setCancelable(cancelable)
    }

    protected abstract fun getLayoutResId(): Int


}
