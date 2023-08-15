
package com.wenyou.yuilibrary.widget.actionbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import com.wenyou.yuilibrary.R;
import com.wenyou.yuilibrary.YUIHelper;
import com.wenyou.yuilibrary.utils.ThemeUtils;
import com.wenyou.yuilibrary.widget.alpha.YUIAlphaImageView;
import com.wenyou.yuilibrary.widget.alpha.YUIAlphaLinearLayout;
import com.wenyou.yuilibrary.widget.alpha.YUIAlphaTextView;
import com.wenyou.yuilibrary.widget.textview.AutoMoveTextView;

import java.util.LinkedList;

/**
 * @description 标题栏
 * @date: 2021/9/15 13:56
 * @author: jy
 */
public class TitleBar extends ViewGroup implements View.OnClickListener {
    //中间title的对齐方式
    public static final int CENTER_CENTER = 0;
    public static final int CENTER_LEFT = 1;
    public static final int CENTER_RIGHT = 2;

    private YUIAlphaTextView mLeftText;
    private YUIAlphaLinearLayout mCenterLayout;
    private LinearLayout mRightLayout;
    private TextView mCenterText;
    private TextView mSubTitleText;
    private View mCustomCenterView;
    private View mDividerView;

    /**
     * 屏幕宽
     */
    private int mScreenWidth;
    /**
     * 标题栏的高度
     */
    private int mBarHeight;

    /**
     * 点击动作控件的padding
     */
    private int mActionPadding;
    /**
     * 左右侧文字的padding
     */
    private int mSideTextPadding;
    private int mCenterGravity;

    private float mSideTextSize;
    private float mTitleTextSize;
    private float mSubTitleTextSize;
    private float mActionTextSize;

    private int mSideTextColor;
    private int mTitleTextColor;
    private int mSubTitleTextColor;
    private int mActionTextColor;

