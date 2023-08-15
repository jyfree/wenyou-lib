package com.wenyou.yuilibrary.widget.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.wenyou.yuilibrary.R;
import com.wenyou.yuilibrary.widget.banner.base.BaseIndicatorBanner;


/**
 * @description 简单的文字轮播
 * @date: 2021/2/5 14:30
 * @author: jy
 */
public class SimpleTextBanner extends BaseIndicatorBanner<String, SimpleTextBanner> {
    public SimpleTextBanner(Context context) {
        super(context);
    }

    public SimpleTextBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleTextBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onTitleSelect(TextView tv, int position) {
    }

    @Override
    public View onCreateItemView(int position) {
        View inflate = View.inflate(getContext(), R.layout.yui_adapter_simple_text, null);
        TextView tv = inflate.findViewById(R.id.tv);
        tv.setText(mDatas.get(position));
        return inflate;
    }
}
