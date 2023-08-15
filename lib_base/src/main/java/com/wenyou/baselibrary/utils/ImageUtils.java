package com.wenyou.baselibrary.utils;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.PostProcessor;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @description ImageDecoder图片工具类
 * @date: 2021/12/16 13:44
 * @author: jy
 */
@TargetApi(Build.VERSION_CODES.P)
public class ImageUtils {

    public static ImageUtils get() {
        return new ImageUtils();
    }

    public Decoder decode(@NonNull ImageDecoder.Source source) {
        return new Decoder(source);
    }

    public Decoder decode(@NonNull Context context, @DrawableRes int sourceId) {
        ImageDecoder.Source source = ImageDecoder.createSource(context.getResources(), sourceId);
        return new Decoder(source);
    }

    public Decoder decode(@NonNull Context context, @NonNull Uri uri) {
        ImageDecoder.Source source = ImageDecoder.createSource(context.getContentResolver(), uri);
        return new Decoder(source);
    }

    public Decoder decode(@NonNull ByteBuffer buffer) {
        ImageDecoder.Source source = ImageDecoder.createSource(buffer);
        return new Decoder(source);
    }

    public Decoder decode(@NonNull File file) {
        ImageDecoder.Source source = ImageDecoder.createSource(file);
        return new Decoder(source);
    }

    /**
     * 负责解码的类
     */
    public static class Decoder {
        private final ImageDecoder.Source source;
        //监听器数组
        private final List<ImageDecoder.OnHeaderDecodedListener> decodeListenerList = new ArrayList<>();
        //不完全解码监听器。若触发并返回true，则展示不完整的图片，缺损区域将会是空白
        private ImageDecoder.OnPartialImageListener partialImageListener = null;
        //图片预处理
        private PostProcessor postProcessor = null;
        private int sizeIndex = -1; //尺寸设置监听器角标
        private int sampleSizeIndex = -1;   //清晰度监听器角标

        //真正的头加载监听器，在里面遍历调用定义的方法
        private final ImageDecoder.OnHeaderDecodedListener mListener =
                new ImageDecoder.OnHeaderDecodedListener() {
                    @Override
                    public void onHeaderDecoded(@NonNull ImageDecoder decoder
                            , @NonNull ImageDecoder.ImageInfo info
                            , @NonNull ImageDecoder.Source source) {

                        //图片预处理
                        if (postProcessor != null)
                            decoder.setPostProcessor(postProcessor);
                        //不完整加载监听器
                        if (partialImageListener != null)
                            decoder.setOnPartialImageListener(partialImageListener);
                        //遍历已经添加的所有监听器
                        for (ImageDecoder.OnHeaderDecodedListener listener : decodeListenerList)
                            listener.onHeaderDecoded(decoder, info, source);
                    }
                };

        private Decoder(@NonNull ImageDecoder.Source source) {
            this.source = source;
        }

        /**
         * 获取圆角图片，会覆盖之前设置的postProcessor
         */
        public Decoder roundCorners(final float roundX, final float roundY) {
            postProcessor = new PostProcessor() {
                @Override
                public int onPostProcess(@NonNull Canvas canvas) {
                    Path path = new Path();
                    path.setFillType(Path.FillType.INVERSE_EVEN_ODD);
                    int width = canvas.getWidth();
                    int height = canvas.getHeight();
                    path.addRoundRect(0, 0, width, height, roundX, roundY
                            , Path.Direction.CW);
                    Paint paint = new Paint();
                    paint.setAntiAlias(true);
                    paint.setColor(Color.TRANSPARENT);
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                    canvas.drawPath(path, paint);
                    return PixelFormat.TRANSLUCENT;
                }
            };
            return this;
        }

        /**
         * 设置不完整加载监听器
         */
        public Decoder partialImageListener(ImageDecoder.OnPartialImageListener listener) {
            partialImageListener = listener;
            return this;
        }

        /**
         * 设置头解码监听器
         */
        public Decoder headDecodeListener(ImageDecoder.OnHeaderDecodedListener listener) {
            if (!decodeListenerList.contains(listener))
                decodeListenerList.add(listener);
            return this;
        }

        /**
         * 设置图片预处理方法
         */
        public Decoder postProcessor(PostProcessor postProcessor) {
            this.postProcessor = postProcessor;
            return this;
        }

        /**
         * 设置图片压缩
         */
        public Decoder setSampleSize(final int sampleSize) {
            if (sampleSizeIndex != -1)
                decodeListenerList.remove(sampleSizeIndex);
            sampleSizeIndex = decodeListenerList.size();
            decodeListenerList.add(new ImageDecoder.OnHeaderDecodedListener() {
                @Override
                public void onHeaderDecoded(@NonNull ImageDecoder decoder, @NonNull ImageDecoder.ImageInfo info, @NonNull ImageDecoder.Source source) {
                    decoder.setTargetSampleSize(sampleSize);
                }
            });
            return this;
        }

        /**
         * 设置图片尺寸
         */
        public Decoder setSampleSize(final int width, final int height) {
            if (sizeIndex != -1)
                decodeListenerList.remove(sizeIndex);
            sizeIndex = decodeListenerList.size();
            decodeListenerList.add(new ImageDecoder.OnHeaderDecodedListener() {
                @Override
                public void onHeaderDecoded(@NonNull ImageDecoder decoder, @NonNull ImageDecoder.ImageInfo info, @NonNull ImageDecoder.Source source) {
                    decoder.setTargetSize(width, height);
                }
            });
            return this;
        }

        /**
         * 解码Drawable
         */
        public Drawable decodeDrawable() throws IOException {
            return ImageDecoder.decodeDrawable(source, mListener);
        }

        /**
         * 解码Drawable(自动播放)
         * AnimatedImageDrawable类，用于绘制和显示 GIF 和 WebP 动画图像
         */
        public Drawable decodeDrawableAutoStart() throws IOException {
            Drawable decodedAnimation = ImageDecoder.decodeDrawable(source, mListener);
            if (decodedAnimation instanceof AnimatedImageDrawable) {
                // Prior to start(), the first frame is displayed.
                ((AnimatedImageDrawable) decodedAnimation).start();
            }
            return decodedAnimation;
        }

        /**
         * 解码Bitmap
         */
        public Bitmap decodeBitmap() throws IOException {
            return ImageDecoder.decodeBitmap(source, mListener);
        }
    }
}

