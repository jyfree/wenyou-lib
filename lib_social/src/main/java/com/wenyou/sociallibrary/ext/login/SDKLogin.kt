package com.wenyou.sociallibrary.ext.login

/**
 * @description
 * @date: 2021/12/16 14:00
 * @author: jy
 */
class SDKLogin private constructor() {
    val sdkLoginManager: SDKLoginManager = SDKLoginManager()

    private object Holder {
        val instance = SDKLogin()
    }

    companion object {

        val instance: SDKLogin
            get() = Holder.instance
    }
}
