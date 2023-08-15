package com.wenyou.yuilibrary.widget.nine

import android.content.Context
import android.widget.ImageView

/**
 * @description
 * @date: 2021/12/16 14:23
 * @author: jy
 */
abstract class NineGridViewAdapter(val imageInfo: List<ImageInfo>) {
    /**
     * 如果要实现图片点击的逻辑，重写此方法即可
     */
    open fun onImageItemClick(
        context: Context,
        nineGridView: NineGridView,
        index: Int,
        imageInfo: List<ImageInfo>
    ) {
    }

    /**
     * 生成ImageView容器的方式，默认使用NineGridImageViewWrapper类，即点击图片后，图片会有蒙板效果
     * 如果需要自定义图片展示效果，重写此方法即可
     */
    fun generateImageView(context: Context): ImageView {
        return NineGridViewWrapper(context)
    }
}