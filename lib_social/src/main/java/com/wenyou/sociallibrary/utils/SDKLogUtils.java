package com.wenyou.sociallibrary.utils;

import android.util.Log;

import com.wenyou.sociallibrary.SDKConfig;

import java.util.Locale;

/**
 * @description log打印
 * @date: 2021/12/16 14:06
 * @author: jy
 */
public class SDKLogUtils {

    private static final boolean SHOW_LOG = SDKConfig.isShowLog();
    private static final String TAG = "YLog_socialSdk";

    enum LogLevel {
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

    public static void e(Object... args) {
        if (SHOW_LOG) {
            StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
            log(TAG, traceElement, getString(args), LogLevel.ERROR);
        }
    }

    public static void i(Object... args) {
        if (SHOW_LOG) {
            StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
            log(TAG, traceElement, getString(args), LogLevel.INFO);
        }
    }

    public static void w(Object... args) {
        if (SHOW_LOG) {
            StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
            log(TAG, traceElement, getString(args), LogLevel.WARN);
        }
    }

    public static void d(Object... args) {
        if (SHOW_LOG) {
            StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
            log(TAG, traceElement, getString(args), LogLevel.DEBUG);
        }
    }

    private static String getString(Object... args) {
        if (args.length == 1) {
            return args[0].toString();
        } else {
            StringBuilder message = new StringBuilder();
            for (Object object : args) {
                message.append(object);
                message.append("---");
            }
            return message.toString();
        }
    }

    private static void log(String tag, StackTraceElement traceElement, String message, LogLevel logLevel) {
        String msgFormat = "[%s]--类名：%s--方法名：%s--第%d行--信息--%s";
        String messageWithTime = String.format(
                Locale.CHINA,
                msgFormat,
                logLevel.name(),
                traceElement.getFileName(),
                traceElement.getMethodName(),
                traceElement.getLineNumber(),
                message
        );
        switch (logLevel) {
            case INFO:
                Log.i(tag, messageWithTime);
                break;
            case WARN:
                Log.w(tag, messageWithTime);
                break;
            case DEBUG:
                Log.d(tag, messageWithTime);
                break;
            case ERROR:
                Log.e(tag, messageWithTime);
                break;
        }
    }

}
