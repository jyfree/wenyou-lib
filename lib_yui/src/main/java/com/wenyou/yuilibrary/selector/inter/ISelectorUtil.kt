package com.wenyou.yuilibrary.selector.inter;

import android.view.View
import com.wenyou.yuilibrary.selector.YSelector

/**
 * @description
 * @date: 2021/12/16 14:16
 * @author: jy
 */
interface ISelectorUtil<T, V : View> {
    /**
     * 目标view
     * @param v 需要设置样式的view
     */
    fun into(view: V): YSelector

    fun build(): T
}