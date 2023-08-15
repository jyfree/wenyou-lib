package com.wenyou.yuilibrary.strategy

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView

/**
 * @description 图片加载策略管理
 * @date: 2021/2/4 15:39
 * @author: jy
 */
class ImageLoader : IImageLoadStrategy {

    private var strategy: IImageLoadStrategy? = null


    companion object {
        @Volatile
        private var sInstance: ImageLoader? = null

        fun get(): ImageLoader {
            if (sInstance == null) {
                synchronized(ImageLoader::class.java) {
                    if (sInstance == null) {
                        sInstance = ImageLoader()
                    }
                }
            }
            return sInstance!!
        }
    }

    /**
     * 初始化图片加载的策略
     *
     * @param strategy 加载策略
     */
    fun init(strategy: IImageLoadStrategy) {
        this.strategy = strategy
    }


    /**
     * 检测是否调用初始化方法
     */
    private fun initialize() {
        if (strategy == null) {
            throw ExceptionInInitializerError("请先在全局Application中调用ImageLoader.get().init(strategy)初始化！")
        }
    }

    override fun loadImage(
        imageView: ImageView,
        path: Any?,
        placeholderRes: Int,
        errorRes: Int,
        circle: Int,
        align: AlignEnum,
        strokeWidth: Float,
        strokeColor: Int,
        timeoutMs: Int
    ) {
        initialize()
        strategy?.loadImage(
            imageView,
            path,
            placeholderRes,
            errorRes,
            circle,
            align,
            strokeWidth,
            strokeColor,
            timeoutMs
        )
    }

    override fun loadBitmap(
        context: Context,
        path: Any,
        placeholderRes: Int,
        errorRes: Int,
        callback: (Bitmap?) -> Unit,
        width: Int,
        height: Int,
        timeoutMs: Int
    ) {
        initialize()
        strategy?.loadBitmap(
            context,
            path,
            placeholderRes,
            errorRes,
            callback,
            width,
            height,
            timeoutMs
        )
    }


}