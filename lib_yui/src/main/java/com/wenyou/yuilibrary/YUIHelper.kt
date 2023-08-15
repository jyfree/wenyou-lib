package com.wenyou.yuilibrary

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ViewGroup.LayoutParams
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * @description
 * @date: 2021/12/16 14:20
 * @author: jy
 */
object YUIHelper {
    fun getColorRes(@ColorRes colorRes: Int) =
        ContextCompat.getColor(YUI.getContext(), colorRes)

    fun getDrawableRes(@DrawableRes drawableRes: Int): Drawable? =
        ContextCompat.getDrawable(YUI.getContext(), drawableRes)

    fun dip2px(dipValue: Float): Int = dp2px(YUI.getContext(), dipValue)

    fun sp2px(sp: Float): Float {
        val scale: Float = YUI.getContext().resources.displayMetrics.scaledDensity
        return sp * scale
    }

    private fun dp2px(context: Context?, dipValue: Float): Int {
        if (context != null) {

            if (LayoutParams.MATCH_PARENT.toFloat() == dipValue) {
                return LayoutParams.MATCH_PARENT
            }

            if (LayoutParams.WRAP_CONTENT.toFloat() == dipValue) {
                return LayoutParams.WRAP_CONTENT
            }

            val scale = context.resources.displayMetrics.density
            return (dipValue * scale + 0.5f).toInt()
        }
        return dipValue.toInt()
    }
}