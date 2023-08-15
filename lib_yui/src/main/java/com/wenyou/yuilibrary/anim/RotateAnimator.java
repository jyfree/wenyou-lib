package com.wenyou.yuilibrary.anim;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @description 旋转动画
 * @date: 2021/2/5 13:55
 * @author: jy
 */
public class RotateAnimator extends BaseAnimator {
    public RotateAnimator() {
        this.mDuration = 200;
    }

    @Override
    public void setAnimation(View view) {
        this.mAnimatorSet.playTogether(ObjectAnimator.ofFloat(view, "rotation", 0, 180));
    }
}
