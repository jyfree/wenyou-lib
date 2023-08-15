package com.wenyou.yuilibrary.anim;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @description 缩放动画
 * @date: 2021/2/5 13:56
 * @author: jy
 */
public class ZoomInAnimator extends BaseAnimator {

    public ZoomInAnimator() {
        this.mDuration = 200;
    }

    @Override
    public void setAnimation(View view) {
        this.mAnimatorSet.playTogether(ObjectAnimator.ofFloat(view, "scaleX", 1.0F, 1.5F),
                ObjectAnimator.ofFloat(view, "scaleY", 1.0F, 1.5F));
    }
}
