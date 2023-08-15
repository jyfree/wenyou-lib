package com.wenyou.supply.download.ok

import com.wenyou.supply.download.ok.bean.DownloadInfo
import com.wenyou.supply.download.ok.chain.AbstractOutputStreamChain
import com.wenyou.supply.download.ok.chain.FileOutputStreamChain
import com.wenyou.supply.download.ok.chain.RandomFileOutputStreamChain
import okhttp3.ResponseBody
import java.io.File


object HttpDownWriteUtils {
    fun writeFileFromResponse(responseBody: ResponseBody, file: File, info: DownloadInfo) {
        val fileChain: AbstractOutputStreamChain =
            FileOutputStreamChain()
        val randomFileChain: AbstractOutputStreamChain =
            RandomFileOutputStreamChain()
        randomFileChain.next = fileChain
        randomFileChain.invoke(responseBody, file, info)
    }
}