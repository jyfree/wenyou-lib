package com.wenyou.yuilibrary.widget.alpha;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;


/**
 * @description 在 pressed 和 disabled 时改变 View 的透明度
 * @date: 2021/2/5 17:54
 * @author: jy
 */
public class YUIAlphaConstraintLayout extends ConstraintLayout {

    private IAlphaViewHelper mAlphaViewHelper;

    public YUIAlphaConstraintLayout(Context context) {
        super(context);
    }

    public YUIAlphaConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YUIAlphaConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private IAlphaViewHelper getAlphaViewHelper() {
        if (mAlphaViewHelper == null) {
            mAlphaViewHelper = new YUIAlphaViewHelper(this);
        }
        return mAlphaViewHelper;
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        getAlphaViewHelper().onPressedChanged(this, pressed);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        getAlphaViewHelper().onEnabledChanged(this, enabled);
    }

    /**
     * 设置是否要在 press 时改变透明度
     *
     * @param changeAlphaWhenPress 是否要在 press 时改变透明度
     */
    public void setChangeAlphaWhenPress(boolean changeAlphaWhenPress) {
        getAlphaViewHelper().setChangeAlphaWhenPress(changeAlphaWhenPress);
    }

    /**
     * 设置是否要在 disabled 时改变透明度
     *
     * @param changeAlphaWhenDisable 是否要在 disabled 时改变透明度
     */
    public void setChangeAlphaWhenDisable(boolean changeAlphaWhenDisable) {
        getAlphaViewHelper().setChangeAlphaWhenDisable(changeAlphaWhenDisable);
    }

    /**
     * 设置 press 时透明度
     *
     * @param mPressedAlpha
     */
    public void setPressedAlpha(float mPressedAlpha) {
        getAlphaViewHelper().setPressedAlpha(mPressedAlpha);
    }

    /**
     * 设置 disabled 时透明度
     *
     * @param mDisabledAlpha
     */
    public void setDisabledAlpha(float mDisabledAlpha) {
        getAlphaViewHelper().setDisabledAlpha(mDisabledAlpha);
    }
}
