package com.wenyou.simpledemo.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.content.FileProvider
import com.wenyou.baselibrary.utils.YLogUtils
import java.io.File
import java.io.IOException

object SimpleUtils {

    /**
     * 安装App（支持7.0）
     * 8.0 需要manifest注册权限：android.permission.REQUEST_INSTALL_PACKAGES
     * @param file      文件
     * @param authority 7.0及以上安装需要传入清单文件中的`<provider>`的authorities属性
     * <br></br>参看https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     */
    fun openFile(context: Context, file: File) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val type = getMIMEType(file)
            intent.setDataAndType(Uri.fromFile(file), type)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //判读版本是否在7.0以上
                //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致  参数3  共享的文件
                val apkUri = FileProvider.getUriForFile(
                    context,
                    context.packageName + ".FileProvider",
                    file
                )
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
            } else {
                try {
                    Runtime.getRuntime().exec("chmod 777 " + file.canonicalPath)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                intent.setDataAndType(
                    Uri.fromFile(file),
                    "application/vnd.android.package-archive"
                )
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "打开安装包出错", Toast.LENGTH_SHORT).show()
            YLogUtils.e("apk更新--打开安装包出错", e.message)
        }
    }

    private fun getMIMEType(var0: File): String? {
        var var1: String? = ""
        val var2 = var0.name
        val var3 =
            var2.substring(var2.lastIndexOf(".") + 1, var2.length).toLowerCase()
        var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3)
        return var1
    }
}