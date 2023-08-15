package com.wenyou.simpledemo.network

import android.os.Build


/**
 * @description
 * @date: 2021/12/16 16:54
 * @author: jy
 */
object ApiPublicParams {

    /**
     * 获取手机设备型号
     */
    fun getClientModel(): String = Build.MODEL ?: "unknow"

    /**
     * 获取系统OS版本
     */
    fun getOSVersion() = Build.VERSION.RELEASE ?: "unknow"

}