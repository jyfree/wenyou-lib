package com.wenyou.sociallibrary;

import android.app.Application;
import android.content.Context;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.openapi.IWBAPI;
import com.sina.weibo.sdk.openapi.WBAPIFactory;
import com.wenyou.sociallibrary.utils.SDKAppUtils;

/**
 * @description sdk配置，在application调用
 * @date: 2021/12/16 14:08
 * @author: jy
 */
public class SDKConfig {


    //微博  配置信息
    private static String wb_appID;//appId
    private static String wb_redirectUrl;//回调地址
    private static String wb_scope;//作用域

    //QQ 配置信息
    private static String qq_appID;//appId
    private static String qq_fileProvider;//FileProvider名

    //微信  配置信息
    private static String wx_appID;//appId
    private static String wx_MchID;//商户号
    private static String wx_ApiKey;//API密钥

    private static boolean showLog = false;
    private static String appName;//app名称

    private static volatile SDKConfig sdkConfig;

    public static SDKConfig getDefault() {
        if (sdkConfig == null) {
            synchronized (SDKConfig.class) {
                if (sdkConfig == null) {
                    sdkConfig = new SDKConfig();
                }
            }
        }
        return sdkConfig;
    }

    private void setBuilder(Builder builder) {

        qq_appID = builder.qq_appID;
        qq_fileProvider = builder.qq_fileProvider;

        wb_appID = builder.wb_appID;
        wb_redirectUrl = builder.wb_redirectUrl;
        wb_scope = builder.wb_scope;

        wx_appID = builder.wx_appID;
        wx_MchID = builder.wx_MchID;
        wx_ApiKey = builder.wx_ApiKey;

        showLog = builder.showLog;
        appName = builder.appName;
    }

    public static Builder beginBuilder() {
        return new Builder();
    }

    public static String getWb_appID() {
        return wb_appID;
    }

    public static String getWb_redirectUrl() {
        return wb_redirectUrl;
    }

    public static String getWb_scope() {
        return wb_scope;
    }


    public static IWBAPI initWB(Context context) {
        AuthInfo authInfo = new AuthInfo(SDKAppUtils.getApp(),
                getWb_appID(),
                getWb_redirectUrl(),
                getWb_scope());
        IWBAPI mWBAPI = WBAPIFactory.createWBAPI(context);
        mWBAPI.registerApp(context, authInfo);
        return mWBAPI;
    }

    public static String getQq_appID() {
        return qq_appID;
    }

    public static String getQq_fileProvider() {
        return qq_fileProvider;
    }

    public static String getWx_appID() {
        return wx_appID;
    }

    public static String getWx_MchID() {
        return wx_MchID;
    }

    public static String getWx_ApiKey() {
        return wx_ApiKey;
    }

    public static boolean isShowLog() {
        return showLog;
    }

    public static String getAppName() {
        return appName;
    }

    public static class Builder {
        private String qq_appID;
        private String qq_fileProvider;

        private String wb_appID;
        private String wb_redirectUrl;
        private String wb_scope;

        private String wx_appID;
        private String wx_MchID;
        private String wx_ApiKey;

        private boolean showLog;
        private String appName;


        public Builder() {
        }

        //*****************QQ*******************
        public Builder qqAppID(String qq_appID) {
            this.qq_appID = qq_appID;
            return this;
        }

        public Builder qqFileProvider(String qq_fileProvider) {
            this.qq_fileProvider = qq_fileProvider;
            return this;
        }

        //*****************微博*******************
        public Builder wbAppID(String wb_appID) {
            this.wb_appID = wb_appID;
            return this;
        }

        public Builder wbRedirectUrl(String wb_redirectUrl) {
            this.wb_redirectUrl = wb_redirectUrl;
            return this;
        }

        public Builder wbScope(String wb_scope) {
            this.wb_scope = wb_scope;
            return this;
        }

        //*****************微信*******************
        public Builder wxAppID(String wx_appID) {
            this.wx_appID = wx_appID;
            return this;
        }

        public Builder wxMchID(String wxMchID) {
            this.wx_MchID = wxMchID;
            return this;
        }

        public Builder wxApiKey(String wxApiKey) {
            this.wx_ApiKey = wxApiKey;
            return this;
        }

        public Builder isShowLog(boolean showLog) {
            this.showLog = showLog;
            return this;
        }

        public Builder appName(String appName) {
            this.appName = appName;
            return this;
        }

        public void build(Application application) {
            SDKAppUtils.init(application);
            getDefault().setBuilder(this);
        }
    }

}
