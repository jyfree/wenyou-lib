package com.wenyou.baselibrary.channel

import android.app.Activity

/**
 * 状态栏样式
 */
class ChannelToolbar : ChannelToolbarInterface {

    private var channelToolbarImpl: ChannelToolbarInterface? = null

    companion object {
        @Volatile
        private var sInstance: ChannelToolbar? = null

        fun get(): ChannelToolbar {
            if (sInstance == null) {
                synchronized(ChannelToolbar::class.java) {
                    if (sInstance == null) {
                        sInstance = ChannelToolbar()
                    }
                }
            }
            return sInstance!!
        }
    }

    fun init(channelToolbarImpl: ChannelToolbarInterface) {
        this.channelToolbarImpl = channelToolbarImpl
    }

    override fun defaultToolbarStyle(): ChannelToolbarStyle? {
        return channelToolbarImpl?.defaultToolbarStyle()
    }

    override fun customToolbarStyle(activity: Activity) {
        channelToolbarImpl?.customToolbarStyle(activity)
    }

}