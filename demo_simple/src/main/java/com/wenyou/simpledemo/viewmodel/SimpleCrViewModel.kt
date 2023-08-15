package com.wenyou.simpledemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wenyou.baselibrary.ext.livedata.RequestState
import com.wenyou.baselibrary.ext.livedata.StatefulLiveData
import com.wenyou.baselibrary.ext.livedata.StatefulMutableLiveData
import com.wenyou.baselibrary.utils.YLogUtils
import com.wenyou.simpledemo.network.coroutines.CrViewModel
import com.wenyou.simpledemo.repository.CrUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * @description
 * @date: 2021/12/16 16:59
 * @author: jy
 */
class SimpleCrViewModel constructor(
    private val userRepository: CrUserRepository
) : CrViewModel() {

    private val data = MutableLiveData<String>()
    //模拟状态机LiveData
    private val statefulLiveData = StatefulMutableLiveData<String>()

    fun getData(): LiveData<String> {
        return data
    }

    fun getStatefulLiveData(): StatefulLiveData<String> {
        return statefulLiveData
    }

    fun getCode(phoneNum: String) {
        YLogUtils.i("获取手机验证码--phoneNum", phoneNum)
        launchGo({
            val result = userRepository.getCode(phoneNum)
            YLogUtils.i("获取手机验证码--成功", result)
            data.value = result.toString()
        }, {
            YLogUtils.e("getCode failed:${it.code} , ${it.message}")
            data.value = "failed:${it.code} , ${it.message}"
        }
        )
    }

    fun requestData() {
        statefulLiveData.value = RequestState.Loading(true)
        viewModelScope.launch(Dispatchers.Main) {
            delay(2000)
            statefulLiveData.value = RequestState.Loading(false)
            statefulLiveData.value = RequestState.Success("success")
            delay(1000)
            statefulLiveData.value = RequestState.Error("9527", Exception("测试异常状态"))
        }
    }
}