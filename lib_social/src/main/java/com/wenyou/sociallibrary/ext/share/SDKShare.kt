package com.wenyou.sociallibrary.ext.share

/**
 * @description
 * @date: 2021/12/16 14:01
 * @author: jy
 */
class SDKShare private constructor() {
    val sdkShareManager: SDKShareManager = SDKShareManager()

    private object Holder {
        val instance = SDKShare()
    }

    companion object {

        val instance: SDKShare
            get() = Holder.instance
    }
}
