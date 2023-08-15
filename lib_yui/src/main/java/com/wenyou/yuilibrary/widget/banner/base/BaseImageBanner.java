package com.wenyou.yuilibrary.widget.banner.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.wenyou.yuilibrary.strategy.ImageLoaderExt;
import com.wenyou.yuilibrary.widget.banner.BannerImageType;
import com.wenyou.yuilibrary.widget.banner.BannerItem;

import java.lang.ref.WeakReference;

/**
 * @description 图片轮播条
 * @date: 2021/2/5 13:49
 * @author: jy
 */
public abstract class BaseImageBanner<T extends BaseImageBanner<T>> extends BaseIndicatorBanner<BannerItem, T> {

    /**
     * 默认加载图片
     */
    protected int mPlaceHolder = 0;

    /**
     * 加载图片的高／宽比率
     */
    protected double mScale;

    public BaseImageBanner(Context context) {
        super(context);
        initImageBanner(context);
    }

    public BaseImageBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initImageBanner(context);
    }

    public BaseImageBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initImageBanner(context);
    }

    /**
     * 初始化ImageBanner
     *
     * @param context
     */
    protected void initImageBanner(Context context) {
        mScale = getContainerScale();
    }

    @Override
    public void onTitleSelect(TextView tv, int position) {
        final BannerItem item = getItem(position);
        if (item != null) {
            tv.setText(item.title);
        }
    }

    /**
     * @return 轮播布局的ID
     */
    protected abstract int getItemLayoutId();

    /**
     * @return 图片控件的ID
     */
    protected abstract int getImageViewId();

    /**
     * 创建ViewPager的Item布局
     *
     * @param position
     */
    @Override
    public View onCreateItemView(int position) {
        View inflate = View.inflate(getContext(), getItemLayoutId(), null);
        ImageView iv = inflate.findViewById(getImageViewId());

        //解决Glide资源释放的问题
        WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(iv);
        ImageView target = imageViewWeakReference.get();

        BannerItem item = getItem(position);
        if (item != null && target != null) {
            loadingImageView(target, item);
        }
        return inflate;
    }

    /**
     * 默认加载图片的方法，可以重写
     *
     * @param iv
     * @param item
     */
    protected void loadingImageView(ImageView iv, BannerItem item) {
        //设置图片间的距离
        iv.setPadding((int) imageGap, 0, (int) imageGap, 0);
        iv.setScaleType(imageScaleType);
        Object imgObject = item.imgObject;
        if (null == imgObject) {
            iv.setImageBitmap(null);
        } else {
            switch (item.type) {
                case BannerImageType.RES:
                    iv.setImageResource((Integer) imgObject);
                    break;
                case BannerImageType.BITMAP:
                    iv.setImageBitmap((Bitmap) imgObject);
                    break;
                case BannerImageType.URL:
                    if (!TextUtils.isEmpty(imgObject.toString())) {
                        if (mScale > 0) {
                            int itemWidth = getItemWidth();
                            int itemHeight = (int) (itemWidth * mScale);
                            iv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, itemHeight));
                        } else {
                            int itemWidth = getItemWidth();
                            int itemHeight = getItemHeight();
                            iv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, itemHeight));
                        }
                        ImageLoaderExt.INSTANCE.loadImage(iv, imgObject, mPlaceHolder, imageCircleRadius);
                    } else {
                        iv.setImageDrawable(getPlaceHolderDrawable());
                    }
                    break;
            }
        }
    }

    private Drawable getPlaceHolderDrawable() {
        if (mPlaceHolder == 0) {
            return new ColorDrawable(Color.parseColor("#555555"));
        } else {
            return ContextCompat.getDrawable(getContext(), mPlaceHolder);
        }
    }

    public T setPlaceHolder(int placeHolder) {
        mPlaceHolder = placeHolder;
        return (T) this;
    }

    public double getScale() {
        return mScale;
    }

    public T setScale(double scale) {
        mScale = scale;
        return (T) this;
    }

    @Override
    protected void onDetachedFromWindow() {
        //解决内存泄漏的问题
        pauseScroll();
        super.onDetachedFromWindow();
    }
}
