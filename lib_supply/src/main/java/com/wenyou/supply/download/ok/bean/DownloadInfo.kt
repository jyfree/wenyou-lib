package com.wenyou.supply.download.ok.bean

class DownloadInfo(
    var url: String,//下载地址
    var savePath: String?,//存储位置
    var countLength: Long = 0,//文件总长度
    var readLength: Long = 0,//文件已下载长度
    var state: Int = 0//下载状态
) {
    fun setState(downState: DownState) {
        state = downState.state
    }

    fun getState(): DownState {
        return when (state) {
            0 -> DownState.START
            1 -> DownState.DOWN
            2 -> DownState.PAUSE
            3 -> DownState.STOP
            4 -> DownState.ERROR
            5 -> DownState.FINISH
            else -> DownState.FINISH
        }
    }

    override fun toString(): String {
        return "DownloadInfo(url='$url', savePath=$savePath, countLength=$countLength, readLength=$readLength, state=$state)"
    }


}