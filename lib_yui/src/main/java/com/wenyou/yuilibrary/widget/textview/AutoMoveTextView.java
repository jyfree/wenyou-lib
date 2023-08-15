package com.wenyou.yuilibrary.widget.textview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * @description 自定义跑马灯TextView类
 * @date: 2021/9/15 14:03
 * @author: jy
 */
public class AutoMoveTextView extends AppCompatTextView {
    public AutoMoveTextView(Context context) {
        super(context);
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
    }

    public AutoMoveTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
    }

    public AutoMoveTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
