package com.wenyou.yuilibrary.transform;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * @description 翻转切换
 * @date: 2021/2/5 16:55
 * @author: jy
 */
public class FlowTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(@NonNull View page, float position) {
        page.setRotationY(position * -30f);
    }
}
