package com.wenyou.baselibrary.base.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

/**
 * @description
 * @date: 2021/12/16 11:53
 * @author: jy
 */
public abstract class BaseDataViewGroup<D> extends BaseViewGroup {
    public D data;

    public BaseDataViewGroup(Context context) {
        super(context);
    }

    public BaseDataViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseDataViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setData(D data) {
        if (data != null) {
            this.data = data;
        }
    }
}
