package com.wenyou.http.rxjava

import com.wenyou.http.rxjava.bean.SuperBaseBean
import com.wenyou.http.rxjava.bean.SuperSingleBaseBean
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @description
 * @date: 2021/12/16 14:35
 * @author: jy
 */
object RxHelper {


    fun <T, R : SuperBaseBean<T>> handleResult(): ObservableTransformer<R, T> =
        ObservableTransformer { upstream ->
            val flatMap = upstream.flatMap<T> { result ->
                if (result.success()) {
                    if (result.data() == null) {
                        Observable.error(ApiException(result.code(), result.msg()))
                    } else {
                        createData(result.data())
                    }
                } else {
                    Observable.error(
                        ApiException(
                            result.code(),
                            result.msg()
                        )
                    )
                }
            }
            flatMap.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }


    fun <T> handleSingleResult(): ObservableTransformer<T, T> = ObservableTransformer { upstream ->
        val flatMap = upstream.flatMap<T> { result ->
            result as SuperSingleBaseBean
            if (result.success()) {
                createData(result)
            } else {
                Observable.error<T>(
                    ApiException(
                        result.code(),
                        result.msg()
                    )
                )
            }
        }
        flatMap.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun <T> createData(data: T): ObservableSource<out T>? {
        return Observable.create { e ->
            try {
                e.onNext(data)
                e.onComplete()
            } catch (ex: Exception) {
                e.onError(ex)
            }
        }
    }
}