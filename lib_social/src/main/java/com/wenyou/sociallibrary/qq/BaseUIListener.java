package com.wenyou.sociallibrary.qq;

import com.wenyou.sociallibrary.utils.SDKLogUtils;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * @description
 * @date: 2021/12/16 14:04
 * @author: jy
 */
public class BaseUIListener implements IUiListener {


    BaseUIListener() {
        super();
    }


    @Override
    public void onComplete(Object response) {

        try {
            doComplete((JSONObject) response);
        } catch (Exception e) {
            SDKLogUtils.e(e.getMessage());
        }
    }

    protected void doComplete(JSONObject values) {

        SDKLogUtils.i("qq回调--doComplete", values);
    }

    @Override
    public void onError(UiError e) {

        SDKLogUtils.e("qq回调--onError", e.errorCode, e.errorDetail, e.errorMessage);
    }

    @Override
    public void onCancel() {
        SDKLogUtils.i("qq回调--onCancel");
    }

    @Override
    public void onWarning(int i) {
        SDKLogUtils.i("qq回调--onWarning：", i);
    }


}
