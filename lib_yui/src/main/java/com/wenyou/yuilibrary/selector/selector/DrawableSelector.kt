package com.wenyou.yuilibrary.selector.selector;

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.wenyou.yuilibrary.YUIHelper
import com.wenyou.yuilibrary.selector.YSelector
import com.wenyou.yuilibrary.selector.inter.ISelectorUtil

/**
 * @description
 * @date: 2021/12/16 14:16
 * @author: jy
 */
class DrawableSelector private constructor() : ISelectorUtil<Drawable, View> {

    companion object {
        fun getInstance(): DrawableSelector = DrawableSelector()
    }

    //不可点击
    private var mDisabledDrawable: Drawable? = null
    //选中
    private var mSelectedDrawable: Drawable? = null
    //获取焦点
    private var mFocusedDrawable: Drawable? = null
    //触摸
    private var mPressedDrawable: Drawable? = null
    //正常
    private var mNormalDrawable: Drawable? = null
    //是否设置TextView|button颜色选择器
    private var isSelectorColor: Boolean = false
    private var mColorStateList: ColorStateList? = null

    private var hasSetDisabledDrawable = false
    private var hasSetPressedDrawable = false
    private var hasSetSelectedDrawable = false
    private var hasSetFocusedDrawable = false

    init {
        mNormalDrawable = ColorDrawable(Color.TRANSPARENT)
    }

    fun defaultDrawable(drawable: Drawable): DrawableSelector {
        mNormalDrawable = drawable
        if (!hasSetDisabledDrawable)
            mDisabledDrawable = drawable
        if (!hasSetPressedDrawable)
            mPressedDrawable = drawable
        if (!hasSetSelectedDrawable)
            mSelectedDrawable = drawable
        if (!hasSetFocusedDrawable)
            mFocusedDrawable = drawable
        return this
    }

    fun disabledDrawable(drawable: Drawable): DrawableSelector {
        mDisabledDrawable = drawable
        hasSetDisabledDrawable = true
        return this
    }

    fun pressedDrawable(drawable: Drawable): DrawableSelector {
        mPressedDrawable = drawable
        hasSetPressedDrawable = true
        return this
    }

    fun selectedDrawable(drawable: Drawable): DrawableSelector {
        mSelectedDrawable = drawable
        hasSetSelectedDrawable = true
        return this
    }

    fun focusedDrawable(drawable: Drawable): DrawableSelector {
        mFocusedDrawable = drawable
        hasSetFocusedDrawable = true
        return this
    }

    fun defaultDrawable(@DrawableRes drawableRes: Int): DrawableSelector {
        val drawable = YUIHelper.getDrawableRes(drawableRes)
        return if (drawable == null) this else defaultDrawable(drawable)
    }

    fun disabledDrawable(@DrawableRes drawableRes: Int): DrawableSelector {
        val drawable = YUIHelper.getDrawableRes(drawableRes)
        return if (drawable == null) this else disabledDrawable(drawable)
    }

    fun pressedDrawable(@DrawableRes drawableRes: Int): DrawableSelector {
        val drawable = YUIHelper.getDrawableRes(drawableRes)
        return if (drawable == null) this else pressedDrawable(drawable)
    }

    fun selectedDrawable(@DrawableRes drawableRes: Int): DrawableSelector {
        val drawable = YUIHelper.getDrawableRes(drawableRes)
        return if (drawable == null) this else selectedDrawable(drawable)
    }

    fun focusedDrawable(@DrawableRes drawableRes: Int): DrawableSelector {
        val drawable = YUIHelper.getDrawableRes(drawableRes)
        return if (drawable == null) this else focusedDrawable(drawable)
    }

    /**
     * .
     * 背景状态选择器（背景Drawable）
     *
     * @param pressedDrawable 触摸颜色 例：Context.getResources.getDrawable(R.drawable/mipmap.xxx)
     * @param normalDrawable  正常颜色 例：Context.getResources.getDrawable(R.drawable/mipmap.xxx)
     */
    fun selectorBackground(pressedDrawable: Drawable, normalDrawable: Drawable): DrawableSelector {
        this.mPressedDrawable = pressedDrawable
        this.mNormalDrawable = normalDrawable
        this.hasSetPressedDrawable = true
        return this
    }

    /**
     * 设置背景选择器同时设置字体颜色颜色器
     *
     * @param pressedColorResId 触摸颜色 例：R.color.colorPrimary
     * @param normalColorResId  正常颜色 例：R.color.colorPrimary
     */
    fun selectorColor(@ColorRes pressedColorResId: Int, @ColorRes normalColorResId: Int): DrawableSelector {
        mColorStateList = ColorSelector.getInstance()
            .pressedColor(pressedColorResId)
            .defaultColor(normalColorResId)
            .build()
        this.isSelectorColor = true
        return this
    }

    /**
     * 设置背景选择器同时设置字体颜色颜色器
     *
     * @param pressedColor 触摸颜色 例：#ffffff
     * @param normalColor  正常颜色 例：#ffffff
     */
    fun selectorColor(pressedColor: String, normalColor: String): DrawableSelector {
        mColorStateList = ColorSelector.getInstance()
            .pressedColor(pressedColor)
            .defaultColor(normalColor)
            .build()
        this.isSelectorColor = true
        return this
    }

    override fun into(view: View): YSelector {
        if (view !is Button) { //针对selector作用在非button无效问题
            view.isClickable = true
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.background = create()
        } else {
            view.setBackgroundDrawable(create())
        }
        if (isSelectorColor) {
            try {
                if (view is TextView) {
                    view.setTextColor(mColorStateList)
                } else if (view is Button) {
                    view.setTextColor(mColorStateList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw ExceptionInInitializerError("设置字体颜色选择器（Selector）请传入TextView（包括Button）！！！")
            }

        }
        return YSelector
    }


    override fun build(): Drawable = create()

    /**
     * 创建触摸颜色变化
     */
    private fun create(): StateListDrawable {
        val selector = StateListDrawable()
        if (hasSetDisabledDrawable)
            selector.addState(intArrayOf(-android.R.attr.state_enabled), mDisabledDrawable)
        if (hasSetPressedDrawable)
            selector.addState(intArrayOf(android.R.attr.state_pressed), mPressedDrawable)
        if (hasSetSelectedDrawable)
            selector.addState(intArrayOf(android.R.attr.state_selected), mSelectedDrawable)
        if (hasSetFocusedDrawable)
            selector.addState(intArrayOf(android.R.attr.state_focused), mFocusedDrawable)
        selector.addState(intArrayOf(), mNormalDrawable)
        return selector
    }
}