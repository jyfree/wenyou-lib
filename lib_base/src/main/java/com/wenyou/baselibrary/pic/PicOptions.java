package com.wenyou.baselibrary.pic;

import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 * @description
 * @date: 2021/12/16 13:34
 * @author: jy
 */
public class PicOptions {

    private boolean isCrop;
    private int cropWidth;
    private int cropHeight;
    private String providerAuthority;
    private File cacheFile;

    private PicOptions(Builder builder) {
        isCrop = builder.isCrop;
        cropWidth = builder.cropWidth;
        cropHeight = builder.cropHeight;
        providerAuthority = builder.providerAuthority;
        cacheFile = createCacheFile();
    }

    private File createCacheFile() {
        String fileName = System.currentTimeMillis() + ".jpg";
        String filePath;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + fileName;
        } else {
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Pictures" + File.separator + fileName;
        }
        return new File(filePath);
    }

    public boolean isCrop() {
        return isCrop;
    }

    public int getCropWidth() {
        return cropWidth;
    }

    public int getCropHeight() {
        return cropHeight;
    }

    public String getProviderAuthority() {
        return providerAuthority;
    }

    public File getCacheFile() {
        return cacheFile;
    }

    public static Builder beginBuilder() {
        return new Builder();
    }

    public static class Builder {

        private boolean isCrop = false;
        private int cropWidth = 0;
        private int cropHeight = 0;
        private String providerAuthority;

        public Builder setCrop(boolean crop) {
            isCrop = crop;
            return this;
        }

        public Builder setCropWidth(int cropWidth) {
            this.cropWidth = cropWidth;
            return this;
        }

        public Builder setCropHeight(int cropHeight) {
            this.cropHeight = cropHeight;
            return this;
        }

        public Builder setProviderAuthority(String providerAuthority) {
            this.providerAuthority = providerAuthority;
            return this;
        }

        public PicOptions build() {
            return new PicOptions(this);
        }
    }
}
