package com.wenyou.yuilibrary.anim;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @description 没有感知动画
 * @date: 2021/2/5 13:57
 * @author: jy
 */
public class NoAnimator extends BaseAnimator {

    public NoAnimator() {
        this.mDuration = 200;
    }

    @Override
    public void setAnimation(View view) {
        this.mAnimatorSet.playTogether(ObjectAnimator.ofFloat(view, "alpha", 1, 1));
    }
}
