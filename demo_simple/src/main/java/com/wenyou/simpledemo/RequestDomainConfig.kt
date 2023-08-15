package com.wenyou.simpledemo


/**
 * @description 域名配置
 * @date: 2021/8/23 17:22
 * @author: jy
 */
object RequestDomainConfig {
    private val isTestService = BuildConfig.DEBUG

    fun getBaseUrl(): String {
        return if (isTestService) {
            "https://wenyou.tech/wysq-gateway-test/"
        } else {
            "https://wenyou.tech/wysq-gateway-test/"
        }
    }
}
