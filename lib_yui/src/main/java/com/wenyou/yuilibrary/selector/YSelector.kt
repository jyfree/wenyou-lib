package com.wenyou.yuilibrary.selector;

import com.wenyou.yuilibrary.selector.selector.ColorSelector
import com.wenyou.yuilibrary.selector.selector.CompoundDrawableSelector
import com.wenyou.yuilibrary.selector.selector.DrawableSelector
import com.wenyou.yuilibrary.selector.selector.ShapeSelector

/**
 * @description
 * @date: 2021/12/16 14:16
 * @author: jy
 */
object YSelector {


    /**
     * 设置样式（主要是椭圆和矩形）
     */
    fun shapeSelector(): ShapeSelector = ShapeSelector.getInstance()

    /**
     * Color字体颜色选择器
     */
    fun colorSelector(): ColorSelector = ColorSelector.getInstance()


    /**
     * Drawable背景选择器
     */
    fun drawableSelector(): DrawableSelector = DrawableSelector.getInstance()

    /**
     * Drawable方位选择器
     */
    fun compoundDrawableSelector(): CompoundDrawableSelector =
        CompoundDrawableSelector.getInstance()
}