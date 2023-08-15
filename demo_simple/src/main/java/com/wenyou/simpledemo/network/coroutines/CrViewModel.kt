package com.wenyou.simpledemo.network.coroutines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wenyou.baselibrary.utils.ToastUtils
import com.wenyou.http.coroutines.ApiException
import com.wenyou.http.coroutines.ExceptionHandle
import com.wenyou.http.coroutines.ext.requestMain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

/**
 * @description 协程 viewModel
 * @date: 2021/12/16 14:30
 * @author: jy
 */
open class CrViewModel : ViewModel() {

    /**
     * 菊花
     */
    private var loading = MutableLiveData<Boolean>()

    fun getLoading(): LiveData<Boolean> {
        return loading
    }

    /**
     * 所有网络请求都在 viewModelScope 域中启动，当页面销毁时会自动
     * 调用ViewModel的  #onCleared 方法取消所有协程
     */
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = requestMain { block() }

    /**
     *  不过滤请求结果
     * @param block 请求体
     * @param error 失败回调
     * @param complete  完成回调（无论成功失败都会调用）
     * @param isShowLoading 是否显示加载框
     */
    fun launchGo(
        block: suspend CoroutineScope.() -> Unit,
        error: suspend (ApiException) -> Unit = {
            ToastUtils.showToast(it.message)
        },
        complete: suspend CoroutineScope.() -> Unit = {},
        isShowLoading: Boolean = true
    ) {
        if (isShowLoading) loading.value = true
        launchUI {
            handleException(
                withContext(Dispatchers.IO) { block },
                { error(it) },
                {
                    if (isShowLoading) {
                        loading.value = false
                    }
                    complete()
                }
            )
        }
    }

    /**
     * 异常统一处理
     */
    private suspend fun handleException(
        block: suspend CoroutineScope.() -> Unit,
        error: suspend CoroutineScope.(ApiException) -> Unit,
        complete: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                block()
            } catch (e: Throwable) {
                error(ExceptionHandle.handleException(e))
            } finally {
                complete()
            }
        }
    }
}