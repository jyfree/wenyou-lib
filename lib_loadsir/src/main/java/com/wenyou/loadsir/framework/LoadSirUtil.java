package com.wenyou.loadsir.framework;

import android.os.Looper;

import com.wenyou.loadsir.framework.target.ITarget;

import java.util.List;

/**
 * @description
 * @date: 2021/12/22 15:34
 * @author: jy
 */
public class LoadSirUtil {

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static ITarget getTargetContext(Object target, List<ITarget> targetContextList) {
        for (ITarget targetContext : targetContextList) {
            if (targetContext.equals(target)) {
                return targetContext;
            }

        }
        throw new IllegalArgumentException("No TargetContext fit it");
    }
}
