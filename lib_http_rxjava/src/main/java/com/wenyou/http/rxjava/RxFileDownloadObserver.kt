package com.wenyou.http.rxjava

import android.os.Handler
import android.os.Looper
import com.wenyou.supply.download.ok.DownloadProgressListener
import com.wenyou.supply.download.ok.bean.DownState
import com.wenyou.supply.download.ok.bean.DownloadInfo
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable


abstract class RxFileDownloadObserver<T>(
    private var downInfo: DownloadInfo,
    private var tag: Any? = null
) : Observer<T>,
    DownloadProgressListener {

    private var mDisposable: Disposable? = null
    private var startTime = System.currentTimeMillis()
    private var handler: Handler? = null

    init {
        handler = Handler(Looper.getMainLooper())
    }


    override fun onSubscribe(d: Disposable?) {
        mDisposable = d
        tag?.let {
            mDisposable?.let { disposable ->
                RxApiManager.get().add(it, disposable)
            }
        }
        startTime = System.currentTimeMillis()
        downInfo.setState(DownState.START)
    }

    override fun onNext(t: T) {
        downInfo.setState(DownState.DOWN)
        onDownloadSuccess(t)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        downInfo.setState(DownState.ERROR)
        onDownloadFail(ExceptionHandle.handleException(e))
        onEnd()
    }

    override fun onComplete() {
        downInfo.setState(DownState.FINISH)
        downInfo.readLength = downInfo.countLength
        onEnd()
        downloadEnd()
    }

    private fun onEnd() {
        if (mDisposable?.isDisposed == false) {
            mDisposable?.dispose()
        }
        tag?.let {
            RxApiManager.get().remove(it)
        }
    }


    /**
     * 下载结束，计算整个上传速度
     */
    private fun downloadEnd() {
        if (downInfo.countLength != 0L) {
            val time = (System.currentTimeMillis() - startTime) / 1000
            val speed = downInfo.countLength / 1024f / 1024f / time
            onSpeed(true, speed)
        }
    }

    override fun update(read: Long, count: Long, done: Boolean) {
        var readNew = read
        if (downInfo.countLength > count) {
            readNew = downInfo.countLength - count + read
        } else {
            downInfo.countLength = count
        }
        downInfo.readLength = readNew

        val progress = (downInfo.readLength * 100 / downInfo.countLength).toInt()
        handler?.post {
            onProgress(progress)
        }
    }

    override fun onSpeedChange(speed: Float) {
        handler?.post {
            onSpeed(false, speed)
        }
    }

    abstract fun onDownloadSuccess(t: T)

    abstract fun onDownloadFail(e: ApiException)

    abstract fun onProgress(progress: Int)

    abstract fun onSpeed(downloadEnd: Boolean, speed: Float)

}