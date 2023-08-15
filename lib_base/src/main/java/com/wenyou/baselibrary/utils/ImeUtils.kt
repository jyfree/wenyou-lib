package com.wenyou.baselibrary.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @description 软键盘工具
 * @date: 2021/12/16 13:39
 * @author: jy
 */
object ImeUtils {
    fun hideIME(activity: Activity) {
        val view = activity.currentFocus
        if (null != view) {
            hideIME(activity, view)
        }
    }

    fun hideIME(context: Context?, v: View?) {
        if (context == null || v == null) {
            return
        }
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }

    fun showIME(activity: Activity, view: View?) {
        var view = view
        if (null == view) {
            view = activity.currentFocus
            if (null == view) {
                return
            }
        }
        (activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    fun showIME(activity: Activity, view: View?, flag: Int) {
        var view = view
        if (null == view) {
            view = activity.currentFocus
            if (null == view) {
                return
            }
        }
        (activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
            view,
            flag
        )
    }
}