package com.wenyou.yuilibrary.utils

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.AttrRes
import androidx.annotation.StyleableRes
import androidx.appcompat.content.res.AppCompatResources

/**
 * @description 主题工具类
 * @date: 2021/2/5 19:16
 * @author: jy
 */
object ThemeUtils {

    fun resolveBoolean(
        context: Context, @AttrRes attr: Int,
        fallback: Boolean
    ): Boolean {
        val a = context.obtainStyledAttributes(intArrayOf(attr))
        return try {
            a.getBoolean(0, fallback)
        } finally {
            a.recycle()
        }
    }

    fun resolveFloat(
        context: Context,
        @AttrRes attrRes: Int,
        defaultValue: Float
    ): Float {
        val a = context.obtainStyledAttributes(intArrayOf(attrRes))
        return try {
            a.getFloat(0, defaultValue)
        } finally {
            a.recycle()
        }
    }

    /**
     * 获取Drawable属性（兼容VectorDrawable）
     *
     * @param context
     * @param typedArray
     * @param index
     * @return
     */
    fun getDrawableAttrRes(
        context: Context,
        typedArray: TypedArray, @StyleableRes index: Int
    ): Drawable? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return typedArray.getDrawable(index)
        } else {
            val resourceId = typedArray.getResourceId(index, -1)
            if (resourceId != -1) {
                return AppCompatResources.getDrawable(context, resourceId)
            }
        }
        return null
    }


}