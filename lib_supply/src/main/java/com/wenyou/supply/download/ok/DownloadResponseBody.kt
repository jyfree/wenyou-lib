package com.wenyou.supply.download.ok

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.buffer

class DownloadResponseBody(
    private val responseBody: ResponseBody?,
    val progressListener: DownloadProgressListener? = null,
    private val isReckonSpeed: Boolean = false
) : ResponseBody() {

    override fun contentLength(): Long = responseBody?.contentLength() ?: 0L

    override fun contentType(): MediaType? = responseBody?.contentType()


    override fun source(): BufferedSource = CountingSink().buffer()

    inner class CountingSink : ForwardingSource(responseBody!!.source()) {
        private var totalBytesRead = 0L
        private var beforeTime = System.currentTimeMillis() // 前一秒
        private var secondCount: Long = 0 // 每秒的上传量

        init {
            beforeTime = System.currentTimeMillis() // 前一秒
        }

        override fun read(sink: Buffer, byteCount: Long): Long {
            val bytesRead = super.read(sink, byteCount)
            // read() returns the number of bytes read, or -1 if this source is exhausted.
            totalBytesRead += if (bytesRead != -1L) bytesRead else 0

            val currentTime = System.currentTimeMillis()
            if (currentTime - beforeTime > 1000) {
                beforeTime = currentTime
                responseBody?.contentLength()
                    ?.let { progressListener?.update(totalBytesRead, it, bytesRead == -1L) }

                //计算下载速度
                if (isReckonSpeed) {
                    val speed = secondCount / 1024f / 1024f
                    progressListener?.onSpeedChange(speed)
                    secondCount = 0
                }
            } else {
                secondCount += bytesRead
                //下载完成
                if (bytesRead == -1L && totalBytesRead == contentLength()) {
                    progressListener?.update(totalBytesRead, totalBytesRead, true)
                }
            }
            return bytesRead
        }
    }


}