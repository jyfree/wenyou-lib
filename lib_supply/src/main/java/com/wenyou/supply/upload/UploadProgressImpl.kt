package com.wenyou.supply.upload

import android.os.Handler
import android.os.Looper

abstract class UploadProgressImpl : UploadProgressListener {

    private var mTotalLength: Long = 0//总大小
    private var mCompletedBytes: Long = 0//已完成大小
    private var startTime = System.currentTimeMillis()
    private var handler: Handler? = null

    init {
        handler = Handler(Looper.getMainLooper())
    }


    override fun onTotalLength(totalLength: Long) {
        mTotalLength = totalLength
    }

    override fun onProgressChange(bytesWritten: Long, contentLength: Long) {
        //多次进度
        if (mTotalLength != 0L && mTotalLength != contentLength) {
            when (bytesWritten) {
                contentLength -> {
                    mCompletedBytes += bytesWritten
                    if (mCompletedBytes == mTotalLength) {
                        handler?.post {
                            onMainProgress(100)
                        }
                    }
                }
                else -> {
                    val progress =
                        ((mCompletedBytes + bytesWritten) * 100 / mTotalLength).toInt()
                    handler?.post {
                        onMainProgress(progress)
                    }
                }
            }
        } else {
            //单次进度
            val progress = (bytesWritten * 100 / contentLength).toInt()
            handler?.post {
                onMainProgress(progress)
            }
        }
    }

    override fun onSpeedChange(speed: Float) {
        handler?.post {
            onMainSpeed(false, speed)
        }
    }

    /**
     * 刷新开始时间
     */
    fun refreshStartTime() {
        startTime = System.currentTimeMillis()
    }

    /**
     * 上传结束，计算整个上传速度
     */
    fun uploadEnd() {
        if (mTotalLength != 0L) {
            val time = (System.currentTimeMillis() - startTime) / 1000
            val speed = mTotalLength / 1024f / 1024f / time
            onMainSpeed(true, speed)
        }
    }

    abstract fun onMainProgress(progress: Int)

    abstract fun onMainSpeed(uploadEnd: Boolean, speed: Float)
}