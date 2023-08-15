package com.wenyou.yuilibrary.widget.tab

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.widget.LinearLayout

/**
 * @description
 * @date: 2021/12/16 13:44
 * @author: jy
 */
class TabItem {
    var drawableSize = 0
    var defaultDrawable: Drawable? = null
    var selectedDrawable: Drawable? = null
    var bgDrawable: Drawable? = null
    var type = 0

    @JvmField
    var params: LinearLayout.LayoutParams? = null
    var text: String? = null
    var defaultTextColor = 0
    var selectedTextColor = 0
    var selectTextStyle: Typeface = Typeface.DEFAULT
    var defaultTextStyle: Typeface = Typeface.DEFAULT
    var textSize = 0
    var selectedTextSize = 0
    var msgText: String? = null
    var showIndicator = true //是否显示indicator
    var indicatorWidth = 0 //indicator的宽度
    var indicatorHeight = 0 //indicator的高度
    var indicatorDrawable: Drawable? = null //indicator
    var canSelected: Boolean = true

    /**
     * 目前weight=1强制进行平分
     */
    @JvmField
    var weight = 0
    var messageType = MessageType.TYPE_NONE

    interface Type {
        companion object {
            const val TYPE_TAB_IMAGE = 0
            const val TYPE_TAB_TEXT = 2
            const val TYPE_TAB_INDICATOR_TEXT = 4
        }
    }

    interface MessageType {
        companion object {
            const val TYPE_NONE = 0
            const val TYPE_POINT = 1
            const val TYPE_TEXT = 2
        }
    }
}