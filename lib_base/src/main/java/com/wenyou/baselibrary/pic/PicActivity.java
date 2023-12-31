package com.wenyou.baselibrary.pic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @description 读取摄像头|相册图片 activity，需要在manifest注册
 * @date: 2021/12/16 13:33
 * @author: jy
 */
public class PicActivity extends AppCompatActivity implements PicScanListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不接受触摸屏事件
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        Pic.getInstance().getPicManager().behavior(this, getIntent(), savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Pic.getInstance().getPicManager().onActivityResult(this, requestCode, resultCode, data, this);
    }

    @Override
    public void onScanFinish(String path, Uri uri, boolean isCrop) {
        Pic.getInstance().getPicManager().onScanCompleted(this, uri, isCrop);
    }
}
