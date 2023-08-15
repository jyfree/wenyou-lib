package com.wenyou.sociallibrary.helper;

import android.app.Activity;
import android.content.Intent;

import com.wenyou.sociallibrary.constant.SDKSharePlatform;
import com.wenyou.sociallibrary.listener.OnSocialSdkShareListener;
import com.wenyou.sociallibrary.media.BaseMediaObject;
import com.wenyou.sociallibrary.qq.QQShareManager;
import com.wenyou.sociallibrary.wb.WBShareManager;
import com.wenyou.sociallibrary.wx.WXListener;
import com.wenyou.sociallibrary.wx.WXShareManager;

/**
 * @description 第三方分享
 * @date: 2021/12/16 14:01
 * @author: jy
 */
public class ShareHelper {

    public Activity mContext;
    //QQ分享
    private QQShareManager qqShareManager;
    //微信分享
    private WXShareManager wxShareManager;
    //微博分享
    private WBShareManager wbShareManager;

    //回调接口
    private OnSocialSdkShareListener listener;


    public ShareHelper(Activity context, OnSocialSdkShareListener listener) {
        this.mContext = context;
        this.listener = listener;
        initialize();
    }

    private void initialize() {
        qqShareManager = new QQShareManager(mContext);
        qqShareManager.setQqShareListener(listener);
        wxShareManager = new WXShareManager(mContext);
        wbShareManager = new WBShareManager(mContext, listener);
    }

    /**
     * 检查微信是否安装回调
     *
     * @param listener
     */
    public void setWXListener(WXListener listener) {
        wxShareManager.setListener(listener);
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
            if (qqShareManager != null) {
                qqShareManager.result2Activity(requestCode, resultCode, data);
            }
            if (wbShareManager != null) {
                wbShareManager.result2Activity(requestCode, resultCode, data);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void share(int sharePlatform, BaseMediaObject media) {
        switch (sharePlatform) {
            case SDKSharePlatform.QQ_QZONE:
                qqShareManager.doShareAll(media, true);
                break;
            case SDKSharePlatform.QQ_FRIENDS:
                qqShareManager.doShareAll(media, false);
                break;
            case SDKSharePlatform.WX_CB:
                wxShareManager.doShareAll(media, true);
                break;
            case SDKSharePlatform.WX_FRIENDS:
                wxShareManager.doShareAll(media, false);
                break;
            case SDKSharePlatform.WB:
                wbShareManager.doShareAll(media);
                break;
        }
    }
}
