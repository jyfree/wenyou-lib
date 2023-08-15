package com.wenyou.simpledemo

/**
 * @description
 * @date: 2021/12/16 17:00
 * @author: jy
 */
object Constants {

    const val SIMPLE_API_TAG = "testTag"

    object URL {
        const val SHARE_IMAGE_URL = "http://s.bdstatic.com/xbox/wuxian/img/logo426.png"//分享imageUrl
        const val SHARE_TARGET_URL =
            "https://www.baidu.com/"//分享目标地址
    }

    object WXH5Pay {
        var REFERER = "https://payservice.xiaojingzb.com"
        val REDIRECT_URL: String = "$REFERER/pay/android_weixinpayjump.jsp"

    }
}