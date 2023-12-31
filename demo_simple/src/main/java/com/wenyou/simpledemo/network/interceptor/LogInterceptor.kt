package com.wenyou.simpledemo.network.interceptor

import com.wenyou.baselibrary.utils.YLogUtils
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset

/**
 * @description okHttp log拦截器
 * @date: 2021/12/16 14:33
 * @author: jy
 */
class LogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var requestParams = ""
        if (request.method == "POST") {
            val requestBuffer = Buffer()
            request.body?.writeTo(requestBuffer)
            var charset: Charset? = Charset.forName("UTF-8")
            val contentType = request.body?.contentType()
            if (contentType != null) {
                charset = contentType.charset(charset)
            }
            if (charset != null) {
                requestParams = requestBuffer.readString(charset)
            }
            YLogUtils.i("request=url=(${request.url})==params==$requestParams==headers==[${request.headers}]")
        }
        val time = System.currentTimeMillis()
        val response = chain.proceed(request)
        val charset = response.body?.contentType()?.charset()
        val source = response.body?.source()
        source?.request(Long.MAX_VALUE)
        YLogUtils.i(
            "response=请求时长=${System.currentTimeMillis() - time}=(${request.url})",
            source?.buffer?.clone()?.readString(
                charset
                    ?: Charset.forName("utf-8")
            )
        )
        return response
    }
}