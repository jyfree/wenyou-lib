@file:JvmName("ViewModelExt")

package com.wenyou.http.rxjava.viewmodel

import autodispose2.autoDispose
import com.wenyou.http.rxjava.ApiException
import com.wenyou.http.rxjava.RxObserver
import com.wenyou.http.rxjava.utils.RxLogUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * 注意！若使用AndroidLifecycleScopeProvider.from(getLifecycleOwner())绑定生命周期
 * fragment执行onStop会自动取消Rxjava请求
 * 此处viewModel的扩展不引用getLifecycleOwner
 */


/**
 * 数据转换
 * @param request RX请求
 * @param success 成功处理
 * @param failed 失败处理
 * @param composer 数据转换格式
 * @param needLifeCycle 是否需要绑定生命周期
 * @param tag tag标记，可用RxApiManager取消RX
 *
 */
fun <T, R> RxViewModel.call(
    request: () -> Observable<R>,
    success: (T) -> Unit,
    failed: (ApiException) -> Unit =
        { ex -> RxLogUtils.e("request error->code：${ex.code}，message：${ex.message}") },
    composer: ObservableTransformer<R, T>,
    needLifeCycle: Boolean = true,
    tag: Any? = null
) {
    val observer = request.invoke().compose(composer)

    val rxObserver = RxObserver<T>(doNext = {
        success.invoke(it)
    }, doError = {
        failed.invoke(it)
    }, tag = tag)

    if (needLifeCycle) {
        observer.autoDispose(this).subscribe(rxObserver)
    } else {
        observer.subscribe(rxObserver)
    }
}


/**
 * 不进行数据转换
 * @param request RX请求
 * @param success 成功处理
 * @param failed 失败处理
 * @param needLifeCycle 是否需要绑定生命周期
 * @param tag tag标记，可用RxApiManager取消RX
 */
fun <T> RxViewModel.call(
    request: () -> Observable<T>,
    success: (T) -> Unit,
    failed: (ApiException) -> Unit =
        { ex -> RxLogUtils.e("request error->code：${ex.code}，message：${ex.message}") },
    needLifeCycle: Boolean = true,
    tag: Any? = null
) {
    val observer = request.invoke()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    val rxObserver = RxObserver<T>(doNext = {
        success.invoke(it)
    }, doError = {
        failed.invoke(it)
    }, tag = tag)

    if (needLifeCycle) {
        observer.autoDispose(this).subscribe(rxObserver)
    } else {
        observer.subscribe(rxObserver)
    }

}