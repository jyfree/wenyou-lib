package com.wenyou.simpledemo.ui

import android.content.Context
import android.view.Gravity
import com.wenyou.baselibrary.base.dialog.BaseDialog
import com.wenyou.baselibrary.utils.YUnitUtils
import com.wenyou.simpledemo.R

/**
 * @description 对话框示例
 * @date: 2021/12/16 16:58
 * @author: jy
 */
class DialogSimple(context: Context, themeResId: Int) : BaseDialog(context, themeResId) {
    override fun getLayoutResId(): Int = R.layout.simple_dialog

    init {
        initCustomWidthAndHeightContentView(
            Gravity.CENTER,
            YUnitUtils.dp2px(200f),
            YUnitUtils.dp2px(200f)
        )
    }
}