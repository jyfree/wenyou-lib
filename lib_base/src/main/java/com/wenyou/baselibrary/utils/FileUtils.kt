package com.wenyou.baselibrary.utils

import android.os.Environment
import android.os.StatFs
import java.io.File

/**
 * @description
 * @date: 2021/12/16 13:39
 * @author: jy
 */
object FileUtils {

    fun getSdcardPath(): String {
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED != state) {
            YLogUtils.e("sdcard没有挂载")
            return ""
        }
        return BaseUtils.getApp().getExternalFilesDir("").toString() + "/"
    }

    /**
     * 获得sd卡剩余容量，即可用大小
     */
    fun getSDAvailableSize(): Long {
        val externalStorageDirectory = Environment.getExternalStorageDirectory()
        val statFs = StatFs(externalStorageDirectory.path)
        val blockSize: Long
        val availableBlocks: Long
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statFs.blockSizeLong
            availableBlocks = statFs.availableBlocksLong
        } else {
            blockSize = statFs.blockSize.toLong()
            availableBlocks = statFs.availableBlocks.toLong()
        }
        return blockSize * availableBlocks
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return `true`: 存在<br></br>`false`: 不存在
     */
    fun isFileExists(file: File?): Boolean {
        return file != null && file.exists()
    }
}