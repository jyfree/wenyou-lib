package com.wenyou.http.rxjava


import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 * @description
 * @date: 2021/12/16 14:35
 * @author: jy
 */
class RxObserver<T> : Observer<T> {
    private var doError: ((e: ApiException) -> Unit)? = null
    private var doNext: ((t: T) -> Unit?)? = null
    private var doComplete: (() -> Unit?)? = null
    private var tag: Any? = null

    constructor(
        doError: ((e: ApiException) -> Unit)? = null,
        doNext: ((t: T) -> Unit?)? = null
    ) {
        this.doError = doError
        this.doNext = doNext
    }

    constructor(
        doError: ((e: ApiException) -> Unit)? = null,
        doNext: ((t: T) -> Unit?)? = null,
        tag: Any?
    ) {
        this.doError = doError
        this.doNext = doNext
        this.tag = tag
    }

    constructor(
        doError: ((e: ApiException) -> Unit)? = null,
        doNext: ((t: T) -> Unit?)? = null,
        doComplete: (() -> Unit?)? = null,
        tag: Any?
    ) {
        this.doError = doError
        this.doNext = doNext
        this.doComplete = doComplete
        this.tag = tag
    }

    constructor(doNext: ((t: T) -> Unit?)? = null) {
        this.doNext = doNext
    }

    private var mDisposable: Disposable? = null

    override fun onSubscribe(d: Disposable) {
        mDisposable = d
        tag?.let {
            RxApiManager.get().add(it, d)
        }
    }

    override fun onComplete() {
        doComplete?.invoke()
        onEnd()
    }

    override fun onNext(t: T) {
        doNext?.invoke(t)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        doError?.invoke(ExceptionHandle.handleException(e))
        onEnd()
    }

    private fun onEnd() {
        if (mDisposable?.isDisposed == false) {
            mDisposable?.dispose()
        }
        tag?.let {
            RxApiManager.get().remove(it)
        }
    }
}