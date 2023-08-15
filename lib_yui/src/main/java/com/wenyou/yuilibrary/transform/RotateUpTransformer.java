package com.wenyou.yuilibrary.transform;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * @description 向上旋转切换
 * @date: 2021/2/5 16:55
 * @author: jy
 */
public class RotateUpTransformer implements ViewPager.PageTransformer {

    private static final float ROT_MOD = -15f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        final float width = page.getWidth();
        final float rotation = ROT_MOD * position;

        page.setPivotX(width * 0.5f);
        page.setPivotY(0f);
        page.setTranslationX(0f);
        page.setRotation(rotation);
    }
}
