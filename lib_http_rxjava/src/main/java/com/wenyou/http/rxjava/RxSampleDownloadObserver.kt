package com.wenyou.http.rxjava

import com.wenyou.http.rxjava.utils.RxLogUtils
import com.wenyou.supply.download.ok.bean.DownloadInfo

abstract class RxSampleDownloadObserver(downloadInfo: DownloadInfo, tag: Any? = null) :
    RxFileDownloadObserver<DownloadInfo>(downloadInfo, tag) {
    override fun onDownloadSuccess(t: DownloadInfo) {
        RxLogUtils.i("download--onDownloadSuccess", t.toString())
    }

    override fun onDownloadFail(e: ApiException) {
        RxLogUtils.e("download--onDownloadFail--code：${e.code}--msg：${e.message}")
    }

    override fun onProgress(progress: Int) {
//        RxLogUtils.i("download--onProgress", progress)
    }
}