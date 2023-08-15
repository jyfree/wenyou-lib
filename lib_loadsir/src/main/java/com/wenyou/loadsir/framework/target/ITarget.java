package com.wenyou.loadsir.framework.target;


import com.wenyou.loadsir.framework.callback.Callback;
import com.wenyou.loadsir.framework.core.LoadLayout;

/**
 * @description
 * @date: 2021/12/22 15:34
 * @author: jy
 */
public interface ITarget {
    /**
     * @param target
     * @return v1.3.8
     */
    boolean equals(Object target);

    /**
     * 1.removeView 2.确定LP 3.addView
     *
     * @param target
     * @param onReloadListener
     * @return v1.3.8
     */
    LoadLayout replaceView(Object target, Callback.OnReloadListener onReloadListener);
}
