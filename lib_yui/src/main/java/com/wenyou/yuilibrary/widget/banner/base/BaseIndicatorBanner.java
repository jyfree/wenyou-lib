package com.wenyou.yuilibrary.widget.banner.base;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wenyou.yuilibrary.R;
import com.wenyou.yuilibrary.YUIHelper;
import com.wenyou.yuilibrary.anim.BaseAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 轮播下方的引导器
 * @date: 2021/2/5 13:50
 * @author: jy
 */
public abstract class BaseIndicatorBanner<E, T extends BaseIndicatorBanner<E, T>> extends BaseBanner<E, T> {
    public static final int STYLE_DRAWABLE_RESOURCE = 0;
    public static final int STYLE_CORNER_RECTANGLE = 1;

    private List<ImageView> mIndicatorViews = new ArrayList<>();
    private int mIndicatorStyle;
    private int mIndicatorWidth;
    private int mIndicatorHeight;
    private int mIndicatorGap;
    private int mIndicatorCornerRadius;
    private boolean isShowSingleIndicator;

    private Drawable mSelectDrawable;
    private Drawable mUnSelectDrawable;
    private int mSelectColor;
    private int mUnSelectColor;

    private Class<? extends BaseAnimator> mSelectAnimClass;
    private Class<? extends BaseAnimator> mUnSelectAnimClass;

    private LinearLayout mLlIndicators;

    public BaseIndicatorBanner(Context context) {
        super(context);
        initAttrs(context, null);
    }

