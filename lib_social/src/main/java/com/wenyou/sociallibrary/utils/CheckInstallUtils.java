package com.wenyou.sociallibrary.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * @description 检测app安装
 * @date: 2021/9/7 11:11
 * @author: jy
 */
public class CheckInstallUtils {

    public static boolean isWXInstall() {
        return isInstall("com.tencent.mm");
    }

    public static boolean isQQInstall() {
        return isInstall("com.tencent.mobileqq");
    }

    public static boolean isWBInstall() {
        return isInstall("com.sina.weibo");
    }


    /**
     * 检测apk是否安装，按照包名
     */
    public static boolean isInstall(String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = SDKAppUtils.getApp().getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo != null) {
            return true;
        }
        return false;
    }
}
