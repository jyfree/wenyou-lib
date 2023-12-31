package com.wenyou.sociallibrary.wb;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Patterns;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoSourceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.common.UiError;
import com.sina.weibo.sdk.openapi.IWBAPI;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.wenyou.sociallibrary.SDKConfig;
import com.wenyou.sociallibrary.constant.SDKImageType;
import com.wenyou.sociallibrary.constant.SDKSharePlatform;
import com.wenyou.sociallibrary.listener.OnSocialSdkShareListener;
import com.wenyou.sociallibrary.media.BaseMediaObject;
import com.wenyou.sociallibrary.media.JYAudio;
import com.wenyou.sociallibrary.media.JYImage;
import com.wenyou.sociallibrary.media.JYText;
import com.wenyou.sociallibrary.media.JYVideo;
import com.wenyou.sociallibrary.media.JYWeb;
import com.wenyou.sociallibrary.utils.JYImageUtils;
import com.wenyou.sociallibrary.utils.SDKByteUtils;
import com.wenyou.sociallibrary.utils.SDKLogUtils;

import java.io.File;
import java.util.UUID;


/**
 * @description 微博分享
 * @date: 2021/12/16 14:06
 * @author: jy
 */
public class WBShareManager implements WbShareCallback {

    private Activity mContext;

    private boolean hasText;
    private boolean hasImage;
    private boolean hasWebPage;

    private OnSocialSdkShareListener listener;
    private IWBAPI iwbapi;


    public WBShareManager(Activity context, OnSocialSdkShareListener listener) {
        this.mContext = context;
        this.listener = listener;
        iwbapi = SDKConfig.initWB(context);
        setShareAllType();

    }


    /**
     * onActivityResults处调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void result2Activity(int requestCode, int resultCode, Intent data) {
        if (iwbapi != null) {
            iwbapi.doResultIntent(data, this);
        }
    }


    /**
     * 设置是否分享text文本
     *
     * @param hasText
     */
    public void setHasText(boolean hasText) {
        this.hasText = hasText;
    }

    /**
     * 设置是否分享图片
     *
     * @param hasImage
     */
    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    /**
     * 设置是否分享网页
     *
     * @param hasWebPage
     */
    public void setHasWebpage(boolean hasWebPage) {
        this.hasWebPage = hasWebPage;
    }

    /**
     * 设置分享全部类型
     */
    public void setShareAllType() {
        hasText = true;
        hasImage = true;
        hasWebPage = true;
    }

    /**
     * 分享文本消息
     *
     * @param text 消息内容
     */
    public void shareTextMsg(String text) {
        hasText = true;
        hasImage = false;
        hasWebPage = false;
        shareMultiMsg(text, null, null, null, null, null, null, null, null);
    }

    /**
     * 分享图片消息
     *
     * @param imagePath 图片路径
     */
    public void shareImageMsg(String imagePath) {
        hasText = false;
        hasImage = true;
        hasWebPage = false;
        shareMultiMsg(null, imagePath, null, null, null, null, null, null, null);
    }

    /**
     * 分享图片消息
     *
     * @param bitmap bitmap对象
     */
    public void shareImageMsg(Bitmap bitmap) {
        hasText = false;
        hasImage = true;
        hasWebPage = false;
        shareMultiMsg(null, null, bitmap, null, null, null, null, null, null);
    }

    /**
     * 分享图片消息
     *
     * @param bytes bitmap byte数组
     */
    public void shareImageMsg(byte[] bytes) {
        hasText = false;
        hasImage = true;
        hasWebPage = false;
        shareMultiMsg(null, null, null, bytes, null, null, null, null, null);
    }

    /**
     * 分享网页消息
     *
     * @param title       标题
     * @param description 描述
     * @param defaultText 默认text
     * @param targetUrl   目标链接地址
     * @param thumbImage  缩略图
     */
    public void shareWebpageMsg(String title, String description, String defaultText, String targetUrl, Bitmap thumbImage) {
        hasText = false;
        hasImage = false;
        hasWebPage = true;
        shareMultiMsg(null, null, null, null, title, description, defaultText, targetUrl, thumbImage);
    }

    /**
     * 分享视频
     *
     * @param videoPath 视频地址
     * @param title     标题
     */
    public void shareVideo(String videoPath, String title) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.textObject = getTextObj(title);
        weiboMessage.videoSourceObject = getVideoObject(videoPath);

