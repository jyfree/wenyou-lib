package com.wenyou.supply.download.sys

import java.io.File

/**
 * @description
 * @date: 2021/12/16 14:37
 * @author: jy
 */
interface DownloadView {
    fun downloadSuccess(file: File)
    fun downloadFailed(error: String) {}
}