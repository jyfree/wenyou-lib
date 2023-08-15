
package com.wenyou.uidemo.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.Collection;

/**
 * @description 通用的RecyclerView适配器
 * @date: 2021/3/19 14:35
 * @author: jy
 */
public abstract class BaseRecyclerAdapter<T> extends XRecyclerAdapter<T, RecyclerViewHolder> {

    public BaseRecyclerAdapter() {
        super();
    }

    public BaseRecyclerAdapter(Collection<T> list) {
        super(list);
    }

    public BaseRecyclerAdapter(T[] data) {
        super(data);
    }

    /**
     * 适配的布局
     *
     * @param viewType
     * @return
     */
    protected abstract int getItemLayoutId(int viewType);

    @NonNull
    @Override
    protected RecyclerViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(inflateView(parent, getItemLayoutId(viewType)));
    }

}
