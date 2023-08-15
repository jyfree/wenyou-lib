@file:JvmName("ViewExt")

package com.wenyou.baselibrary.ext

import android.view.View
import com.wenyou.baselibrary.R
import kotlin.math.abs


/**
 * 最近一次点击的时间
 */
private var mLastClickTime: Long = 0

/**
 * 是否是快速点击--全局作用域
 *
 * @param intervalMillis  时间间期（毫秒）
 * @return  true:是，false:不是
 */
fun isFastDoubleClick(intervalMillis: Long): Boolean {
    val time = System.currentTimeMillis()
    val timeInterval = abs(time - mLastClickTime)
    mLastClickTime = time
    return timeInterval < intervalMillis
}


/**
 * 是否是快速点击--每个view
 * @receiver View
 * @param intervalMillis 时间间期（毫秒）
 * @return Boolean
 */
fun View.isFastDoubleClickView(intervalMillis: Long): Boolean {
    return try {
        val time = System.currentTimeMillis()
        val lastTime = this.getTag(R.id.tag_view_time) as? Long ?: 0
        val timeInterval = abs(time - lastTime)
        this.setTag(R.id.tag_view_time, time)
        timeInterval < intervalMillis
    } catch (e: Exception) {
        false
    }
}
