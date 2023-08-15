package com.wenyou.supply.download.ok.chain

import com.wenyou.supply.download.ok.bean.DownloadInfo
import okhttp3.ResponseBody
import java.io.*


class FileOutputStreamChain : AbstractOutputStreamChain(OUTPUT_STREAM_FILE) {
    override fun execute(responseBody: ResponseBody, file: File, info: DownloadInfo) {
        if (!file.parentFile.exists())
            file.parentFile.mkdirs()

        var inputStream: InputStream? = null
        var bos: BufferedOutputStream? = null
        try {
            inputStream = responseBody.byteStream()
            bos = BufferedOutputStream(FileOutputStream(file))
            val buffer = ByteArray(1024 * 1024)
            var len: Int
            while (inputStream.read(buffer).also { len = it } != -1) {
                bos.write(buffer, 0, len)
            }
            bos.flush()
        } catch (e: Exception) {
            throw Exception(e.message)
        } finally {
            closeIO(inputStream)
            closeIO(bos)
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