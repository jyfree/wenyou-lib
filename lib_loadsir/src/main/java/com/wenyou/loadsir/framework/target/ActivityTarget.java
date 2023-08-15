package com.wenyou.loadsir.framework.target;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.wenyou.loadsir.framework.callback.Callback;
import com.wenyou.loadsir.framework.callback.SuccessCallback;
import com.wenyou.loadsir.framework.core.LoadLayout;


/**
 * @description
 * @date: 2021/12/22 15:34
 * @author: jy
 */
public class ActivityTarget implements ITarget {

    @Override
    public boolean equals(Object target) {
        return target instanceof Activity;
    }

    @Override
    public LoadLayout replaceView(Object target, Callback.OnReloadListener onReloadListener) {
        Activity activity = (Activity) target;
        ViewGroup contentParent = activity.findViewById(android.R.id.content);
        int childIndex = 0;
        View oldContent = contentParent.getChildAt(childIndex);
        contentParent.removeView(oldContent);

        ViewGroup.LayoutParams oldLayoutParams = oldContent.getLayoutParams();
        LoadLayout loadLayout = new LoadLayout(activity, onReloadListener);
        loadLayout.setupSuccessLayout(new SuccessCallback(oldContent, activity,
                onReloadListener));
        contentParent.addView(loadLayout, childIndex, oldLayoutParams);
        return loadLayout;
    }
}
