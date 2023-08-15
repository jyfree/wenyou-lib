package com.wenyou.sociallibrary.listener;

/**
 * @description
 * @date: 2021/12/16 14:02
 * @author: jy
 */
public interface OnSocialSdkShareListener {

    /**
     * 分享成功
     *
     * @param sharePlatform 分享平台
     */
    void shareSuccess(int sharePlatform);

    /**
     * 分享失败
     * 注意！！
     * 一、QQ只支持本地纯图片分享
     * 二、QQ不支持纯文本分享
     * 三、QQ不支持视频分享
     * 四、微博只支持本地视频分享
     * 五、微博不支持音频分享
     *
     * @param sharePlatform 分享平台
     */
    void shareFail(int sharePlatform, String error);

    /**
     * 取消分享
     *
     * @param sharePlatform 分享平台
     */
    void shareCancel(int sharePlatform);


}
