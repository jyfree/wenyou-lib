package com.wenyou.baselibrary.pic;

import android.net.Uri;

/**
 * @description
 * @date: 2021/12/16 13:34
 * @author: jy
 */
public interface PicScanListener {
    void onScanFinish(String path, Uri uri, boolean isCrop);
}
