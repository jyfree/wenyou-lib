package com.wenyou.simpledemo.network.api


import com.wenyou.simpledemo.network.bean.HttpParam
import com.wenyou.simpledemo.network.coroutines.CrSingleBaseBean
import com.wenyou.simpledemo.network.rxjava.RxBaseBean
import com.wenyou.simpledemo.network.rxjava.RxSingleBaseBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @description api
 * @date: 2021/8/23 17:19
 * @author: jy
 */
interface ApiSimpleService {

    //获取验证码--协程
    @POST("wysq-auth/auth/sms/send-check-code")
    suspend fun getCode(@Body params: HttpParam): CrSingleBaseBean

    //获取验证码--rxJava
    @POST("wysq-auth/auth/sms/send-check-code")
    fun getCode2RxJava(@Body params: HttpParam): Observable<RxSingleBaseBean>

    //获取内容--rxJava
    @POST("wysq-auth/auth/sms/send-check-code")
    fun getContent(@Body params: HttpParam): Observable<RxBaseBean<String>>
}