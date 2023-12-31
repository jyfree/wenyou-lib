package com.wenyou.sociallibrary.wx;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Patterns;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.wenyou.sociallibrary.constant.SDKImageType;
import com.wenyou.sociallibrary.media.BaseMediaObject;
import com.wenyou.sociallibrary.media.JYAudio;
import com.wenyou.sociallibrary.media.JYImage;
import com.wenyou.sociallibrary.media.JYText;
import com.wenyou.sociallibrary.media.JYVideo;
import com.wenyou.sociallibrary.media.JYWeb;
import com.wenyou.sociallibrary.utils.JYImageUtils;
import com.wenyou.sociallibrary.utils.SDKByteUtils;
import com.wenyou.sociallibrary.utils.SDKLogUtils;

/**
 * @description 微信分享
 * @date: 2021/12/16 14:08
 * @author: jy
 */
public class WXShareManager extends WXChannelManager {


    public WXShareManager(Context context) {
        super(context);
    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public void doShareAll(BaseMediaObject media, boolean isTimelineCb) {

        if (media instanceof JYWeb) {
            JYWeb jyWeb = (JYWeb) media;
            shareWeb(jyWeb.webUrl, jyWeb.title, jyWeb.description, JYImageUtils.getImageBitmap(mContext, jyWeb.thumb), isTimelineCb);
        } else if (media instanceof JYText) {
            JYText jyText = (JYText) media;
            shareText(jyText.content, jyText.content, isTimelineCb);
        } else if (media instanceof JYImage) {
            JYImage jyImage = (JYImage) media;
            if (jyImage.imageType == SDKImageType.URL_IMAGE) {
                SDKLogUtils.i("微信图片分享--图片路径分享");
                shareImage2Path(JYImageUtils.getImagePath(jyImage), JYImageUtils.getImageBitmap(mContext, jyImage.thumb), isTimelineCb);
            } else if (jyImage.imageType == SDKImageType.BYTE_ARRAY) {
                SDKLogUtils.i("微信图片分享--byte array分享");
                shareImage2ByteArray(JYImageUtils.getImageByteArray(jyImage), JYImageUtils.getImageBitmap(mContext, jyImage.thumb), isTimelineCb);
            } else {
                SDKLogUtils.i("微信图片分享--图片bitmap分享");
                shareImage2Bitmap(JYImageUtils.getImageBitmap(mContext, jyImage), JYImageUtils.getImageBitmap(mContext, jyImage.thumb), isTimelineCb);
            }
        } else if (media instanceof JYAudio) {
            JYAudio jyAudio = (JYAudio) media;
            shareAudio(jyAudio.audioUrl, jyAudio.title, jyAudio.description, JYImageUtils.getImageBitmap(mContext, jyAudio.thumb), isTimelineCb);
        } else if (media instanceof JYVideo) {
            JYVideo jyVideo = (JYVideo) media;
            if (!Patterns.WEB_URL.matcher(jyVideo.videoUrl).matches()) {
                SDKLogUtils.e("微信只支持在线视频分享--分享本地视频将无法观看");
            }
            shareVideo(jyVideo.videoUrl, jyVideo.title, jyVideo.description, JYImageUtils.getImageBitmap(mContext, jyVideo.thumb), isTimelineCb);
        } else {
            SDKLogUtils.e("未知分享类型");
        }
    }

    /**
     * 分享网页
     *
     * @param webUrl       网页地址
     * @param title        标题 512长度限制
     * @param description  描述 1024长度限制
     * @param thumb        缩略图 32K
     * @param isTimelineCb 是否分享到朋友圈
     */
    public void shareWeb(String webUrl, String title, String description, Bitmap thumb, boolean isTimelineCb) {
        if (!checkInstallWX())
            return;
        WXWebpageObject webPage = new WXWebpageObject();
        webPage.webpageUrl = webUrl;
        WXMediaMessage msg = new WXMediaMessage(webPage);
        msg.title = title;
        msg.description = description;
        if (thumb != null) {
            msg.thumbData = SDKByteUtils.INSTANCE.compressBmp2LimitSize(thumb, 32);
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = isTimelineCb ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        sendReq(req);

    }

    /**
     * 分享文本
     *
     * @param content      分享内容
     * @param description  分享描述
     * @param isTimelineCb 是否分享到朋友圈
     */
    public void shareText(String content, String description, boolean isTimelineCb) {
        if (!checkInstallWX())
            return;
        // 初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = content;

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = description;

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        //分享方式  朋友圈：WXSceneTimeline，好友会话：WXSceneSession
        req.scene = isTimelineCb ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;

        // 调用api接口发送数据到微信
        sendReq(req);
    }

    private void shareImage2Bitmap(Bitmap image, Bitmap thumb, boolean isTimelineCb) {
        if (image == null) {
            SDKLogUtils.d("微信图片分享--图片bitmap分享--bitmap为null");
        }
        WXImageObject imgObj = new WXImageObject(image);
        shareImage(imgObj, thumb, isTimelineCb);
    }

    private void shareImage2Path(String imagePath, Bitmap thumb, boolean isTimelineCb) {
        WXImageObject imgObj = new WXImageObject();
        imgObj.imagePath = imagePath;
        shareImage(imgObj, thumb, isTimelineCb);

    }

    private void shareImage2ByteArray(byte[] bytes, Bitmap thumb, boolean isTimelineCb) {
        WXImageObject imgObj = new WXImageObject();
        imgObj.imageData = bytes;
        shareImage(imgObj, thumb, isTimelineCb);

    }

    /**
     * 分享图片
     *
     * @param imgObj       图片实体
     * @param thumb        缩略图
     * @param isTimelineCb 是否分享到朋友圈
     */
    private void shareImage(WXImageObject imgObj, Bitmap thumb, boolean isTimelineCb) {
        if (!checkInstallWX())
            return;
        try {

            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = imgObj;
            if (thumb != null) {
                msg.thumbData = SDKByteUtils.INSTANCE.compressBmp2LimitSize(thumb, 32);
            }

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("img");
            req.message = msg;
            req.scene = isTimelineCb ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
            sendReq(req);

        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分享音乐
     *
     * @param musicUrl     音乐地址
     * @param title        标题
     * @param description  描述
     * @param thumb        缩略图
     * @param isTimelineCb 是否分享到朋友圈
     */
    private void shareAudio(String musicUrl, String title, String description, Bitmap thumb, boolean isTimelineCb) {
        if (!checkInstallWX())
            return;
        try {
            WXMusicObject music = new WXMusicObject();
            music.musicUrl = musicUrl;

            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = music;
            msg.title = title;
            msg.description = description;
            if (thumb != null) {
                msg.thumbData = SDKByteUtils.INSTANCE.compressBmp2LimitSize(thumb, 32);
            }

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("music");
            req.message = msg;
            req.scene = isTimelineCb ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
            sendReq(req);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分享视频
     *
     * @param videoUrl     视频地址
     * @param title        标题
     * @param description  说明
     * @param thumb        缩略图
     * @param isTimelineCb 是否分享到朋友圈
     */
    private void shareVideo(String videoUrl, String title, String description, Bitmap thumb, boolean isTimelineCb) {
        if (!checkInstallWX())
            return;
        try {
            WXVideoObject video = new WXVideoObject();
            video.videoUrl = videoUrl;

            WXMediaMessage msg = new WXMediaMessage(video);
            msg.title = title;
            msg.description = description;
            if (thumb != null) {
                msg.thumbData = SDKByteUtils.INSTANCE.compressBmp2LimitSize(thumb, 32);
            }

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("video");
            req.message = msg;
            req.scene = isTimelineCb ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
            sendReq(req);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendReq(SendMessageToWX.Req req) {
        boolean startWXSuccess = mWxAPI.sendReq(req);
        SDKLogUtils.i("wxShare--startWXSuccess", startWXSuccess);
        if (listener != null) {
            listener.startWX(startWXSuccess);
        }
    }
}
