package com.wenyou.baselibrary.pic;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import com.wenyou.baselibrary.utils.BaseUtils;
import com.wenyou.baselibrary.utils.RomUtils;
import com.wenyou.baselibrary.utils.YHandlerUtils;
import com.wenyou.baselibrary.utils.YLogUtils;

import java.util.List;

/**
 * @description
 * @date: 2021/12/16 13:34
 * @author: jy
 */
public class PicManager {

    private final String TAG = "PicManager";

    private final int REQUEST_CODE_CAMERA = 1009;//拍照
    private final int REQUEST_CODE_PICTURE = 1008;//相册
    private final int REQUEST_CODE_CUT_PIC = 1007;//裁剪

    private final String REQUEST_TYPE = "requestType";

    private PicOptions picOptions;
    private PicListener listener;

    public PicManager setPicOptions(PicOptions picOptions) {
        this.picOptions = picOptions;
        return this;
    }

    public PicManager setListener(PicListener listener) {
        this.listener = listener;
        return this;
    }

    public void takeCamera(Context context) {
        startPicActivity(context, REQUEST_CODE_CAMERA);
    }

    public void takePhotoAlbum(Context context) {
        startPicActivity(context, REQUEST_CODE_PICTURE);
    }


    private void startPicActivity(Context context, int type) {
        Intent intent = new Intent(context, PicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(REQUEST_TYPE, type);
        context.startActivity(intent);
    }

    public void behavior(Activity activity, Intent intent, Bundle savedInstanceState) {
        if (intent == null) {
            YLogUtils.INSTANCE.eTag(TAG, "behavior intent is null");
            onDestroy(activity);
            return;
        }
        if (intent.getExtras() == null) {
            YLogUtils.INSTANCE.eTag(TAG, "behavior extras is null");
            onDestroy(activity);
            return;
        }
        if (savedInstanceState == null) {
            int requestType = intent.getExtras().getInt(REQUEST_TYPE);
            if (requestType == REQUEST_CODE_CAMERA) {
                executeCamera(activity);
            } else if (requestType == REQUEST_CODE_PICTURE) {
                executePhotoAlbum(activity);
            }
        }
    }

    private void executeCamera(Activity context) {

        String providerAuthority = context.getPackageName() + ".FileProvider";
        if (null != picOptions.getProviderAuthority() && !picOptions.getProviderAuthority().isEmpty()) {
            providerAuthority = picOptions.getProviderAuthority();
        }

        Uri imageUri;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                imageUri = FileProvider.getUriForFile(context, providerAuthority, picOptions.getCacheFile());
            } catch (Exception e) {
                e.printStackTrace();
                imageUri = Uri.fromFile(picOptions.getCacheFile());
            }
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            imageUri = Uri.fromFile(picOptions.getCacheFile());
        }
        // 指定调用相机拍照后照片的储存路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        context.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    private void executePhotoAlbum(Activity context) {
        String action = Intent.ACTION_PICK;
        if (RomUtils.isMiui()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                action = Intent.ACTION_OPEN_DOCUMENT;
            }
        }
        Intent intent = new Intent(action, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        context.startActivityForResult(intent, REQUEST_CODE_PICTURE);
    }

    private void crop(Activity context, Uri uri) {

        YLogUtils.INSTANCE.iTag(TAG, "crop uri：" + uri);

        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", picOptions.getCropWidth());
        intent.putExtra("outputY", picOptions.getCropHeight());
        intent.putExtra("return-data", false);

        Uri saveUri;
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion >= Build.VERSION_CODES.N) {
            String providerAuthority = context.getPackageName() + ".FileProvider";
            if (null != picOptions.getProviderAuthority() && !picOptions.getProviderAuthority().isEmpty()) {
                providerAuthority = picOptions.getProviderAuthority();
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            saveUri = FileProvider.getUriForFile(context, providerAuthority, picOptions.getCacheFile());
        } else {
            saveUri = Uri.fromFile(picOptions.getCacheFile());
        }
        // intent.putExtra(MediaStore.EXTRA_OUTPUT,
        intent.putExtra(MediaStore.EXTRA_OUTPUT, saveUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection

        //重要！！！添加权限，不然裁剪完后报 “保存时发生错误，保存失败”
        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, saveUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        if (componentName != null) {
            context.startActivityForResult(intent, REQUEST_CODE_CUT_PIC);
        }

    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data, PicScanListener scanListener) {
        if (listener == null || (requestCode != REQUEST_CODE_CAMERA && requestCode != REQUEST_CODE_PICTURE && requestCode != REQUEST_CODE_CUT_PIC)) {
            YLogUtils.INSTANCE.eTag(TAG, "onActivityResult Unknown requestCode or listener is null");
            onDestroy(activity);
            return;
        }
        if (resultCode != Activity.RESULT_OK) {
            YLogUtils.INSTANCE.eTag(TAG, "onActivityResult--cancel--resultCode", resultCode);
            onDestroy(activity);
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                YLogUtils.INSTANCE.iTag(TAG, "onActivityResult camera");
                scanFile(picOptions.getCacheFile().getAbsolutePath(), picOptions.isCrop(), scanListener);
                break;
            case REQUEST_CODE_PICTURE:

                if (data == null || data.getData() == null) {
                    YLogUtils.INSTANCE.eTag(TAG, "onActivityResult picture data is null ");
                    if (null != listener) {
                        listener.onTakePicFail();
                    }
                    onDestroy(activity);
                    return;
                }
                YLogUtils.INSTANCE.iTag(TAG, "onActivityResult picture");
                if (picOptions.isCrop()) {
                    crop(activity, data.getData());
                } else {
                    YLogUtils.INSTANCE.iTag(TAG, "onActivityResult picture callback uri：" + data.getData());
                    if (null != listener) {
                        listener.onTakePicSuccess(data.getData());
                    }
                    onDestroy(activity);
                }
                break;
            case REQUEST_CODE_CUT_PIC:
                YLogUtils.INSTANCE.iTag(TAG, "onActivityResult crop");
                scanFile(picOptions.getCacheFile().getAbsolutePath(), false, scanListener);
                break;
        }
    }

    private void scanFile(String path, boolean isCrop, PicScanListener scanListener) {
        new PicMediaScanner(BaseUtils.getApp(), path, "image/jpeg", isCrop, scanListener);
    }

    public void onScanCompleted(final Activity activity, final Uri uri, final boolean isCrop) {
        YHandlerUtils.INSTANCE.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isCrop) {
                    crop(activity, uri);
                } else {
                    YLogUtils.INSTANCE.iTag(TAG, "scanFile camera or crop callback uri：" + uri);
                    if (null != listener) {
                        listener.onTakePicSuccess(uri);
                    }
                    onDestroy(activity);
                }
            }
        });
    }

    /**
     * 摧毁本库的 PicActivity
     */
    private void onDestroy(Activity activity) {
        listener = null;
        if (activity != null) {
            activity.finish();
        }
    }
}
