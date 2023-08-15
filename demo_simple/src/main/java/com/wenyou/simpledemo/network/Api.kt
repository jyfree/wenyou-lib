package com.wenyou.simpledemo.network


import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.wenyou.simpledemo.RequestDomainConfig
import com.wenyou.simpledemo.network.api.ApiSimpleService
import com.wenyou.simpledemo.network.interceptor.LogInterceptor
import com.wenyou.simpledemo.network.interceptor.ParamsInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @description
 * @date: 2021/12/16 16:54
 * @author: jy
 */
class Api {

    private val gSon = GsonBuilder().registerTypeAdapter(
        Date::class.java,
        JsonDeserializer<Date> { json, _, _ -> Date(json.asJsonPrimitive.asLong) }).create()

    /**
     * 静态内部类单例
     */
    object ApiHolder {

        private val api = Api()

        val SIMPLE_SERVICE: ApiSimpleService = api.initRetrofit(RequestDomainConfig.getBaseUrl())
            .create(ApiSimpleService::class.java)

    }

    /**
     * 初始化Retrofit
     */
    fun initRetrofit(baseUrl: String): Retrofit {
        val builder = Retrofit.Builder()
        //支持直接格式化json返回Bean对象
        builder.addConverterFactory(GsonConverterFactory.create(gSon))
        //支持RxJava
        builder.addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        builder.baseUrl(baseUrl)
        val client = initOkHttpClient()
        if (client != null) {
            builder.client(client)
        }
        return builder.build()
    }

    private fun initOkHttpClient(): OkHttpClient? {
        val httpClientBuild = OkHttpClient.Builder()
        httpClientBuild.addInterceptor(ParamsInterceptor())
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
        httpClientBuild.addInterceptor(LogInterceptor())
        return httpClientBuild.build()
    }

    companion object {
        val simpleInstance: ApiSimpleService
            get() = ApiHolder.SIMPLE_SERVICE
    }

}
