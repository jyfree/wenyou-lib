package com.wenyou.loadsir.simple;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.wenyou.loadsir.R;
import com.wenyou.loadsir.framework.callback.Callback;

/**
 * @description 动画回调
 * @date: 2021/12/22 15:47
 * @author: jy
 */
public class AnimateCallback extends Callback {

    private View animateView;

    @Override
    protected int onCreateView() {
        return R.layout.simple_loadsir_callback_animate;
    }

    @Override
    protected void onViewCreate(Context context, View view) {
        super.onViewCreate(context, view);
    }

    @Override
    public void onAttach(Context context, View view) {
        animateView = view.findViewById(R.id.loadSir_view_animate);
        Animation animation = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setRepeatCount(Integer.MAX_VALUE);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        animateView.startAnimation(animation);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (animateView != null) {
            animateView.clearAnimation();
        }
    }
}
