package com.wenyou.baselibrary.utils

import android.annotation.SuppressLint
import android.util.Log
import com.wenyou.baselibrary.BuildConfig
import com.wenyou.baselibrary.helper.LogCatHelper
import java.text.SimpleDateFormat
import java.util.*

/**
 * 注意DEBUG等级不写日志文件，不打印堆栈信息
 * VERBOSE等级写日志文件，不打印堆栈信息
 * @description 打印log
 * @date: 2021/12/16 13:42
 * @author: jy
 */
object YLogUtils {

    var SHOW_LOG = BuildConfig.DEBUG
    private const val TAG = "YLog_default"

    @SuppressLint("SimpleDateFormat")
    private val sLogFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS  ")
    private val logTimeStr: String
        get() = sLogFormat.format(Date())

    private var level = LogLevel.ALL

    enum class LogLevel {
        DEBUG,
        INFO,
        WARN,
        ERROR,
        VERBOSE,
        ALL
    }

    fun setLogLevel(logLevel: LogLevel) {
        level = logLevel
    }

    fun eThrow(ex: Throwable?) {
        if (SHOW_LOG) {
            val logBody = Log.getStackTraceString(ex)
            val traceElement = Thread.currentThread().stackTrace[3]
            log(TAG, traceElement, logBody, LogLevel.ERROR)
        }
    }

    fun eThrow(ex: Throwable?, vararg args: Any?) {
        if (SHOW_LOG) {
            val logMessage = "${getString(*args)}\n${Log.getStackTraceString(ex)}"
            val traceElement = Thread.currentThread().stackTrace[3]
            log(TAG, traceElement, logMessage, LogLevel.ERROR)
        }
    }

    fun e(vararg args: Any?) {
        if (SHOW_LOG) {
            val traceElement = Thread.currentThread().stackTrace[3]
            log(TAG, traceElement, getString(*args), LogLevel.ERROR)
        }
    }

    fun i(vararg args: Any?) {
        if (SHOW_LOG) {
            val traceElement = Thread.currentThread().stackTrace[3]
            log(TAG, traceElement, getString(*args), LogLevel.INFO)
        }
    }

    fun w(vararg args: Any?) {
        if (SHOW_LOG) {
            val traceElement = Thread.currentThread().stackTrace[3]
            log(TAG, traceElement, getString(*args), LogLevel.WARN)
        }
    }

    fun d(vararg args: Any?) {
        if (SHOW_LOG) {
            logDebug(TAG, getString(*args))
        }
    }

    fun v(vararg args: Any?) {
        if (SHOW_LOG) {
            logV(TAG, getString(*args))
        }
    }

    //**************************************自定义TAG****************************

    fun eThrowTag(tag: String, ex: Throwable?) {
        if (SHOW_LOG) {
            val logBody = Log.getStackTraceString(ex)
            val traceElement = Thread.currentThread().stackTrace[3]
            log(tag, traceElement, logBody, LogLevel.ERROR)
        }
    }

    fun eThrowTag(tag: String, ex: Throwable?, vararg args: Any?) {
        if (SHOW_LOG) {
            val logMessage = "${getString(*args)}\n${Log.getStackTraceString(ex)}"
            val traceElement = Thread.currentThread().stackTrace[3]
            log(tag, traceElement, logMessage, LogLevel.ERROR)
        }
    }

    fun eTag(tag: String, vararg args: Any?) {
        if (SHOW_LOG) {
            val traceElement = Thread.currentThread().stackTrace[3]
            log(tag, traceElement, getString(*args), LogLevel.ERROR)
        }
    }

    fun iTag(tag: String, vararg args: Any?) {
        if (SHOW_LOG) {
            val traceElement = Thread.currentThread().stackTrace[3]
            log(tag, traceElement, getString(*args), LogLevel.INFO)
        }
    }

    fun wTag(tag: String, vararg args: Any?) {
        if (SHOW_LOG) {
            val traceElement = Thread.currentThread().stackTrace[3]
            log(tag, traceElement, getString(*args), LogLevel.WARN)
        }
    }

    fun dTag(tag: String, vararg args: Any?) {
        if (SHOW_LOG) {
            logDebug(tag, getString(*args))
        }
    }

    fun vTag(tag: String, vararg args: Any?) {
        if (SHOW_LOG) {
            logV(tag, getString(*args))
        }
    }

    private fun getString(vararg args: Any?): String {
        return when (args.size) {
            1 -> args[0].toString()
            else -> {
                val message = StringBuilder()
                for (`object` in args) {
                    message.append(`object`)
                    message.append("---")
                }
                message.toString()
            }
        }
    }

    private fun logDebug(tag: String, message: String) {
        Log.d(tag, "[DEBUG]--$message")
    }


    private fun logV(tag: String, message: String) {
        if (level == LogLevel.ALL || level == LogLevel.VERBOSE) {
            Log.v(tag, "[VERBOSE]--$message")
            LogCatHelper.getInstance().writeLogFile("$logTimeStr$tag：[VERBOSE]：$message")
        }
    }

    private fun log(
        tag: String,
        traceElement: StackTraceElement,
        message: String,
        logLevel: LogLevel
    ) {
        val msgFormat = "[%s]--类名：%s--方法名：%s--第%d行--信息--%s"
        val messageWithTime = String.format(
            Locale.CHINA,
            msgFormat,
            logLevel.name,
            traceElement.fileName,
            traceElement.methodName,
            traceElement.lineNumber,
            message
        )

        if (level == LogLevel.ALL || logLevel == level) {
            when (logLevel) {
                LogLevel.INFO -> Log.i(tag, messageWithTime)
                LogLevel.WARN -> Log.w(tag, messageWithTime)
                LogLevel.ERROR -> Log.e(tag, messageWithTime)
                else -> Log.i(tag, messageWithTime)
            }
            LogCatHelper.getInstance().writeLogFile("$logTimeStr$tag：$messageWithTime")
        }
    }

}
