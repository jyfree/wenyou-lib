package com.wenyou.loadsir.framework.core;


import com.wenyou.loadsir.framework.callback.Callback;

/**
 * @description
 * @date: 2021/12/22 15:32
 * @author: jy
 */
public interface Convertor<T> {
    Class<? extends Callback> map(T t);
}
