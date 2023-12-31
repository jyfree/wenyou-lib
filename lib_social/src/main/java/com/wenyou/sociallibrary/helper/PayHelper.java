package com.wenyou.sociallibrary.helper;

import android.app.ProgressDialog;
import android.content.Context;

import com.wenyou.sociallibrary.R;
import com.wenyou.sociallibrary.alipay.AliPayManager;
import com.wenyou.sociallibrary.alipay.PayBean;
import com.wenyou.sociallibrary.listener.OnSocialSdkPayListener;
import com.wenyou.sociallibrary.utils.SDKLogUtils;
import com.wenyou.sociallibrary.wx.WXListener;
import com.wenyou.sociallibrary.wx.WXPayBean;
import com.wenyou.sociallibrary.wx.WXPayManager;

/**
 * @description 第三方支付
 * @date: 2021/12/16 14:01
 * @author: jy
 */
public class PayHelper {

    private Context mContext;
    private ProgressDialog mProgressDialog;
    private OnSocialSdkPayListener listener;

    public PayHelper(Context context, OnSocialSdkPayListener listener) {
        this.mContext = context;
        this.listener = listener;
        initProgressDialog();
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(mContext.getString(R.string.social_sdk_pay_prompt));
        mProgressDialog.setCancelable(false);
    }

    public void showProgressDialog(boolean isShow) {
        try {
            if (mProgressDialog == null || mContext == null)
                return;
            if (isShow) {
                mProgressDialog.show();
            } else {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            SDKLogUtils.e(e.getMessage());
        }
    }

    //**********************************支付宝支付*********************************************

    /**
     * 支付宝支付
     *
     * @param payBean 订单参数
     */
    public void aliPay(String orderId, PayBean payBean) {
        getAliPayManager(orderId).pay(payBean);
    }

    public void aliPay(String orderId, String payInfo) {
        getAliPayManager(orderId).pay(payInfo);
    }

    private AliPayManager aliPayManager;

    private AliPayManager getAliPayManager(final String orderId) {
        //支付宝支付
        if (aliPayManager == null) {
            aliPayManager = new AliPayManager(mContext, new OnSocialSdkPayListener() {
                @Override
                public void paySuccess(int type, String id) {
                    listener.paySuccess(type, orderId);
                }

                @Override
                public void payFail(int type, String error) {
                    listener.payFail(type, error);
                }

                @Override
                public void payCancel(int type) {
                    listener.payCancel(type);
                }
            });
        }
        return aliPayManager;
    }


    //**********************************微信支付*********************************

    private WXPayManager wxPayManager;

    private void initWXPayManager() {
        if (wxPayManager == null) {
            wxPayManager = new WXPayManager(mContext);
        }
    }

    /**
     * 微信支付
     *
     * @param wxPayBean
     */
    public void wxPay(WXPayBean wxPayBean) {
        initWXPayManager();
        wxPayManager.genPayReq(wxPayBean);
        wxPayManager.pay();
    }

    /**
     * 本地调起微信支付测试示例
     */
    public void wxPayTest() {
        initWXPayManager();
        wxPayManager.localPayTask();
    }

    /**
     * 检查微信是否安装回调
     *
     * @param listener
     */
    public void setWxListener(WXListener listener) {
        initWXPayManager();
        wxPayManager.setListener(listener);
    }

}