    public BaseIndicatorBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public BaseIndicatorBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(context, attrs);
    }

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BaseIndicatorBanner);
        mIndicatorStyle = ta.getInt(R.styleable.BaseIndicatorBanner_bb_indicatorStyle, STYLE_CORNER_RECTANGLE);
        mIndicatorWidth = ta.getDimensionPixelSize(R.styleable.BaseIndicatorBanner_bb_indicatorWidth, YUIHelper.INSTANCE.dip2px(6));
        mIndicatorHeight = ta.getDimensionPixelSize(R.styleable.BaseIndicatorBanner_bb_indicatorHeight, YUIHelper.INSTANCE.dip2px(6));
        mIndicatorGap = ta.getDimensionPixelSize(R.styleable.BaseIndicatorBanner_bb_indicatorGap, YUIHelper.INSTANCE.dip2px(6));
        mIndicatorCornerRadius = ta.getDimensionPixelSize(R.styleable.BaseIndicatorBanner_bb_indicatorCornerRadius, YUIHelper.INSTANCE.dip2px(3));
        mSelectColor = ta.getColor(R.styleable.BaseIndicatorBanner_bb_indicatorSelectColor, Color.parseColor("#ffffff"));
        mUnSelectColor = ta.getColor(R.styleable.BaseIndicatorBanner_bb_indicatorUnSelectColor, Color.parseColor("#88ffffff"));
        isShowSingleIndicator = ta.getBoolean(R.styleable.BaseIndicatorBanner_bb_isShowSingleIndicator, true);

        int selectRes = ta.getResourceId(R.styleable.BaseIndicatorBanner_bb_indicatorSelectRes, 0);
        int unSelectRes = ta.getResourceId(R.styleable.BaseIndicatorBanner_bb_indicatorUnSelectRes, 0);
        ta.recycle();

        //create indicator container
        mLlIndicators = new LinearLayout(context);
        mLlIndicators.setGravity(Gravity.CENTER);

        setIndicatorSelectorRes(unSelectRes, selectRes);
    }

    @Override
    public View onCreateIndicator() {
        if (mIndicatorStyle == STYLE_CORNER_RECTANGLE) {//rectangle
            this.mUnSelectDrawable = getDrawable(mUnSelectColor, mIndicatorCornerRadius);
            this.mSelectDrawable = getDrawable(mSelectColor, mIndicatorCornerRadius);
        }

        int size = mDatas.size();
        mIndicatorViews.clear();

        mLlIndicators.removeAllViews();
        if (!isShowSingleIndicator && size == 1){
            return mLlIndicators;
        }
        for (int i = 0; i < size; i++) {
            ImageView iv = new ImageView(getContext());
            iv.setImageDrawable(i == mCurrentPosition ? mSelectDrawable : mUnSelectDrawable);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(mIndicatorWidth,
                    mIndicatorHeight);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                lp.setMarginStart(i == 0 ? 0 : mIndicatorGap);
            } else {
                lp.setMargins(i == 0 ? 0 : mIndicatorGap, 0, 0, 0);
            }
            mLlIndicators.addView(iv, lp);
            mIndicatorViews.add(iv);
        }

        setCurrentIndicator(mCurrentPosition);

        return mLlIndicators;
    }

    @Override
    public void setCurrentIndicator(int position) {
        if (mIndicatorViews.isEmpty()){
            return;
        }
        for (int i = 0; i < mIndicatorViews.size(); i++) {
            mIndicatorViews.get(i).setImageDrawable(i == position ? mSelectDrawable : mUnSelectDrawable);
        }
        try {
            if (mSelectAnimClass != null) {
                if (position == mLastPosition) {
                    mSelectAnimClass.newInstance().playOn(mIndicatorViews.get(position));
                } else {
                    mSelectAnimClass.newInstance().playOn(mIndicatorViews.get(position));
                    if (mUnSelectAnimClass == null) {
                        mSelectAnimClass.newInstance().interpolator(new ReverseInterpolator()).playOn(mIndicatorViews.get(mLastPosition));
                    } else {
                        mUnSelectAnimClass.newInstance().playOn(mIndicatorViews.get(mLastPosition));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置显示样式,STYLE_DRAWABLE_RESOURCE or STYLE_CORNER_RECTANGLE
     */
    public T setIndicatorStyle(int indicatorStyle) {
        this.mIndicatorStyle = indicatorStyle;
        return (T) this;
    }

    /**
     * 设置显示宽度,单位dp,默认6dp
     */
    public T setIndicatorWidth(float indicatorWidth) {
        this.mIndicatorWidth = YUIHelper.INSTANCE.dip2px(indicatorWidth);
        return (T) this;
    }

    /**
     * 设置显示器高度,单位dp,默认6dp
     */
    public T setIndicatorHeight(float indicatorHeight) {
        this.mIndicatorHeight = YUIHelper.INSTANCE.dip2px(indicatorHeight);
        return (T) this;
    }

    /**
     * 设置两个显示器间距,单位dp,默认6dp
     */
    public T setIndicatorGap(float indicatorGap) {
        this.mIndicatorGap = YUIHelper.INSTANCE.dip2px(indicatorGap);
        return (T) this;
    }

    /**
     * 设置显示器选中颜色(for STYLE_CORNER_RECTANGLE),默认"#ffffff"
     */
    public T setIndicatorSelectColor(int selectColor) {
        this.mSelectColor = selectColor;
        return (T) this;
    }

    /**
     * 设置显示器未选中颜色(for STYLE_CORNER_RECTANGLE),默认"#88ffffff"
     */
    public T setIndicatorUnSelectColor(int unSelectColor) {
        this.mUnSelectColor = unSelectColor;
        return (T) this;
    }

    /**
     * 设置显示器圆角弧度(for STYLE_CORNER_RECTANGLE),单位dp,默认3dp
     */
    public T setIndicatorCornerRadius(float indicatorCornerRadius) {
        this.mIndicatorCornerRadius = YUIHelper.INSTANCE.dip2px(indicatorCornerRadius);
        return (T) this;
    }

    /**
     * 设置显示器选中以及未选中资源(for STYLE_DRAWABLE_RESOURCE)
     */
    public T setIndicatorSelectorRes(int unSelectRes, int selectRes) {
        try {
            if (mIndicatorStyle == STYLE_DRAWABLE_RESOURCE) {
                if (selectRes != 0) {
                    this.mSelectDrawable = getResources().getDrawable(selectRes);
                }
                if (unSelectRes != 0) {
                    this.mUnSelectDrawable = getResources().getDrawable(unSelectRes);
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return (T) this;
    }

    /**
     * 设置显示器选中动画
     */
    public T setSelectAnimClass(Class<? extends BaseAnimator> selectAnimClass) {
        this.mSelectAnimClass = selectAnimClass;
        return (T) this;
    }

    /**
     * 设置显示器未选中动画
     */
    public T setUnSelectAnimClass(Class<? extends BaseAnimator> unSelectAnimClass) {
        this.mUnSelectAnimClass = unSelectAnimClass;
        return (T) this;
    }

    private static class ReverseInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }
    }

    private GradientDrawable getDrawable(int color, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setColor(color);

        return drawable;
    }
}
