package com.wenyou.sociallibrary.utils

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

object SDKByteUtils {

    fun Bitmap.compressBmp2LimitSize(maxSize: Int): ByteArray {
        val baos = ByteArrayOutputStream()
        var quality = 100
        compress(Bitmap.CompressFormat.JPEG, quality, baos)
        var baosLength = baos.toByteArray().size
        while (baosLength / 1024 > maxSize) {
            baos.reset()
            quality = 0.coerceAtLeast(quality - 10)
            compress(Bitmap.CompressFormat.JPEG, quality, baos)
            baosLength = baos.toByteArray().size
            if (quality == 0) break
        }
        val bytes = baos.toByteArray()
        SDKLogUtils.i("compressBmp2LimitSize", "bytes.length=${bytes.size / 1024}KB")
        try {
            baos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bytes
    }
}