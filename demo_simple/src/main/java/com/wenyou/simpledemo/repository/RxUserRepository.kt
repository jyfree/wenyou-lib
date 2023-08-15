package com.wenyou.simpledemo.repository

import com.wenyou.simpledemo.network.Api
import com.wenyou.simpledemo.network.bean.defaultRequestMultipleParams
import com.wenyou.simpledemo.network.rxjava.RxBaseBean
import com.wenyou.simpledemo.network.rxjava.RxSingleBaseBean
import io.reactivex.rxjava3.core.Observable


/**
 * @description RxJava用户仓库
 * @date: 2021/8/23 17:13
 * @author: jy
 */
class RxUserRepository {
    /**
     * 获取手机验证码
     * @param phoneNum 手机号
     * @return Boolean
     */
    fun getCode(phoneNum: String): Observable<RxSingleBaseBean> {
        val multipleParams = defaultRequestMultipleParams() + ("phoneNo" to phoneNum)
        return Api.simpleInstance.getCode2RxJava(
            multipleParams
        )
    }

    /**
     * 获取内容--rxJava
     */
    fun getContent(phoneNum: String): Observable<RxBaseBean<String>> {
        val multipleParams = defaultRequestMultipleParams() + ("phoneNo" to phoneNum)
        return Api.simpleInstance.getContent(
            multipleParams
        )
    }
}