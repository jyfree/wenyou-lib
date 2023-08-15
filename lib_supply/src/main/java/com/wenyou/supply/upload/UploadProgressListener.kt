package com.wenyou.supply.upload

interface UploadProgressListener {
    /**
     * 总大小
     * 注意！多文件时调用
     */
    fun onTotalLength(totalLength: Long)

    /**
     * 上传进度
     * @param bytesWritten 已下载大小
     * @param contentLength 文件大小
     */
    fun onProgressChange(bytesWritten: Long, contentLength: Long)

    /**
     * 上传速度
     * 单位Mb/s
     */
    fun onSpeedChange(speed: Float)
}