package com.wenyou.supply.upload

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.Buffer
import okio.BufferedSink
import okio.ForwardingSink
import okio.buffer
import java.io.File

/**
 * 上传文件
 */
class UploadFileRequestBody(
    file: File,
    val uploadProgressListener: UploadProgressListener? = null,
    private val isReckonSpeed: Boolean = false
) : RequestBody() {
    private val mRequestBody: RequestBody =
        create("application/octet-stream".toMediaTypeOrNull(), file)

    override fun contentType(): MediaType? = mRequestBody.contentType()

    override fun contentLength(): Long = mRequestBody.contentLength()

    override fun writeTo(sink: BufferedSink) {
        val countingSink = CountingSink(sink)
        val bufferedSink = countingSink.buffer()
        mRequestBody.writeTo(bufferedSink)
        bufferedSink.flush()
    }

    inner class CountingSink(delegate: BufferedSink) : ForwardingSink(delegate) {
        private var bytesWritten: Long = 0
        private var beforeTime = System.currentTimeMillis() // 前一秒
        private var secondCount: Long = 0 // 每秒的上传量

        init {
            beforeTime = System.currentTimeMillis() // 前一秒
        }

        override fun write(source: Buffer, byteCount: Long) {
            super.write(source, byteCount)
            bytesWritten += byteCount
            if (isReckonSpeed) {
                val currentTime = System.currentTimeMillis()
                if (currentTime - beforeTime > 1000) {
                    beforeTime = currentTime
                    val speed = secondCount / 1024f / 1024f
                    uploadProgressListener?.onSpeedChange(speed)
                    secondCount = 0
                } else {
                    secondCount += byteCount
                }
            }
            uploadProgressListener?.onProgressChange(bytesWritten, contentLength())
        }
    }
}