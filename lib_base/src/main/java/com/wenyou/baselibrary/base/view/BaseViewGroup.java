package com.wenyou.baselibrary.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

/**
 * @description
 * @date: 2021/12/16 11:53
 * @author: jy
 */
public abstract class BaseViewGroup extends ViewGroup {

    public BaseViewGroup(Context context) {
        this(context, null);
    }

    public BaseViewGroup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(context, initResLayoutId(), this);
        initUI(view);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    public abstract int initResLayoutId();

    public abstract void initUI(View view);


}
