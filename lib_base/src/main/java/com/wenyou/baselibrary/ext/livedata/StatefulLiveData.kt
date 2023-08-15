package com.wenyou.baselibrary.ext.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * @description 状态机liveData
 * @date: 2021/12/21 17:50
 * @author: jy
 */
typealias StatefulLiveData<T> = LiveData<RequestState<T>>

typealias StatefulMutableLiveData<T> = MutableLiveData<RequestState<T>>

@MainThread
inline fun <T> StatefulLiveData<T>.observeState(
    owner: LifecycleOwner,
    init: ResultBuilder<T>.() -> Unit
) {
    val result = ResultBuilder<T>().apply(init)

    observe(owner) { state ->
        when (state) {
            is RequestState.Loading -> result.onLoading(state.show)
            is RequestState.Success -> result.onSuccess(state.data)
            is RequestState.Error -> result.onError(state.errorMsg, state.exception)
        }
    }
}

@MainThread
inline fun <T> LiveData<T>.observe(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
): Observer<T> {
    val wrappedObserver = Observer<T> { t -> onChanged.invoke(t) }
    observe(owner, wrappedObserver)
    return wrappedObserver
}
