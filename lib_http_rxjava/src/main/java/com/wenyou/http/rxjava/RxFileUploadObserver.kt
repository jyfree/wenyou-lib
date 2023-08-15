package com.wenyou.http.rxjava

import com.wenyou.supply.upload.UploadProgressImpl
import com.wenyou.supply.upload.UploadProgressListener
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 * @description
 * @date: 2021/12/16 14:35
 * @author: jy
 */
abstract class RxFileUploadObserver<T>(private var tag: Any? = null) : Observer<T> {
    private var mDisposable: Disposable? = null
    private var listener = object : UploadProgressImpl() {
        override fun onMainProgress(progress: Int) {
            onProgress(progress)
        }

        override fun onMainSpeed(uploadEnd: Boolean, speed: Float) {
            onSpeed(uploadEnd, speed)
        }

    }

    fun getUploadProgressListener(): UploadProgressListener {
        return listener
    }

    override fun onSubscribe(d: Disposable?) {
        this.mDisposable = d
        tag?.let {
            mDisposable?.let { disposable ->
                RxApiManager.get().add(it, disposable)
            }
        }
        listener.refreshStartTime()
    }

    override fun onNext(t: T) {
        onUploadSuccess(t)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        onUploadFail(ExceptionHandle.handleException(e))
        onEnd()
    }

    override fun onComplete() {
        onEnd()
        listener.uploadEnd()
    }

    private fun onEnd() {
        if (mDisposable?.isDisposed == false) {
            mDisposable?.dispose()
        }
        tag?.let {
            RxApiManager.get().remove(it)
        }
    }

    abstract fun onUploadSuccess(t: T)

    abstract fun onUploadFail(e: ApiException)

    abstract fun onProgress(progress: Int)

    abstract fun onSpeed(uploadEnd: Boolean, speed: Float)
}