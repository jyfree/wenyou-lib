package com.wenyou.baselibrary.ext.livedata

/**
 * @description 状态机回调
 * @date: 2021/12/21 17:44
 * @author: jy
 */
class ResultBuilder<T>() {
    var onLoading: (show: Boolean) -> Unit = {}
    var onSuccess: (data: T?) -> Unit = { }
    var onError: (errorMsg: String?, exception: Exception?) -> Unit = { errorMsg, exception -> }
}