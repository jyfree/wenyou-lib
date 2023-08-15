package com.wenyou.yuilibrary.transform;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * @description 3D旋转切换
 * @date: 2021/2/5 16:55
 * @author: jy
 */
public class DepthTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE_DEPTH = 0.75f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        float alpha, scale, translationX;
        if (position > 0 && position < 1) {
            // moving to the right
            alpha = (1 - position);
            scale = MIN_SCALE_DEPTH + (1 - MIN_SCALE_DEPTH) * (1 - Math.abs(position));
            translationX = (page.getWidth() * -position);
        } else {
            // use default for all other cases
            alpha = 1;
            scale = 1;
            translationX = 0;
        }

        page.setAlpha(alpha);
        page.setTranslationX(translationX);
        page.setScaleX(scale);
        page.setScaleY(scale);
    }
}
