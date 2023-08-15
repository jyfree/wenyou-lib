package com.wenyou.http.rxjava

import com.wenyou.http.rxjava.utils.RxLogUtils
import okhttp3.ResponseBody

abstract class RxSampleUploadObserver(tag: Any? = null) : RxFileUploadObserver<ResponseBody>(tag) {
    override fun onUploadSuccess(t: ResponseBody) {
        RxLogUtils.i("upload--onUploadSuccess", t.string())
    }

    override fun onUploadFail(e: ApiException) {
        RxLogUtils.e("upload--onUploadFail--code：${e.code}--msg：${e.message}")
    }

    override fun onProgress(progress: Int) {
//        RxLogUtils.i("upload--onProgress", progress)
    }
}