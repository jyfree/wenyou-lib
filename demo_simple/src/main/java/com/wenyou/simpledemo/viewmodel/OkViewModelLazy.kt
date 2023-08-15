package com.wenyou.simpledemo.viewmodel

///**
// * 全局共享的ViewModel
// */
//
//private val applicationViewModelStore = ViewModelStore()
//
//fun getApplicationViewModelStore() = applicationViewModelStore
//
//@MainThread
//public inline fun <reified VM : ViewModel> ComponentActivity.applicationViewModels(
//    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
//): ViewModelLazy<VM> {
//    val factoryPromise = factoryProducer ?: {
//        defaultViewModelProviderFactory
//    }
//
//    return ViewModelLazy(VM::class, { getApplicationViewModelStore() }, factoryPromise)
//}
//
//@MainThread
//public inline fun <reified VM : ViewModel> Fragment.applicationViewModels(
//        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
//): ViewModelLazy<VM> {
//    val factoryPromise = factoryProducer ?: {
//        defaultViewModelProviderFactory
//    }
//
//    return ViewModelLazy(VM::class, { getApplicationViewModelStore() }, factoryPromise)
//}