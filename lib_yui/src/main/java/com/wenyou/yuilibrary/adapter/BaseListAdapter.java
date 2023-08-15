package com.wenyou.yuilibrary.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description 集合列表适配器
 * @date: 2021/2/22 14:13
 * @author: jy
 */
public abstract class BaseListAdapter<T, H> extends BaseAdapter {

    private final List<T> mData = new ArrayList<>();
    private Context mContext;
    /**
     * 当前点击的条目
     */
    protected int mSelectPosition = -1;

    public BaseListAdapter(Context context) {
        mContext = context;
    }

    public BaseListAdapter(Context context, List<T> data) {
        mContext = context;
        setData(data);
    }

    public BaseListAdapter(Context context, T[] data) {
        mContext = context;
        setData(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        H holder;
        if (convertView == null) {
            convertView = View.inflate(getContext(), getLayoutId(), null);
            holder = newViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (H) convertView.getTag();
        }

        T item = getItem(position);
        if (item != null) {
            convert(holder, item, position);
        }
        return convertView;
    }

    /**
     * 创建ViewHolder
     *
     * @param convertView
     * @return
     */
    protected abstract H newViewHolder(View convertView);

    /**
     * 获取适配的布局ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 转换布局
     *
     * @param holder
     * @param item
     * @param position
     */
    protected abstract void convert(H holder, T item, int position);


    public void setData(List<T> data) {
        if (data != null) {
            mData.clear();
            mData.addAll(data);
            mSelectPosition = -1;
            notifyDataSetChanged();
        }
    }

    public void setData(T[] data) {
        if (data != null && data.length > 0) {
            setData(Arrays.asList(data));
        }
    }

    public void addData(List<T> data) {
        if (data != null && !data.isEmpty()) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void addData(T[] data) {
        addData(Arrays.asList(data));
    }

    public void addData(T data) {
        if (data != null) {
            mData.add(data);
            notifyDataSetChanged();
        }
    }

    public void removeElement(T element) {
        if (mData.contains(element)) {
            mData.remove(element);
            notifyDataSetChanged();
        }
    }

    public void removeElement(int position) {
        if (mData.size() > position) {
            mData.remove(position);
            notifyDataSetChanged();
        }
    }

    public void removeElements(List<T> elements) {
        if (elements != null && elements.size() > 0 && mData.size() >= elements.size()) {
            for (T element : elements) {
                mData.remove(element);
            }
            notifyDataSetChanged();
        }
    }

    public void removeElements(T[] elements) {
        if (elements != null && elements.length > 0) {
            removeElements(Arrays.asList(elements));
        }
    }

    public void updateElement(T element, int position) {
        if (checkPosition(position)) {
            mData.set(position, element);
            notifyDataSetChanged();
        }
    }

    public void addElement(T element) {
        if (element != null) {
            mData.add(element);
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        mData.clear();
        mSelectPosition = -1;
        notifyDataSetChanged();
    }

    public void clearNotNotify() {
        mData.clear();
        mSelectPosition = -1;
    }

    protected void visible(boolean flag, View view) {
        if (flag) {
            view.setVisibility(View.VISIBLE);
        }
    }

    protected void gone(boolean flag, View view) {
        if (flag) {
            view.setVisibility(View.GONE);
        }
    }

    protected void inVisible(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    protected Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(mContext, resId);
    }

    protected String getString(int resId) {
        return mContext.getResources().getString(resId);
    }

    protected int getColor(int resId) {
        return ContextCompat.getColor(mContext, resId);
    }

    public List<T> getItems() {
        return mData;
    }

    public int getSize() {
        return mData.size();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return checkPosition(position) ? mData.get(position) : null;
    }

    private boolean checkPosition(int position) {
        return position >= 0 && position <= mData.size() - 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public Context getContext() {
        return mContext;
    }

    /**
     * @return 当前列表的选中项
     */
    public int getSelectPosition() {
        return mSelectPosition;
    }

    /**
     * 设置当前列表的选中项
     *
     * @param selectPosition
     * @return
     */
    public BaseListAdapter setSelectPosition(int selectPosition) {
        mSelectPosition = selectPosition;
        notifyDataSetChanged();
        return this;
    }

    /**
     * 获取当前列表选中项
     *
     * @return 当前列表选中项
     */
    public T getSelectItem() {
        return getItem(mSelectPosition);
    }

}
