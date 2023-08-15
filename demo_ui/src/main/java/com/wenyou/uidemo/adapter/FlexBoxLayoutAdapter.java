package com.wenyou.uidemo.adapter;

import android.util.SparseBooleanArray;

import androidx.annotation.NonNull;

import com.wenyou.uidemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @description google弹性布局 适配器
 * @date: 2021/3/19 14:36
 * @author: jy
 */
public class FlexBoxLayoutAdapter extends BaseRecyclerAdapter<String> {

    private boolean mIsMultiSelectMode;
    private boolean mCancelable;

    private SparseBooleanArray mSparseArray = new SparseBooleanArray();

    public FlexBoxLayoutAdapter(String[] data) {
        super(data);
    }

    public FlexBoxLayoutAdapter setIsMultiSelectMode(boolean isMultiSelectMode) {
        mIsMultiSelectMode = isMultiSelectMode;
        return this;
    }

    public FlexBoxLayoutAdapter setCancelable(boolean cancelable) {
        mCancelable = cancelable;
        return this;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.adapter_flexbox_layout_item_demo;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, String item) {
        holder.text(R.id.tv_tag_item, item);
        if (mIsMultiSelectMode) {
            holder.select(R.id.tv_tag_item, mSparseArray.get(position));
        } else {
            holder.select(R.id.tv_tag_item, getSelectPosition() == position);
        }
    }

    /**
     * 选择
     *
     * @param position
     * @return
     */
    public boolean select(int position) {
        return mIsMultiSelectMode ? multiSelect(position) : singleSelect(position);
    }

    /**
     * 多选
     *
     * @param positions
     */
    public void multiSelect(int... positions) {
        if (!mIsMultiSelectMode) {
            return;
        }
        for (int position : positions) {
            multiSelect(position);
        }
    }

    /**
     * 多选
     *
     * @param position
     */
    public boolean multiSelect(int position) {
        if (!mIsMultiSelectMode) {
            return false;
        }
        mSparseArray.append(position, !mSparseArray.get(position));
        notifyItemChanged(position);
        return true;
    }

    /**
     * 单选
     *
     * @param position
     */
    public boolean singleSelect(int position) {
        return singleSelect(position, mCancelable);
    }

    /**
     * 单选
     *
     * @param position
     * @param cancelable
     */
    public boolean singleSelect(int position, boolean cancelable) {
        if (position == getSelectPosition()) {
            if (cancelable) {
                setSelectPosition(-1);
                return true;
            }
        } else {
            setSelectPosition(position);
            return true;
        }
        return false;
    }


    /**
     * @return 获取选中的内容
     */
    public String getSelectContent() {
        String value = getSelectItem();
        if (value == null) {
            return "";
        }
        return value;
    }


    /**
     * @return 获取多选的内容
     */
    public List<String> getMultiContent() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            if (mSparseArray.get(i)) {
                list.add(getItem(i));
            }
        }
        return list;
    }
}
