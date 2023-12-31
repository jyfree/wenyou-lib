package com.wenyou.sociallibrary.wx;

import android.content.Context;

import com.wenyou.sociallibrary.SDKConfig;
import com.wenyou.sociallibrary.utils.SDKLogUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @description 微信登录
 * @date: 2021/12/16 14:07
 * @author: jy
 */
public class WXChannelManager {

    public IWXAPI mWxAPI;

    public Context mContext;

    public WXListener listener;

    public WXChannelManager(Context context) {
        this.mContext = context;
        init();
    }

    public void setListener(WXListener listener) {
        this.listener = listener;
    }

    private void init() {
        if (mWxAPI == null) {
            mWxAPI = WXAPIFactory.createWXAPI(mContext, SDKConfig.getWx_appID(), false);
        }
        mWxAPI.registerApp(SDKConfig.getWx_appID());
    }

    /**
     * 微信登录
     */
    public void wxLogin() {
        if (!checkInstallWX()) {
            return;
        }
        // send oauth request
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "none";
        boolean startWXSuccess = mWxAPI.sendReq(req);
        SDKLogUtils.i("wxLogin--startWXSuccess", startWXSuccess);
        if (listener != null) {
            listener.startWX(startWXSuccess);
        }
    }


    /**
     * 检测微信
     */
    public boolean checkInstallWX() {
        if (!mWxAPI.isWXAppInstalled()) {
            //提醒用户没有安装微信微信
            if (listener != null) {
                listener.installWXAPP();
            }
            return false;
        }
        return true;
    }

}
