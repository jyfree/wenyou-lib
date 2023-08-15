package com.wenyou.simpledemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wenyou.simpledemo.repository.RxUserRepository

/**
 * @description SimpleViewModel的工厂
 * @date: 2020/7/14 10:31
 * @author: jy
 */
class SimpleRxViewModelFactory(
    private val userRepository: RxUserRepository
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SimpleRxViewModel(userRepository) as T
    }
}
