package com.wenyou.yuilibrary.transform;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * @description 侧滑逐渐消失切换
 * @date: 2021/2/5 16:55
 * @author: jy
 */
public class FadeSlideTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {

        page.setTranslationX(0);

        if (position <= -1.0F || position >= 1.0F) {
            page.setAlpha(0.0F);
        } else if (position == 0.0F) {
            page.setAlpha(1.0F);
        } else {
            // position is between -1.0F & 0.0F OR 0.0F & 1.0F
            page.setAlpha(1.0F - Math.abs(position));
        }
    }
}
