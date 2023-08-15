package com.wenyou.supply.download.sys

/**
 * @description
 * @date: 2021/12/16 14:37
 * @author: jy
 */
interface DownloadPresenter {
    /**
     * 根据url下载文件
     */
    fun requestDownload(urls: Array<String>)

    /**
     * 根据url下载文件
     * @param isSavePoint 是否验证重读下载
     */
    fun requestDownload(urls: Array<String>, isSavePoint: Boolean)

    /**
     * 给要下载的文件命名
     * @param fileDir 文件夹
     */
    fun requestDownload(urls: Array<String>, fileDir: String)

    /**
     * 注销广播，如果使用系统DownloadManager，则必须调用该方法注销广播
     */
    fun unregisterReceiver()

    /**
     * 注册监听器，方便监听下载进度与状态
     */
    fun registerContentObserver()
}