package com.wenyou.sociallibrary.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import com.wenyou.sociallibrary.R;
import com.wenyou.sociallibrary.listener.OnSocialSdkLoginListener;
import com.wenyou.sociallibrary.qq.QQChannelManager;
import com.wenyou.sociallibrary.utils.SDKLogUtils;
import com.wenyou.sociallibrary.wb.WBChannelManager;
import com.wenyou.sociallibrary.wx.WXChannelManager;
import com.wenyou.sociallibrary.wx.WXListener;

/**
 * @description 第三方登录
 * @date: 2021/12/16 14:01
 * @author: jy
 */
public class LoginHelper {

    private ProgressDialog mProgressDialog;
    private Activity mContext;
    private OnSocialSdkLoginListener loginListener;

    public LoginHelper(Activity context, OnSocialSdkLoginListener listener) {

        this.mContext = context;
        this.loginListener = listener;
        initProgressDialog();

        //提前初始化微博登录
        if (wbChannelManager == null) {
            wbChannelManager = new WBChannelManager(mContext, loginListener);
        }
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(mContext.getString(R.string.social_sdk_extLogin_channel_loging));
        mProgressDialog.setCancelable(false);
    }

    public void showProgressDialog(boolean isShow) {
        try {
            if (mProgressDialog == null || mContext == null) {
                return;
            }
            if (isShow) {
                mProgressDialog.show();
            } else {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            SDKLogUtils.e(e.getMessage());
        }
    }

    /**
     * onActivityResults处调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void result2Activity(int requestCode, int resultCode, Intent data) {
        try {
            if (qqChannelManager != null) {
                qqChannelManager.result2Activity(requestCode, resultCode, data);
            }
            if (wbChannelManager != null) {
                wbChannelManager.result2Activity(requestCode, resultCode, data);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //***************************qq登录***************************88
    private QQChannelManager qqChannelManager;

    /**
     * qq登录
     */
    public void qqLogin() {
        if (qqChannelManager == null) {
            qqChannelManager = new QQChannelManager(mContext);
            qqChannelManager.setLoginListener(loginListener);
        }
        qqChannelManager.loginQQ();
    }

    //*********************微博登录*******************
    private WBChannelManager wbChannelManager;

    /**
     * 微博登录
     */
    public void wbLogin() {
        if (wbChannelManager == null) {
            wbChannelManager = new WBChannelManager(mContext, loginListener);
        }
        wbChannelManager.wbLogin();
    }


    //****************微信登录**********************
    private WXChannelManager wxChannelManager;

    private void initWXChannelManager() {
        if (wxChannelManager == null) {
            wxChannelManager = new WXChannelManager(mContext);
        }
    }

    public void wxLogin() {
        initWXChannelManager();
        wxChannelManager.wxLogin();
    }

    /**
     * 检查微信是否安装回调
     *
     * @param listener
     */
    public void setWXListener(WXListener listener) {
        initWXChannelManager();
        wxChannelManager.setListener(listener);
    }

}
