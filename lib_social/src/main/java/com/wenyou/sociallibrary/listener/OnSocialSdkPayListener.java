package com.wenyou.sociallibrary.listener;

/**
 * @description
 * @date: 2021/12/16 14:02
 * @author: jy
 */
public interface OnSocialSdkPayListener {

    /**
     * 支付成功
     *
     * @param type    支付类型
     * @param orderId 订单id
     */
    void paySuccess(int type, String orderId);

    /**
     * 支付错误
     *
     * @param type  支付类型
     * @param error 错误信息
     */
    void payFail(int type, String error);

    /**
     * 取消支付
     *
     * @param type
     */
    void payCancel(int type);


}