        iwbapi.shareMessage(mContext, weiboMessage, false);
    }

    /**
     * 多种分享方式结合
     * 分享类型：SHARE_ALL_IN_ONE
     *
     * @param text        文本消息分享
     * @param imagePath   图片消息分享
     * @param title       标题（网页消息分享）
     * @param description 描述（网页消息分享）
     * @param defaultText 默认text（网页消息分享）
     * @param targetUrl   目标链接地址（网页消息分享）
     * @param thumbImage  缩略图（网页消息分享）
     */
    public void shareMultiMsg(String text, String imagePath, Bitmap imageBitmap, byte[] imageByteArray, String title, String description, String defaultText, String targetUrl, Bitmap thumbImage) {
        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (hasText) {
            weiboMessage.textObject = getTextObj(text);
        }
        if (hasImage) {
            if (imagePath == null && imageBitmap == null && imageByteArray == null) {
                SDKLogUtils.e("微博分享--image is null");
            } else if (imageBitmap != null) {
                weiboMessage.imageObject = getImageObj(imageBitmap);
            } else if (imageByteArray != null) {
                weiboMessage.imageObject = getImageObj(imageByteArray);
            } else {
                weiboMessage.imageObject = getImageObj(imagePath);
            }
        }
        if (hasWebPage) {
            weiboMessage.mediaObject = getWebpageObj(title, description, defaultText, targetUrl, thumbImage);
        }
        iwbapi.shareMessage(mContext, weiboMessage, false);

    }

    public void doShareAll(BaseMediaObject media) {

        if (media instanceof JYWeb) {
            hasText = false;
            hasImage = true;
            hasWebPage = true;

            JYWeb jyWeb = (JYWeb) media;
            Bitmap bitmap = JYImageUtils.getImageBitmap(mContext, jyWeb.thumb);
            shareMultiMsg(null, null, bitmap, null, jyWeb.title, jyWeb.description, jyWeb.description, jyWeb.webUrl, bitmap);
        } else if (media instanceof JYImage) {
            JYImage jyImage = (JYImage) media;
            if (jyImage.imageType == SDKImageType.URL_IMAGE) {
                shareImageMsg(JYImageUtils.getImagePath(jyImage));
            } else if (jyImage.imageType == SDKImageType.BYTE_ARRAY) {
                shareImageMsg(JYImageUtils.getImageByteArray(jyImage));
            } else {
                shareImageMsg(JYImageUtils.getImageBitmap(mContext, jyImage));
            }
        } else if (media instanceof JYText) {
            JYText jyText = (JYText) media;
            shareTextMsg(jyText.content);
        } else if (media instanceof JYVideo) {
            JYVideo jyVideo = (JYVideo) media;
            if (Patterns.WEB_URL.matcher(jyVideo.videoUrl).matches()) {
                listener.shareFail(SDKSharePlatform.WB, "微博只支持本地视频分享");
                return;
            }
            SDKLogUtils.i("微博分享视频，需要稍等片刻才能在微博上看到，因为需要上传文件");
            shareVideo(jyVideo.videoUrl, jyVideo.title);
        } else if (media instanceof JYAudio) {
            listener.shareFail(SDKSharePlatform.WB, "微博不支持音频分享");
        } else {
            listener.shareFail(SDKSharePlatform.WB, "未知分享类型");
        }
    }

    /**
     * 创建文本消息对象。
     *
     * @param text 消息内容
     * @return 文本消息对象
     */
    private TextObject getTextObj(String text) {
        TextObject textObject = new TextObject();
        textObject.text = text;
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @param imagePath 图片路径
     * @return 图片消息对象
     */
    private ImageObject getImageObj(String imagePath) {
        ImageObject imageObject = new ImageObject();
        imageObject.imagePath = imagePath;
        return imageObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @param bitmap 图片
     * @return 图片消息对象
     */
    private ImageObject getImageObj(Bitmap bitmap) {
        ImageObject imageObject = new ImageObject();
        imageObject.imageData = SDKByteUtils.INSTANCE.compressBmp2LimitSize(bitmap, 1024);
        return imageObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @param bytes 图片byte数组
     * @return 图片消息对象
     */
    private ImageObject getImageObj(byte[] bytes) {
        ImageObject imageObject = new ImageObject();
        imageObject.imageData = bytes;
        return imageObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @param title       标题
     * @param description 描述
     * @param targetUrl   目标链接地址
     * @param thumbImage  缩略图  （注意：最终压缩过的缩略图大小不得超过 32kb)
     * @return 多媒体（网页）消息对象
     */
    private WebpageObject getWebpageObj(String title, String description, String defaultText, String targetUrl, Bitmap thumbImage) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = UUID.randomUUID().toString();
        mediaObject.title = title;
        mediaObject.description = description;
        if (!thumbImage.isRecycled()) {
            mediaObject.thumbData = SDKByteUtils.INSTANCE.compressBmp2LimitSize(thumbImage, 32);
        }
        mediaObject.actionUrl = targetUrl;
        mediaObject.defaultText = defaultText;
        return mediaObject;
    }

    /**
     * 分享视频
     *
     * @param videoPath
     * @return
     */
    private VideoSourceObject getVideoObject(String videoPath) {
        VideoSourceObject videoSourceObject = new VideoSourceObject();
        videoSourceObject.videoPath = Uri.fromFile(new File(videoPath));
        return videoSourceObject;
    }

    @Override
    public void onComplete() {
        SDKLogUtils.i("微博分享授权--成功");
        listener.shareSuccess(SDKSharePlatform.WB);
    }

    @Override
    public void onCancel() {
        SDKLogUtils.i("微博分享授权--取消");
        listener.shareCancel(SDKSharePlatform.WB);
    }

    @Override
    public void onError(UiError error) {
        SDKLogUtils.e("微博分享授权--失败");
        listener.shareFail(SDKSharePlatform.WB, "分享失败");
    }
}
