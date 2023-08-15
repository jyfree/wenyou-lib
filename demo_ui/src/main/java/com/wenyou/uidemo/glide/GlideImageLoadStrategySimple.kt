package com.wenyou.uidemo.glide

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.wenyou.yuilibrary.strategy.AlignEnum
import com.wenyou.yuilibrary.strategy.IImageLoadStrategy

/**
 * @description glide加载图片策略
 * @date: 2021/2/5 14:45
 * @author: jy
 */
class GlideImageLoadStrategySimple : IImageLoadStrategy {
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
        if (!checkContextValid(imageView.context)) {
            return
        }
        var builder = Glide.with(imageView).load(path)
        if (placeholderRes != 0) {
            builder.placeholder(placeholderRes).also { builder = it }
        }
        if (errorRes != 0) {
            builder.error(errorRes).also { builder = it }
        }
        if (circle != 0) {
            if (circle == 360) {
                builder.apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .also { builder = it }
            } else {
                builder.apply(RequestOptions.bitmapTransform(RoundedCorners(circle)))
                    .also { builder = it }
            }
        }
        when (align) {
            AlignEnum.CENTER_CROP -> builder.centerCrop()
            AlignEnum.CENTER_INSIDE -> builder.centerInside()
            AlignEnum.FIT_CENTER -> builder.fitCenter()
            else -> {
            }
        }
        if (strokeWidth > 0) {
            builder.apply(
                RequestOptions.bitmapTransform(
                    GlideCircleWithBorder(
                        strokeWidth,
                        strokeColor
                    )
                )
            ).also { builder = it }
        }
        if (timeoutMs != 0) {
            builder.timeout(timeoutMs).also { builder = it }
        }
        builder.into(imageView)
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
        if (!checkContextValid(context)) {
            callback.invoke(null)
            return
        }
        var builder = Glide.with(context).asBitmap().load(path)
        if (placeholderRes != 0) {
            builder.placeholder(placeholderRes).also { builder = it }
        }
        if (errorRes != 0) {
            builder.error(errorRes).also { builder = it }
        }
        builder.listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                callback.invoke(null)
                return false
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                callback.invoke(resource)
                return false
            }

        }).also { builder = it }
        if (timeoutMs != 0) {
            builder.timeout(timeoutMs).also { builder = it }
        }
        if (width > 0 && height > 0) {
            builder.submit(width, height)
        } else {
            builder.submit()
        }
    }

    fun checkContextValid(context: Context?): Boolean {
        if (context == null) {
            return false
        }
        if (context is Activity && context.isFinishing) {
            return false
        }
        if (context is Activity && context.isDestroyed) {
            return false
        }
        return true
    }
}