package com.wenyou.http.coroutines

import com.wenyou.http.coroutines.bean.SuperBaseBean
import com.wenyou.http.coroutines.bean.SuperSingleBaseBean


/**
 * @description 协程基础仓库
 * @date: 2021/12/16 14:35
 * @author: jy
 */
open class BaseRepository {

    /**
     * @param remote 网络数据
     * @param local 本地数据
     * @param save 当网络请求成功后，保存数据等操作
     * @param isUseCache 是否使用缓存
     */
    suspend fun <T> callData(
        isUseCache: Boolean = false,
        save: suspend (T) -> Unit = {},
        local: suspend () -> T? = { null },
        remote: suspend () -> SuperBaseBean<T>

    ): T {
        if (isUseCache) {
            val localData = local.invoke()
            if (localData != null) {
                return localData
            }
        }
        remote().let { net ->
            if (!net.success() || net.data() == null) {
                throw ApiException(net.code(), net.msg())
            }
            return net.data()!!.also { save(it) }
        }
    }

    suspend fun <T : SuperSingleBaseBean> callSingleData(
        remote: suspend () -> T
    ): T {
        remote().let { net ->
            if (!net.success()) {
                throw ApiException(net.code(), net.msg())
            }
            return net
        }
    }
}