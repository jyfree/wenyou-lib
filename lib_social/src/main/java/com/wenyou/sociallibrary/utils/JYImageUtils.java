package com.wenyou.sociallibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.wenyou.sociallibrary.constant.SDKImageType;
import com.wenyou.sociallibrary.media.JYImage;

/**
 * @description 图片转换器
 * @date: 2021/12/16 14:05
 * @author: jy
 */
public class JYImageUtils {

    public static Bitmap getImageBitmap(Context context, JYImage jyImage) {
        Bitmap bitmap = null;
        if (jyImage == null) {
            SDKLogUtils.e("getImageBitmap--JYImage is null");
            return null;
        }
        if (jyImage.mObject == null) {
            SDKLogUtils.e("getImageBitmap--JYImage mObject is null");
            return null;
        }
        switch (jyImage.imageType) {
            case SDKImageType.URL_IMAGE:
                bitmap = SDKBitmapUtils.getBitmap(jyImage.mObject.toString());
                break;
            case SDKImageType.BITMAP_IMAGE:
                bitmap = (Bitmap) jyImage.mObject;
                break;
            case SDKImageType.RES_IMAGE:
                bitmap = SDKBitmapUtils.getBitmap(context, (Integer) jyImage.mObject);
                break;
            case SDKImageType.BYTE_ARRAY:
                bitmap = SDKBitmapUtils.getBitmap((byte[]) jyImage.mObject);
                break;
        }
        SDKLogUtils.i("getImageBitmap--bitmap:", bitmap);
        return bitmap;
    }

    public static String getImagePath(JYImage jyImage) {
        if (jyImage == null) {
            SDKLogUtils.e("getImagePath--JYImage is null");
            return "";
        }
        if (jyImage.mObject == null) {
            SDKLogUtils.e("getImagePath--JYImage mObject is null");
            return "";
        }
        return jyImage.mObject.toString();
    }


    public static byte[] getImageByteArray(JYImage jyImage) {
        if (jyImage == null) {
            SDKLogUtils.e("getImageByteArray--JYImage is null");
            return null;
        }
        if (jyImage.mObject == null) {
            SDKLogUtils.e("getImageByteArray--JYImage mObject is null");
            return null;
        }
        return (byte[]) jyImage.mObject;
    }
}
