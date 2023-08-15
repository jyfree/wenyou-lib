package com.wenyou.uidemo.utils

import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.wenyou.yuilibrary.selector.YSelector
import com.wenyou.yuilibrary.selector.selector.CompoundDrawableSelector

/**
 * @description Selector选择器工具类
 * @date: 2021/2/3 13:42
 * @author: jy
 */

fun View.setBgColor(bgColor: String, pressedColor: String) {
    YSelector.shapeSelector()
        .defaultBgColor(bgColor)
        .pressedBgColor(pressedColor)
        .selectorColor("#000000", "#ffffff")
        .radius(20f)
        .into(this)
}


fun TextView.setTextColor(defColor: String, pressedColor: String) {
    YSelector.colorSelector()
        .defaultColor(defColor)
        .pressedColor(pressedColor)
        .into(this)
}

fun View.setBgDrawable(@DrawableRes defDrawableRes: Int, @DrawableRes pressedDrawableRes: Int) {
    YSelector.drawableSelector()
        .defaultDrawable(defDrawableRes)
        .pressedDrawable(pressedDrawableRes)
        .selectorColor("#000000", "#ffffff")
        .into(this)
}

fun TextView.setCompoundDrawable(@DrawableRes defDrawableRes: Int, @DrawableRes pressedDrawableRes: Int, @CompoundDrawableSelector.DrawableOrientation drawableOrientation: String) {
    YSelector.compoundDrawableSelector()
        .setDrawablePadding(5f)
        .setDrawableOrientation(drawableOrientation)
        .defaultDrawable(defDrawableRes)
        .pressedDrawable(pressedDrawableRes)
        .selectorColor("#ff0000", "#000000")
        .into(this)
}