package com.wenyou.simpledemo.download

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import com.wenyou.baselibrary.utils.YLogUtils
import com.wenyou.simpledemo.utils.SimpleUtils
import com.wenyou.supply.download.sys.DownloadPresenter
import com.wenyou.supply.download.sys.DownloadPresenterIml
import com.wenyou.supply.download.sys.DownloadView
import java.io.File


/**
 * @description 资源下载服务
 * @date: 2021/12/16 14:37
 * @author: jy
 */
class ApkSimpleDownloadServer(private var context: Context) : DownloadView {

    private var downloadPresenter: DownloadPresenter? = null

    fun startDownload(url: String, path: String?) {
        if (downloadPresenter == null) {
            downloadPresenter =
                DownloadPresenterIml(
                    context,
                    context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager,
                    this
                )
        }
        if (path.isNullOrEmpty()) {
            downloadPresenter?.requestDownload(arrayOf(url))
        } else {
            downloadPresenter?.requestDownload(arrayOf(url), path)
        }
    }

    fun release() {
        downloadPresenter?.unregisterReceiver()
    }

    override fun downloadSuccess(file: File) {
        YLogUtils.i("DownloadManager", "downloadSuccess", file)
        if (file.exists()) {
            SimpleUtils.openFile(context, file)
        }
    }

    override fun downloadFailed(error: String) {
        super.downloadFailed(error)
        YLogUtils.e("DownloadManager", "downloadFailed", error)

    }
}