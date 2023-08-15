package com.wenyou.yuilibrary.widget.tab;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import com.wenyou.yuilibrary.utils.YUILogUtils;


/**
 * @description
 * @date: 2021/12/16 13:44
 * @author: jy
 */
public class TabContainer extends HorizontalScrollView {
    private TabAdapter mApdater;
    private ViewPager mViewPager;
    private int mLastSelected = -1;
    private int mCacheSelected = -1;
    private OnItemListener mListener;
    private LinearLayout mContentView;
    private int mCenterX;

    public TabContainer(Context context) {
        this(context, null);
    }

    public TabContainer(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(21)
    public TabContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
    }

    public void setOnItemClickListener(OnItemListener listener) {
        this.mListener = listener;
    }

    private void initView() {
        mContentView = new LinearLayout(getContext());
        mContentView.setOrientation(LinearLayout.HORIZONTAL);
        mContentView.setGravity(Gravity.CENTER_VERTICAL);
        addView(mContentView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setAdpater(TabAdapter adpater) {
        //TabContainer崩溃，先try catch日后禁用这个控件，使用新的实现方式
        try {
            this.mApdater = adpater;
            mContentView.removeAllViews();
            if (mApdater == null) {
                return;
            }
            if (ViewCompat.isLaidOut(this)) {
                fillViews();
                firstTabSelect();
            } else {
                getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onGlobalLayout() {
                        fillViews();
                        firstTabSelect();
                        getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        } catch (Exception e) {
            YUILogUtils.INSTANCE.e("TabContainer", "TabContainer" + e.toString());
        }
    }

    private void firstTabSelect() {
        int selectIndex = 0;
        if (mCacheSelected != -1) {
            selectIndex = mCacheSelected;
            mCacheSelected = -1;
        }
        final int targetIndex = selectIndex;
        post(new Runnable() {
            @Override
            public void run() {
                selectTab(targetIndex, true, true);
            }
        });
    }

    private int getTargetScrollX(int newPos) {
        View view = mContentView.getChildAt(newPos);
        int scrollX = (view.getLeft() + view.getWidth() / 2) - mCenterX;
        return scrollX;
    }

    private void selectTab(int pos, boolean first, boolean notify) {
        if (mApdater == null) {
            return;
        }
        if (pos < 0 || pos > mApdater.getCount() - 1) {
            return;
        }
        if (pos > mContentView.getChildCount() - 1) {
            mCacheSelected = pos;
            return;
        }
        for (int index = 0; index < mContentView.getChildCount(); index++) {    //遍历设置选择false
            View lastView = mContentView.getChildAt(index);
            lastView.setSelected(false);
        }
        smoothScrollTo(getTargetScrollX(pos), getScrollY());
        mContentView.getChildAt(pos).setSelected(true);
        if (mListener != null && notify && pos != mLastSelected) {
            mListener.onTabSelect(pos, first);
        }
        if (mViewPager != null) {
            mViewPager.setCurrentItem(pos);
        }
        mLastSelected = pos;
    }

    public void setSelectTab(int pos) {
        selectTab(pos, false, true);
    }

    public int getLastSelectTab() {
        return mLastSelected;
    }

    public void setSelectTab(int pos, boolean notify) {
        selectTab(pos, false, notify);
    }

    private int getMarginStart(LinearLayout.LayoutParams params) {
        int marginStart = params.getMarginStart();
        if (marginStart <= 0) {
            marginStart = params.leftMargin;
        }
        return marginStart;
    }

    private int getMarginEnd(LinearLayout.LayoutParams params) {
        int marginEnd = params.getMarginEnd();
        if (marginEnd <= 0) {
            marginEnd = params.rightMargin;
        }
        return marginEnd;
    }

    private void fillViews() {
        int allocWidth = getWidth();
        TabItem item = null;
        int count = 0;
        for (int i = 0, len = mApdater.getCount(); i < len; i++) {
            item = mApdater.getItem(i);
            if (item.params != null) {

                allocWidth -= (getMarginStart(item.params) + getMarginEnd(item.params));
                if (item.weight == 1) {
                    count++;
                } else {
                    allocWidth -= item.params.width;
                }
            } else {
                count++;
            }
        }

        int width = count > 0 ? allocWidth / count : 0;
        int height = getHeight();
        for (int i = 0, len = mApdater.getCount(); i < len; i++) {
            View view = mApdater.onCreateView(this, i);
            item = mApdater.getItem(i);
            if (item.params != null) {
                if (item.weight == 1) {
                    item.params.width = width;
                }
                //this.addView(view, item.params);
                mContentView.addView(view, item.params);
            } else {
                mContentView.addView(view, new LinearLayout.LayoutParams(width, height));
            }
            final int tabIndex = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    TabItem selectedItem = mApdater.getItem(tabIndex);
                    if (mListener != null) {
                        mListener.onTabClick(tabIndex);
                    }
                    if (selectedItem != null && selectedItem.getCanSelected()) {
                        selectTab(tabIndex, false, true);
                    }
                }
            });
            mApdater.bindData(view, i);
        }
    }

    public interface OnItemListener {
        void onTabSelect(int pos, boolean first);

        void onTabClick(int pos);
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                setSelectTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

}
