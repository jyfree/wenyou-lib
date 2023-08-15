package com.wenyou.simpledemo.repository

import com.wenyou.http.coroutines.BaseRepository
import com.wenyou.simpledemo.network.Api
import com.wenyou.simpledemo.network.bean.HttpParam
import com.wenyou.simpledemo.network.coroutines.CrSingleBaseBean


/**
 * @description 协程用户仓库
 * @date: 2021/8/23 17:13
 * @author: jy
 */
class CrUserRepository : BaseRepository() {
    /**
     * 获取手机验证码
     * @param phoneNum 手机号
     * @return Boolean
     */
    suspend fun getCode(phoneNum: String): CrSingleBaseBean {
        return callSingleData {
            Api.simpleInstance.getCode(
                HttpParam.obtain("phoneNo", phoneNum)
            )
        }
    }

}