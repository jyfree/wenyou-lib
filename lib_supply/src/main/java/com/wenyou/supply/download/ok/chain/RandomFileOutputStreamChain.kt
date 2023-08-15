package com.wenyou.supply.download.ok.chain

import com.wenyou.supply.download.ok.bean.DownloadInfo
import okhttp3.ResponseBody
import java.io.*
import java.nio.channels.FileChannel


class RandomFileOutputStreamChain : AbstractOutputStreamChain(OUTPUT_STREAM_RANDOM_FILE) {
    override fun execute(responseBody: ResponseBody, file: File, info: DownloadInfo) {
        var randomAccessFile: RandomAccessFile? = null
        var channelOut: FileChannel? = null
        var inputStream: InputStream? = null
        try {
            if (!file.parentFile.exists())
                file.parentFile.mkdirs()
            val allLength = if (0L == info.countLength)
                responseBody.contentLength()
            else
                info.readLength + responseBody
                    .contentLength()
            inputStream = responseBody.byteStream()

            randomAccessFile = RandomAccessFile(file, "rwd")
            channelOut = randomAccessFile.channel
            val mappedBuffer = channelOut!!.map(
                FileChannel.MapMode.READ_WRITE,
                info.readLength, allLength - info.readLength
            )
            val buffer = ByteArray(1024 * 1024)
            var len: Int
            while (inputStream.read(buffer).also { len = it } != -1) {
                mappedBuffer.put(buffer, 0, len)
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        } finally {
            closeIO(inputStream)
            closeIO(channelOut)
            closeIO(randomAccessFile)
        }
    }

    private fun closeIO(vararg closeables: Closeable?) {
        for (close in closeables) {
            try {
                close?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}