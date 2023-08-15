package com.wenyou.simpledemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wenyou.baselibrary.utils.YLogUtils
import com.wenyou.http.rxjava.RxHelper
import com.wenyou.http.rxjava.viewmodel.RxViewModel
import com.wenyou.http.rxjava.viewmodel.call
import com.wenyou.simpledemo.Constants
import com.wenyou.simpledemo.network.rxjava.RxHelperExt
import com.wenyou.simpledemo.repository.RxUserRepository


/**
 * @description
 * @date: 2021/12/16 16:59
 * @author: jy
 */
class SimpleRxViewModel constructor(
    private val userRepository: RxUserRepository
) : RxViewModel() {

    private val data = MutableLiveData<String>()

    fun getData(): LiveData<String> {
        return data
    }

    fun getCode(phoneNum: String) {
        YLogUtils.i("获取手机验证码--phoneNum", phoneNum)
        call(request = {
            userRepository.getCode(phoneNum)
        }, success = {
            YLogUtils.i("获取手机验证码--成功", it)
            data.value = it.toString()
        }, failed = {
            YLogUtils.e("getCode failed:${it.code} , ${it.message}")
            data.value = "code：${it.code}，message：${it.message}"
        }, composer = RxHelper.handleSingleResult(), tag = Constants.SIMPLE_API_TAG)
    }

    fun getContent(phoneNum: String) {
        YLogUtils.i("getContent--phoneNum", phoneNum)
        call(request = {
            userRepository.getContent(phoneNum)
        }, success = {
            YLogUtils.i("getContent--成功", it)
            data.value = it.toString()
        }, failed = {
            YLogUtils.e("getContent failed:${it.code} , ${it.message}")
            data.value = "code：${it.code}，message：${it.message}"
        }, composer = RxHelperExt.handleResult(), tag = Constants.SIMPLE_API_TAG)
    }
}