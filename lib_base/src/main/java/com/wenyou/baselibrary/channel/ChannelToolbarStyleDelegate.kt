package com.wenyou.baselibrary.channel

import android.app.Activity
import android.graphics.Color
import com.wenyou.baselibrary.utils.BarUtils
import kotlin.reflect.KProperty

/**
 * @description Toolbar样式代理
 * @date: 2021/9/15 18:42
 * @author: jy
 */
class ChannelToolbarStyleDelegate {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): ChannelToolbarStyle {
        return ChannelToolbarStyle.WHITE
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: ChannelToolbarStyle) {
        val activity = thisRef as Activity
        when (value) {
            ChannelToolbarStyle.TRANSLATE -> {
                BarUtils.setStatusBarFullTransparent(activity)
                BarUtils.setStatusBarColor(activity, Color.TRANSPARENT)
            }
            ChannelToolbarStyle.CUSTOM -> {
                ChannelToolbar.get().customToolbarStyle(activity)
            }
            ChannelToolbarStyle.BLACK -> {
                BarUtils.setStatusBarColor(activity, Color.BLACK, true)
                BarUtils.setLightMode(activity)
            }
            else -> {
                BarUtils.setStatusBarColor(activity, Color.WHITE, true)
                BarUtils.setDarkMode(activity)
            }
        }
    }
}