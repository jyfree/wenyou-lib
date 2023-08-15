package com.wenyou.simpledemo.network.interceptor

import com.wenyou.simpledemo.network.ApiPublicParams
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @description
 * @date: 2021/12/16 16:54
 * @author: jy
 */
class ParamsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("clientMode", ApiPublicParams.getClientModel())
            .addHeader("osVersion", ApiPublicParams.getOSVersion())

        return chain.proceed(builder.build())
    }
}