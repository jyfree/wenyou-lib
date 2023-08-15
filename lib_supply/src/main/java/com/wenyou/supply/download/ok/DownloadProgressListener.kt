package com.wenyou.supply.download.ok

interface DownloadProgressListener {
    /**
     * 下载进度
     * @param read
     * @param count
     * @param done
     */
    fun update(read: Long, count: Long, done: Boolean)

    /**
     * 下载速度
     * 单位Mb/s
     */
    fun onSpeedChange(speed: Float)
}