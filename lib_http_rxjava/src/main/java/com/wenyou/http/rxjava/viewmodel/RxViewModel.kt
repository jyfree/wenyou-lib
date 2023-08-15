package com.wenyou.http.rxjava.viewmodel

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import autodispose2.lifecycle.CorrespondingEventsFunction
import autodispose2.lifecycle.LifecycleEndedException
import autodispose2.lifecycle.LifecycleScopeProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject


/**
 * @description 绑定生命周期的viewModel
 * @date: 2023/7/4 10:07
 * @author: jy
 *
 */
@Keep
open class RxViewModel : ViewModel(), LifecycleScopeProvider<RxViewModel.ViewModelEvent> {

    private val lifecycleEvents = BehaviorSubject.createDefault(ViewModelEvent.CREATED)

    enum class ViewModelEvent {
        CREATED, CLEARED
    }

    override fun lifecycle(): Observable<ViewModelEvent> {
        return lifecycleEvents.hide()
    }

    override fun correspondingEvents(): CorrespondingEventsFunction<ViewModelEvent> {
        return CORRESPONDING_EVENTS
    }

    /**
     * Emit the [ViewModelEvent.CLEARED] event to
     * dispose off any subscriptions in the ViewModel.
     * 在nCleared() 中进行解绑
     */
    override fun onCleared() {
        lifecycleEvents.onNext(ViewModelEvent.CLEARED)
        super.onCleared()
    }

    override fun peekLifecycle(): ViewModelEvent {
        return lifecycleEvents.value
    }

    companion object {
        var CORRESPONDING_EVENTS: CorrespondingEventsFunction<ViewModelEvent> =
            CorrespondingEventsFunction { event ->
                when (event) {
                    ViewModelEvent.CREATED -> ViewModelEvent.CLEARED
                    else -> throw LifecycleEndedException(
                        "Cannot bind to ViewModel lifecycle after onCleared."
                    )
                }
            }
    }
}