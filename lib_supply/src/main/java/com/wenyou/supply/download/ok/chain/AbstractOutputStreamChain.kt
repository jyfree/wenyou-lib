package com.wenyou.supply.download.ok.chain

import com.wenyou.supply.download.ok.bean.DownloadInfo
import okhttp3.ResponseBody
import java.io.File


abstract class AbstractOutputStreamChain(private val type: Int) {
    companion object {
        const val OUTPUT_STREAM_FILE = 1
        const val OUTPUT_STREAM_RANDOM_FILE = 2
    }

    var next: AbstractOutputStreamChain? = null

    fun invoke(responseBody: ResponseBody, file: File, info: DownloadInfo) {
        val allLength = if (0L == info.countLength)
            responseBody.contentLength()
        else
            info.readLength + responseBody
                .contentLength()
        val level = if (allLength > 0) OUTPUT_STREAM_RANDOM_FILE else OUTPUT_STREAM_FILE
        if (level == type) {
            execute(responseBody, file, info)
        } else {
            next?.invoke(responseBody, file, info)
        }
    }

    abstract fun execute(responseBody: ResponseBody, file: File, info: DownloadInfo)
}