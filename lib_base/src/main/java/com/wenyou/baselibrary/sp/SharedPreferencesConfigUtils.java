package com.wenyou.baselibrary.sp;

import android.content.Context;

import com.wenyou.baselibrary.utils.BaseUtils;

/**
 * @description 存储配置信息
 * 使用前，必须在application初始化，如：
 * SharedPreferencesConfigUtils.getInstance().init();
 * @date: 2021/12/16 13:34
 * @author: jy
 */
public class SharedPreferencesConfigUtils extends BaseSharedPref {

    private static volatile SharedPreferencesConfigUtils mInstance;

    private static final String SP_NAME = "Config_File";

    public static final String SIGNATURE = "signature";//签名
    public static final String ROM = "rom";//手机ROM
    public static final String ROM_VERSION = "romVersion";//手机ROM版本

    public SharedPreferencesConfigUtils(Context context, String fileName, int mode) {
        super(context, fileName, mode);
    }

    public static SharedPreferencesConfigUtils getInstance() {
        if (mInstance == null) {
            synchronized (SharedPreferencesConfigUtils.class) {
                if (mInstance == null) {
                    mInstance = new SharedPreferencesConfigUtils(BaseUtils.getApp(), SP_NAME, Context.MODE_MULTI_PROCESS);
                }
            }
        }
        return mInstance;
    }
}
