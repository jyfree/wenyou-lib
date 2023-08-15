package com.wenyou.yuilibrary.span

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

/**
 * @description 富文本点击
 * @date: 2021/12/16 14:18
 * @author: jy
 */
class CustomClickableSpan(
    private val clickInvoke: (view: View) -> Unit,
    private val updateDrawStateInvoke: ((ds: TextPaint) -> Unit)? = null
) : ClickableSpan() {
    override fun onClick(widget: View) {
        clickInvoke.invoke(widget)
    }

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        if (updateDrawStateInvoke == null) {
            super.updateDrawState(ds)
        } else {
            updateDrawStateInvoke.invoke(ds)
        }
    }
}