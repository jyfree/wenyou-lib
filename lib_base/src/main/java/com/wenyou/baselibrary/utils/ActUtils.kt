package com.wenyou.baselibrary.utils

import android.app.Activity
import android.content.Context

/**
 * @description 上下文校验
 * @date: 2021/12/16 13:38
 * @author: jy
 */
object ActUtils {
    const val TAG = "ActUtils"

    /**
     * 校验 context 的有效性
     *
     * @param context
     * @return true 有效，false 无效
     */
    fun checkContextValid(context: Context?): Boolean {
        if (context == null) {
            YLogUtils.w(TAG, "context:$context is null")
            return false
        }
        if (context is Activity && context.isFinishing) {
            YLogUtils.w(TAG, "activity:$context is finishing")
            return false
        }

        if (context is Activity && context.isDestroyed) {
            YLogUtils.w(TAG, "activity:$context is isDestroyed")
            return false
        }
        return true
    }
}