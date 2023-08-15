package com.wenyou.yuilibrary.widget.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


import com.wenyou.yuilibrary.R;
import com.wenyou.yuilibrary.utils.YUILogUtils;

import java.util.Locale;

/**
 * @description 倒计时TextView
 * @date: 2021/2/8 10:08
 * @author: jy
 */
public class CountDownTextView extends AppCompatTextView {

    /**
     * 默认时间间隔1000ms
     */
    private static final int DEFAULT_INTERVAL = 1000;
    /**
     * 默认时长60s
     */
    private static final int DEFAULT_COUNTDOWN_TIME = 60 * 1000;
    /**
     * 默认倒计时文字格式(显示秒数)
     */
    private static final String DEFAULT_COUNT_FORMAT = "%ds";
    /**
     * 默认按钮文字 {@link #getText()}
     */
    private String mDefaultText;
    /**
     * 倒计时时长，单位为毫秒
     */
    private long mCountDownTime;
    /**
     * 时间间隔，单位为毫秒
     */
    private long mInterval;
    /**
     * 倒计时文字格式
     */
    private String mCountDownFormat;
    /**
     * 倒计时是否可用
     */
    private boolean mEnableCountDown = true;

    /**
     * 倒计时
     */
    private CountDownTimer mCountDownTimer;

    /**
     * 是否正在执行倒计时
     */
    private boolean isCountDownNow;

    public CountDownTextView(Context context) {
        this(context, null);
    }

    public CountDownTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        // 获取自定义属性值
        initAttr(context, attrs, defStyleAttr);
    }

    /**
     * 获取自定义属性值
     *
     * @param context
     * @param attrs
     */
    private void initAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownTextView, defStyleAttr, 0);
        mCountDownFormat = typedArray.getString(R.styleable.CountDownTextView_cdt_countDownFormat);
        if (TextUtils.isEmpty(mCountDownFormat)) {
            mCountDownFormat = DEFAULT_COUNT_FORMAT;
        }
        mCountDownTime = typedArray.getInteger(R.styleable.CountDownTextView_cdt_countDown, DEFAULT_COUNTDOWN_TIME);
        mInterval = typedArray.getInteger(R.styleable.CountDownTextView_cdt_countDownInterval, DEFAULT_INTERVAL);

        typedArray.recycle();

        mEnableCountDown = mCountDownTime > mInterval;
    }


    /**
     * 开始计算
     */
    public void startCountDown() {
        if (!mEnableCountDown) {
            throw new SecurityException("总时长需要大于间隔时长，验证不通过");
        }
        mDefaultText = getText().toString();
        // 设置按钮不可点击
        setEnabled(false);
        initCountDown();
        // 开始倒计时
        mCountDownTimer.start();
        isCountDownNow = true;
    }

    private void initCountDown() {
        // 初始化倒计时Timer
        if (mCountDownTimer == null) {
            mCountDownTimer = new CountDownTimer(mCountDownTime, mInterval) {
                @Override
                public void onTick(long millisUntilFinished) {
                    setText(String.format(Locale.CHINA, mCountDownFormat, millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    isCountDownNow = false;
                    setEnabled(true);
                    setText(mDefaultText);
                }
            };
        }
    }

    public CountDownTextView setCountDownFormat(String countDownFormat) {
        mCountDownFormat = countDownFormat;
        return this;
    }

    public CountDownTextView setCountDownTime(long countDownTime) {
        mCountDownTime = countDownTime;
        return this;
    }

    public CountDownTextView setInterval(long interval) {
        mInterval = interval;
        return this;
    }

    /**
     * 是否正在执行倒计时
     *
     * @return 倒计时期间返回true否则返回false
     */
    public boolean isCountDownNow() {
        return isCountDownNow;
    }

    /**
     * 设置倒计时数据
     *
     * @param count           时长
     * @param interval        间隔
     * @param countDownFormat 文字格式
     */
    public CountDownTextView setCountDown(long count, long interval, String countDownFormat) {
        mCountDownTime = count;
        mCountDownFormat = countDownFormat;
        mInterval = interval;
        return this;
    }

    /**
     * 取消倒计时
     */
    public void cancelCountDown() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            YUILogUtils.INSTANCE.i(this.getClass().getSimpleName(), "取消倒计时");
        }
        isCountDownNow = false;
        setText(mDefaultText);
        setEnabled(true);
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (isCountDownNow()) {
            return;
        }
        super.setEnabled(enabled);
        setClickable(enabled);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelCountDown();
    }
}
