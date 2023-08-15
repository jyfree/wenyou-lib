package com.wenyou.sociallibrary.wb;

import android.app.Activity;
import android.content.Intent;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.common.UiError;
import com.sina.weibo.sdk.openapi.IWBAPI;
import com.wenyou.sociallibrary.SDKConfig;
import com.wenyou.sociallibrary.constant.SDKLoginType;
import com.wenyou.sociallibrary.listener.OnSocialSdkLoginListener;
import com.wenyou.sociallibrary.utils.SDKLogUtils;


/**
 * @description 微博登录
 * @date: 2021/8/17 10:57
 * @author: jy
 */
public class WBChannelManager {

    private OnSocialSdkLoginListener listener;
    private IWBAPI iwbapi;
    private Activity context;

    public WBChannelManager(Activity context, OnSocialSdkLoginListener listener) {
        this.context = context;
        this.listener = listener;
        iwbapi = SDKConfig.initWB(context);
    }

    /**
     * 授权, ALL IN ONE   如果手机安装了微博客户端则使用客户端授权,没有则进行网页授权
     */
    public void wbLogin() {
        //auth
        iwbapi.authorize(context, new WbAuthListener() {
            @Override
            public void onComplete(Oauth2AccessToken token) {
                SDKLogUtils.i("微博登录授权--成功", token);
                listener.loginAuthSuccess(SDKLoginType.TYPE_WB, token.getAccessToken(), token.getUid());
            }

            @Override
            public void onError(UiError error) {
                SDKLogUtils.e("微博登录授权--失败--errorCode", error.errorCode, "errorMessage", error.errorMessage);
                listener.loginFail(SDKLoginType.TYPE_WB, error.errorMessage);
            }

            @Override
            public void onCancel() {
                SDKLogUtils.i("微博登录授权--取消");
                listener.loginCancel(SDKLoginType.TYPE_WB);
            }
        });
    }

    /**
     * 授权回调
     * 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
     * <p/>
     * onActivityResults处调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void result2Activity(int requestCode, int resultCode, Intent data) {

        if (iwbapi != null) {
            iwbapi.authorizeCallback(context, requestCode, resultCode, data);
        }
    }
}