    private Drawable mLeftImageResource;
    private String mLeftTextString;
    private String mTitleTextString;
    private String mSubTextString;
    private int mDividerColor;
    private int mDivideHeight;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
        init(context);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar,
                defStyleAttr, 0);
        mBarHeight = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tb_barHeight,
                YUIHelper.INSTANCE.dip2px(52));

        mActionPadding = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tb_actionPadding,
                YUIHelper.INSTANCE.dip2px(8));
        mSideTextPadding = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tb_sideTextPadding,
                YUIHelper.INSTANCE.dip2px(15));
        mCenterGravity = typedArray.getInt(R.styleable.TitleBar_tb_centerGravity, CENTER_CENTER);

        mSideTextSize = typedArray.getDimension(R.styleable.TitleBar_tb_sideTextSize,
                YUIHelper.INSTANCE.sp2px(18f));
        mTitleTextSize = typedArray.getDimension(R.styleable.TitleBar_tb_titleTextSize,
                YUIHelper.INSTANCE.sp2px(21f));
        mSubTitleTextSize = typedArray.getDimension(R.styleable.TitleBar_tb_subTitleTextSize,
                YUIHelper.INSTANCE.sp2px(10f));
        mActionTextSize = typedArray.getDimension(R.styleable.TitleBar_tb_actionTextSize,
                YUIHelper.INSTANCE.sp2px(18f));

        mSideTextColor = typedArray.getColor(R.styleable.TitleBar_tb_sideTextColor, Color.BLACK);
        mTitleTextColor = typedArray.getColor(R.styleable.TitleBar_tb_titleTextColor, Color.BLACK);
        mSubTitleTextColor = typedArray.getColor(R.styleable.TitleBar_tb_subTitleTextColor, Color.BLACK);
        mActionTextColor = typedArray.getColor(R.styleable.TitleBar_tb_actionTextColor, Color.BLACK);

        mLeftImageResource = ThemeUtils.INSTANCE.getDrawableAttrRes(getContext(), typedArray,
                R.styleable.TitleBar_tb_leftImageResource);
        mLeftTextString = typedArray.getString(R.styleable.TitleBar_tb_leftText);
        mTitleTextString = typedArray.getString(R.styleable.TitleBar_tb_titleText);
        mSubTextString = typedArray.getString(R.styleable.TitleBar_tb_subTitleText);
        mDividerColor = typedArray.getColor(R.styleable.TitleBar_tb_dividerColor, Color.TRANSPARENT);
        mDivideHeight = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tb_dividerHeight,
                YUIHelper.INSTANCE.dip2px(1f));

        typedArray.recycle();
    }

    private void init(Context context) {
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        initView(context);
    }

    private void initView(Context context) {
        mLeftText = new YUIAlphaTextView(context);
        mCenterLayout = new YUIAlphaLinearLayout(context);
        mRightLayout = new LinearLayout(context);
        mDividerView = new View(context);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);

        mLeftText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSideTextSize);
        mLeftText.setTextColor(mSideTextColor);
        mLeftText.setText(mLeftTextString);
        if (mLeftImageResource != null) {
            mLeftText.setCompoundDrawablesRelativeWithIntrinsicBounds(mLeftImageResource, null, null, null);
        }
        mLeftText.setSingleLine();

        mLeftText.setGravity(Gravity.CENTER_VERTICAL);
        mLeftText.setPadding(mSideTextPadding, 0, mSideTextPadding, 0);

        mCenterText = new AutoMoveTextView(context);
        mSubTitleText = new TextView(context);
        if (!TextUtils.isEmpty(mSubTextString)) {
            mCenterLayout.setOrientation(LinearLayout.VERTICAL);
        }
        mCenterText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);
        mCenterText.setTextColor(mTitleTextColor);
        mCenterText.setText(mTitleTextString);
        mCenterText.setSingleLine();
        mCenterText.setEllipsize(TextUtils.TruncateAt.MARQUEE);

        mSubTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSubTitleTextSize);
        mSubTitleText.setTextColor(mSubTitleTextColor);
        mSubTitleText.setText(mSubTextString);
        mSubTitleText.setSingleLine();
        mSubTitleText.setPadding(0, YUIHelper.INSTANCE.dip2px(2f), 0, 0);
        mSubTitleText.setEllipsize(TextUtils.TruncateAt.END);

        if (mCenterGravity == CENTER_LEFT) {
            setCenterGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        } else if (mCenterGravity == CENTER_RIGHT) {
            setCenterGravity(Gravity.CENTER_VERTICAL | Gravity.END);
        } else {
            setCenterGravity(Gravity.CENTER);
        }
        mCenterLayout.addView(mCenterText);
        mCenterLayout.addView(mSubTitleText);

        mRightLayout.setPadding(mSideTextPadding, 0, mSideTextPadding, 0);

        mDividerView.setBackgroundColor(mDividerColor);

        addView(mLeftText, layoutParams);
        addView(mCenterLayout);
        addView(mRightLayout, layoutParams);
        addView(mDividerView, new LayoutParams(LayoutParams.MATCH_PARENT, mDivideHeight));
    }


    /**
     * 设置状态栏高度
     *
     * @param height 高度
     * @return
     */
    public TitleBar setHeight(int height) {
        mBarHeight = height;
        setMeasuredDimension(getMeasuredWidth(), mBarHeight);
        return this;
    }

    public TitleBar setLeftImageResource(int resId) {
        if (mLeftText != null) {
            mLeftText.setCompoundDrawablesRelativeWithIntrinsicBounds(resId, 0, 0, 0);
        }
        return this;
    }

    /**
     * 设置左侧图标
     *
     * @param leftImageDrawable 图标
     * @return
     */
    public TitleBar setLeftImageDrawable(Drawable leftImageDrawable) {
        mLeftImageResource = leftImageDrawable;
        if (mLeftText != null) {
            mLeftText.setCompoundDrawablesRelativeWithIntrinsicBounds(mLeftImageResource, null, null, null);
        }
        return this;
    }

    /**
     * 设置左侧文字是否加粗
     *
     * @param isBold 是否加粗
     * @return
     */
    public TitleBar setLeftTextBold(boolean isBold) {
        if (mLeftText != null) {
            mLeftText.getPaint().setFakeBoldText(isBold);
        }
        return this;
    }

    /**
     * 设置中间文字是否加粗
     *
     * @param isBold 是否加粗
     * @return
     */
    public TitleBar setCenterTextBold(boolean isBold) {
        if (mCenterText != null) {
            mCenterText.getPaint().setFakeBoldText(isBold);
        }
        return this;
    }

    /**
     * 设置左侧图标
     *
     * @param resId 图片资源
     * @return
     */
    public TitleBar setBackImageResource(int resId) {
        if (resId != 0) {
            mLeftImageResource = ContextCompat.getDrawable(getContext(), resId);
            if (mLeftImageResource != null) {
                mLeftImageResource.setBounds(0, 0, YUIHelper.INSTANCE.dip2px(12f), YUIHelper.INSTANCE.dip2px(22f));
            }
            mLeftText.setCompoundDrawables(mLeftImageResource, null, null, null);
        } else {
            mLeftImageResource = null;
            mLeftText.setCompoundDrawables(null, null, null, null);
        }
        return this;
    }

    /**
     * 设置左侧点击事件
     *
     * @param l 监听器
     * @return
     */
    public TitleBar setLeftClickListener(OnClickListener l) {
        mLeftText.setOnClickListener(l);
        return this;
    }

    /**
     * 设置左侧文字
     *
     * @param title 标题
     * @return
     */
    public TitleBar setLeftText(CharSequence title) {
        mLeftText.setText(title);
        return this;
    }

    /**
     * 设置左侧文字
     *
     * @param resId 标题资源
     * @return
     */
    public TitleBar setLeftText(int resId) {
        mLeftText.setText(resId);
        return this;
    }

    /**
     * 设置左侧文字大小
     *
     * @param size 文字大小
     * @return
     */
    public TitleBar setLeftTextSize(float size) {
        mLeftText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        return this;
    }

    /**
     * 设置左侧文字的最大长度
     *
     * @param maxEms 最大长度
     * @return
     */
    public TitleBar setLeftTextMaxEms(int maxEms) {
        mLeftText.setMaxEms(maxEms);
        return this;
    }

    /**
     * 设置左侧文字的最大宽度
     *
     * @param maxPixels 最大宽度
     * @return
     */
    public TitleBar setLeftTextMaxWidth(int maxPixels) {
        mLeftText.setMaxWidth(maxPixels);
        return this;
    }

    /**
     * 设置左侧文字长度超出的处理
     *
     * @param where
     * @return
     */
    public TitleBar setLeftTextEllipsize(TextUtils.TruncateAt where) {
        mLeftText.setEllipsize(where);
        return this;
    }

    /**
     * 左侧文字的Padding
     *
     * @param paddingStart
     * @param paddingEnd
     * @return
     */
    public TitleBar setLeftTextPadding(int paddingStart, int paddingEnd) {
        mLeftText.setPaddingRelative(paddingStart, 0, paddingEnd, 0);
        return this;
    }

    /**
     * 左侧文字的颜色
     *
     * @param color
     * @return
     */
    public TitleBar setLeftTextColor(int color) {
        mLeftText.setTextColor(color);
        return this;
    }

    /**
     * 设置左侧文字是否可显示
     *
     * @param visible
     * @return
     */
    public TitleBar setLeftVisible(boolean visible) {
        mLeftText.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 禁用左侧控件
     *
     * @return
     */
    public TitleBar disableLeftView() {
        setBackImageResource(0);
        setLeftTextPadding(mActionPadding, 0);
        setLeftVisible(false);
        return this;
    }


    /**
     * 设置标题文字
     *
     * @param title
     * @return
     */
    public TitleBar setTitle(CharSequence title) {
        int index = title.toString().indexOf("\n");
        if (index > 0) {
            setTitle(title.subSequence(0, index), title.subSequence(index + 1,
                    title.length()), LinearLayout.VERTICAL);
        } else {
            index = title.toString().indexOf("\t");
            if (index > 0) {
                setTitle(title.subSequence(0, index), "  "
                        + title.subSequence(index + 1, title.length()), LinearLayout.HORIZONTAL);
            } else {
                mCenterText.setText(title);
                mSubTitleText.setVisibility(View.GONE);
            }
        }
        return this;
    }

    /**
     * 设置标题和副标题的文字
     *
     * @param title       标题
     * @param subTitle    副标题
     * @param orientation 对齐方式
     * @return
     */
    public TitleBar setTitle(CharSequence title, CharSequence subTitle, int orientation) {
        mCenterLayout.setOrientation(orientation);
        mCenterText.setText(title);

        mSubTitleText.setText(subTitle);
        mSubTitleText.setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public TitleBar setCenterTitle(CharSequence title) {
        mCenterText.setText(title);
        return this;
    }

    /**
     * 设置标题和副标题的文字
     *
     * @param subTitle 副标题
     * @return
     */
    public TitleBar setSubTitle(CharSequence subTitle) {
        mSubTitleText.setText(subTitle);
        mSubTitleText.setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * 设置中间内容的对齐方式
     *
     * @param gravity
     * @return
     */
    public TitleBar setCenterGravity(int gravity) {
        mCenterLayout.setGravity(gravity);
        mCenterText.setGravity(gravity);
        mSubTitleText.setGravity(gravity);
        return this;
    }

    /**
     * 设置中心点击
     *
     * @param l
     * @return
     */
    public TitleBar setCenterClickListener(OnClickListener l) {
        mCenterLayout.setOnClickListener(l);
        return this;
    }

    /**
     * 设置标题文字
     *
     * @param resId
     * @return
     */
    public TitleBar setTitle(int resId) {
        setTitle(getResources().getString(resId));
        return this;
    }

    /**
     * 设置标题文字颜色
     *
     * @param resId
     * @return
     */
    public TitleBar setTitleColor(int resId) {
        mCenterText.setTextColor(resId);
        return this;
    }

    /**
     * 设置标题文字大小
     *
     * @param size
     * @return
     */
    public TitleBar setTitleSize(float size) {
        mCenterText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        return this;
    }

    /**
     * 设置标题背景
     *
     * @param resId
     * @return
     */
    public TitleBar setTitleBackground(int resId) {
        mCenterText.setBackgroundResource(resId);
        return this;
    }

    /**
     * 设置副标题颜色
     *
     * @param resId
     * @return
     */
    public TitleBar setSubTitleColor(int resId) {
        mSubTitleText.setTextColor(resId);
        return this;
    }

    /**
     * 设置副标题字体大小
     *
     * @param size
     * @return
     */
    public TitleBar setSubTitleSize(float size) {
        mSubTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        return this;
    }

    /**
     * 自定义标题
     *
     * @param titleView
     * @return
     */
    public TitleBar setCustomTitle(View titleView) {
        if (titleView == null) {
            mCenterText.setVisibility(View.VISIBLE);
            if (mCustomCenterView != null) {
                mCenterLayout.removeView(mCustomCenterView);
            }

        } else {
            if (mCustomCenterView != null) {
                mCenterLayout.removeView(mCustomCenterView);
            }
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            mCustomCenterView = titleView;
            mCenterLayout.addView(titleView, layoutParams);
            mCenterText.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 设置分割线
     *
     * @param drawable
     * @return
     */
    public TitleBar setDivider(Drawable drawable) {
        mDividerView.setBackground(drawable);
        return this;
    }

    /**
     * 设置分割线颜色
     *
     * @param color
     * @return
     */
    public TitleBar setDividerColor(int color) {
        mDividerView.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置分割线高度
     *
     * @param dividerHeight
     * @return
     */
    public TitleBar setDividerHeight(int dividerHeight) {
        mDividerView.getLayoutParams().height = dividerHeight;
        return this;
    }

    /**
     * 设置标题栏右侧文字的颜色
     *
     * @param colorResId
     * @return
     */
    public TitleBar setActionTextColor(int colorResId) {
        mActionTextColor = colorResId;
        return this;
    }

    /**
     * 中间标题点击事件
     *
     * @param listener
     * @return
     */
    public TitleBar setOnTitleClickListener(OnClickListener listener) {
        mCenterText.setOnClickListener(listener);
        return this;
    }

    @Override
    public void onClick(View view) {
        final Object tag = view.getTag();
        if (tag instanceof Action) {
            final Action action = (Action) tag;
            action.performAction(view);
        }
    }

    /**
     * 添加点击事件
     *
     * @param actionList
     * @return
     */
    public TitleBar addActions(ActionList actionList) {
        int actions = actionList.size();
        for (int i = 0; i < actions; i++) {
            addAction(actionList.get(i));
        }
        return this;
    }

    /**
     * Adds a new {@link Action}.
     *
     * @param action the action to add
     */
    public View addAction(Action action) {
        final int index = mRightLayout.getChildCount();
        return addAction(action, index);
    }

    /**
     * Adds a new {@link Action} at the specified index.
     *
     * @param action the action to add
     * @param index  the position at which to add the action
     */
    public View addAction(Action action, int index) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        View view = inflateAction(action);
        mRightLayout.addView(view, index, params);
        return view;
    }

    /**
     * Removes all action views from this action bar
     */
    public void removeAllActions() {
        mRightLayout.removeAllViews();
    }

    /**
     * Remove a action from the action bar.
     *
     * @param index position of action to remove
     */
    public void removeActionAt(int index) {
        mRightLayout.removeViewAt(index);
    }

    /**
     * Remove a action from the action bar.
     *
     * @param action The action to remove
     */
    public void removeAction(Action action) {
        int childCount = mRightLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = mRightLayout.getChildAt(i);
            if (view != null) {
                final Object tag = view.getTag();
                if (tag instanceof Action && tag.equals(action)) {
                    mRightLayout.removeView(view);
                }
            }
        }
    }

    /**
     * Returns the number of actions currently registered with the action bar.
     *
     * @return action count
     */
    public int getActionCount() {
        return mRightLayout.getChildCount();
    }

    /**
     * Inflates a {@link View} with the given {@link Action}.
     *
     * @param action the action to inflate
     * @return a view
     */
    protected View inflateAction(Action action) {
        View view = null;
        if (action.getView() != null) {
            FrameLayout frameLayout = new FrameLayout(getContext());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_VERTICAL;
            frameLayout.addView(action.getView(), params);
            view = frameLayout;
        } else if (TextUtils.isEmpty(action.getText())) {
            ImageView img = new YUIAlphaImageView(getContext());
            img.setImageResource(action.getDrawable());
            view = img;
        } else {
            TextView text = new YUIAlphaTextView(getContext());
            text.setGravity(Gravity.CENTER);
            text.setText(action.getText());
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActionTextSize);
            if (mActionTextColor != 0) {
                text.setTextColor(mActionTextColor);
            }
            view = text;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            view.setPaddingRelative(action.leftPadding() != -1 ?
                            action.leftPadding() :
                            mActionPadding, 0,
                    action.rightPadding() != -1 ?
                            action.rightPadding() :
                            mActionPadding, 0);
        }
        view.setTag(action);
        view.setOnClickListener(this);
        return view;
    }

    public View getViewByAction(Action action) {
        return findViewWithTag(action);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height;
        if (heightMode != MeasureSpec.EXACTLY) {
            height = mBarHeight;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mBarHeight, MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.getSize(heightMeasureSpec);
        }

        measureChild(mLeftText, widthMeasureSpec, heightMeasureSpec);
        measureChild(mRightLayout, widthMeasureSpec, heightMeasureSpec);
        if (mLeftText.getMeasuredWidth() > mRightLayout.getMeasuredWidth()) {
            mCenterLayout.measure(
                    MeasureSpec.makeMeasureSpec(mScreenWidth -
                            2 * mLeftText.getMeasuredWidth(), MeasureSpec.EXACTLY), heightMeasureSpec);
        } else {
            mCenterLayout.measure(
                    MeasureSpec.makeMeasureSpec(mScreenWidth -
                            2 * mRightLayout.getMeasuredWidth(), MeasureSpec.EXACTLY), heightMeasureSpec);
        }
        measureChild(mDividerView, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (isRtl()) {
            layoutChildView(mRightLayout, mCenterLayout, mLeftText);
        } else {
            layoutChildView(mLeftText, mCenterLayout, mRightLayout);
        }
        mDividerView.layout(0, getMeasuredHeight() - mDividerView.getMeasuredHeight(),
                getMeasuredWidth(), getMeasuredHeight());
    }

    private void layoutChildView(View leftView, View centerView, View rightView) {
        leftView.layout(0, 0, leftView.getMeasuredWidth(), leftView.getMeasuredHeight());
        rightView.layout(mScreenWidth - rightView.getMeasuredWidth(), 0,
                mScreenWidth, rightView.getMeasuredHeight());
        if (mCenterGravity == CENTER_LEFT) {
            centerView.layout(leftView.getMeasuredWidth(), 0,
                    mScreenWidth - leftView.getMeasuredWidth(), getMeasuredHeight());
        } else if (mCenterGravity == CENTER_RIGHT) {
            centerView.layout(rightView.getMeasuredWidth(), 0,
                    mScreenWidth - rightView.getMeasuredWidth(), getMeasuredHeight());
        } else {
            if (leftView.getMeasuredWidth() > rightView.getMeasuredWidth()) {
                centerView.layout(leftView.getMeasuredWidth(), 0,
                        mScreenWidth - leftView.getMeasuredWidth(), getMeasuredHeight());
            } else {
                centerView.layout(rightView.getMeasuredWidth(), 0,
                        mScreenWidth - rightView.getMeasuredWidth(), getMeasuredHeight());
            }
        }
    }


    /**
     * 事件动作集合
     */
    public static class ActionList extends LinkedList<Action> {

    }

    /**
     * 事件动作
     */
    public interface Action {
        /**
         * @return 显示文字
         */
        String getText();

        /**
         * @return 显示图标
         */
        int getDrawable();

        /**
         * 点击动作
         *
         * @param view
         */
        void performAction(View view);

        /**
         * @return 左边间距
         */
        int leftPadding();

        /**
         * @return 右边间距
         */
        int rightPadding();

        /**
         * @return view
         */
        View getView();
    }

    /**
     * 图片动作
     */
    public static abstract class ImageAction implements Action {

        private int mDrawableId;

        public ImageAction(@DrawableRes int drawableId) {
            mDrawableId = drawableId;
        }

        @Override
        public int getDrawable() {
            return mDrawableId;
        }

        @Override
        public String getText() {
            return null;
        }

        @Override
        public int leftPadding() {
            return -1;
        }

        @Override
        public int rightPadding() {
            return 0;
        }

        @Override
        public View getView() {
            return null;
        }
    }

    /**
     * 文字动作
     */
    public static abstract class TextAction implements Action {

        final private String mText;

        public TextAction(String text) {
            mText = text;
        }

        @Override
        public int getDrawable() {
            return 0;
        }

        @Override
        public String getText() {
            return mText;
        }

        @Override
        public int leftPadding() {
            return -1;
        }

        @Override
        public int rightPadding() {
            return 0;
        }

        @Override
        public View getView() {
            return null;
        }
    }

    /**
     * 文字动作
     */
    public static abstract class ViewAction implements Action {

        final private View mView;

        public ViewAction(View view) {
            mView = view;
        }

        @Override
        public int getDrawable() {
            return 0;
        }

        @Override
        public String getText() {
            return "";
        }

        @Override
        public int leftPadding() {
            return -1;
        }

        @Override
        public int rightPadding() {
            return 0;
        }

        public View getView() {
            return mView;
        }
    }


    private boolean isRtl() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return getLayoutDirection() == LAYOUT_DIRECTION_RTL;
        } else {
            return false;
        }
    }

    public YUIAlphaTextView getLeftText() {
        return mLeftText;
    }

    public TextView getSubTitleText() {
        return mSubTitleText;
    }

    public TextView getCenterText() {
        return mCenterText;
    }

    public View getDividerView() {
        return mDividerView;
    }
}
