package com.wenyou.yuilibrary.transform;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * @description 向下旋转切换
 * @date: 2021/2/5 16:55
 * @author: jy
 */
public class RotateDownTransformer implements ViewPager.PageTransformer {

    private static final float ROT_MOD = -15f;

    @Override
    public void transformPage(View page, float position) {
        final float width = page.getWidth();
        final float height = page.getHeight();
        final float rotation = ROT_MOD * position * -1.25f;

        page.setPivotX(width * 0.5f);
        page.setPivotY(height);
        page.setRotation(rotation);
    }
}
