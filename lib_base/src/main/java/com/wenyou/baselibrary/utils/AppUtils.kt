package com.wenyou.baselibrary.utils

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Build
import com.wenyou.baselibrary.sp.SharedPreferencesConfigUtils
import java.io.FileInputStream
import java.io.IOException
import java.nio.charset.Charset

/**
 * @description
 * @date: 2021/12/16 13:38
 * @author: jy
 */
object AppUtils {

    /**
     * 获取进程名
     */
    fun getCurProcessName(context: Context, pid: Int): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Application.getProcessName()
        } else {
            getProcessNameOld()
        }
    }

    private fun getProcessNameOld(): String? {
        var fileInputStream: FileInputStream? = null
        try {
            val fn = "/proc/self/cmdline"
            fileInputStream = FileInputStream(fn)
            val buffer = ByteArray(256)
            var len = 0
            var b: Int = 0
            while (fileInputStream.read().also { b = it } > 0 && len < buffer.size) {
                buffer[len++] = b.toByte()
            }
            if (len > 0) {
                return String(buffer, 0, len, Charset.defaultCharset())
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        } finally {
            try {
                fileInputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    /**
     * 获取签名
     */
    fun getSignature(context: Context): String {
        val spKey = "signature"
        var signStr = SharedPreferencesConfigUtils.getInstance().getString(
            spKey, "unKnow"
        )
        if (signStr != "unKnow") {
            return signStr
        }
        //获取包管理器
        val packageManager = context.packageManager
        val packageInfo: PackageInfo
        //获取当前要获取 SHA1 值的包名，也可以用其他的包名，但需要注意，
        //在用其他包名的前提是，此方法传递的参数 Context 应该是对应包的上下文。
        val packageName = context.packageName
        //签名信息
        var signatures: Array<Signature>? = null
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                packageInfo = packageManager.getPackageInfo(
                    context.packageName,
                    PackageManager.GET_SIGNING_CERTIFICATES
                )
                val signingInfo = packageInfo.signingInfo
                signatures = signingInfo.apkContentsSigners
            } else {
                //获得包的所有内容信息类
                packageInfo =
                    packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
                signatures = packageInfo.signatures
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        if (null != signatures && signatures.isNotEmpty()) {
            val sign: Signature = signatures[0]
            signStr = MD5Utils.getMD5(sign.toByteArray()).toLowerCase()
            SharedPreferencesConfigUtils.getInstance().putApply(
                spKey,
                signStr
            )
        }
        return signStr
    }


    fun relaunchApp() {
        relaunchApp(false)
    }

    /**
     * 重启
     * @param isKillProcess 如果为True，则终止进程，否则为false。
     */
    fun relaunchApp(isKillProcess: Boolean) {
        val intent = getLaunchAppIntent(BaseUtils.getApp().packageName, true)
        if (intent == null) {
            YLogUtils.e("AppUtils--Didn't exist launcher activity.")
            return
        }
        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_CLEAR_TASK
        )
        BaseUtils.getApp().startActivity(intent)
        if (!isKillProcess) return
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(0)
    }

    private fun getLaunchAppIntent(packageName: String): Intent? {
        return getLaunchAppIntent(packageName, false)
    }

    private fun getLaunchAppIntent(packageName: String, isNewTask: Boolean): Intent? {
        val launcherActivity = getLauncherActivity(packageName)
        if (!launcherActivity.isEmpty()) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            val cn = ComponentName(packageName, launcherActivity)
            intent.component = cn
            return if (isNewTask) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) else intent
        }
        return null
    }

    private fun getLauncherActivity(pkg: String): String {
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.setPackage(pkg)
        val pm = BaseUtils.getApp().packageManager
        val info = pm.queryIntentActivities(intent, 0)
        val size = info.size
        if (size == 0) return ""
        for (i in 0 until size) {
            val ri = info.get(i)
            if (ri.activityInfo.processName == pkg) {
                return ri.activityInfo.name
            }
        }
        return info[0].activityInfo.name
    }
}