package com.wenyou.baselibrary.channel

import android.app.Activity

/**
 * 自定义状态栏style
 */
interface ChannelToolbarInterface {
    /**
     * 默认主题
     * @description 若为null,则默认是ChannelToolbarStyle.WHITE
     */
    fun defaultToolbarStyle(): ChannelToolbarStyle?

    /**
     * 自定义主题
     * @description 若设置主题为自定义，则需要在此方法设置主题。
     * 若自定义多个主题，可以利用参数activity来判断
     */
    fun customToolbarStyle(activity: Activity)

}