package com.wenyou.baselibrary.ext.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.*

/**
 * @description 封装liveData，处理粘性问题
 * @date: 2021/12/21 17:44
 * @author: jy
 */
//为 LiveData<Event<T>>提供类型别名，使用 EventLiveData<T> 即可
typealias EventMutableLiveData<T> = MutableLiveData<Event<T>>

typealias EventLiveData<T> = LiveData<Event<T>>

/**
 * 事件可被多个观察者消费，且每个观察者 [viewModelStore] 仅能消费一次
 *
 */
@MainThread
inline fun <T> EventLiveData<T>.observeSingleEvent(
    owner: LifecycleOwner,
    viewModelStore: ViewModelStore,
    crossinline onChanged: (T) -> Unit
): Observer<Event<T>> {
    val wrappedObserver = Observer<Event<T>> { t ->
        //数据没有被使用过则发送给调用者，否则不处理
        t.getContentIfNotHandled(viewModelStore)?.let { data ->
            onChanged.invoke(data)
        }
    }
    observe(owner, wrappedObserver)
    return wrappedObserver
}

/**
 * 整个事件只能被唯一观察者消费
 */
@MainThread
inline fun <T> EventLiveData<T>.observeSingleEvent(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
): Observer<Event<T>> {
    val wrappedObserver = Observer<Event<T>> { t ->
        //数据没有被使用过则发送给调用者，否则不处理
        t.getContentIfNotHandled()?.let { data ->
            onChanged.invoke(data)
        }
    }
    observe(owner, wrappedObserver)
    return wrappedObserver
}

/**
 * 不考虑粘性问题，和 UI 数据一样，每次都通知观察者
 */
@MainThread
inline fun <T> EventLiveData<T>.observeEvent(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
): Observer<Event<T>> {
    val wrappedObserver = Observer<Event<T>> { t ->
        onChanged.invoke(t.peekContent())
    }
    observe(owner, wrappedObserver)
    return wrappedObserver
}

fun <T> EventMutableLiveData<T>.postEventValue(value: T) {
    postValue(Event(value))
}

fun <T> EventMutableLiveData<T>.setEventValue(value: T) {
    setValue(Event(value))
}