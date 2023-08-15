package com.wenyou.yuilibrary.widget.verify;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * @description 文本输入
 * @date: 2021/2/18 15:41
 * @author: jy
 */
public class PwdEditText extends AppCompatEditText {

    private InputIntercept mInputConnection;

    public PwdEditText(Context context) {
        super(context);
        init();
    }

    public PwdEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PwdEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mInputConnection = new InputIntercept(null, true);
    }

    /**
     * 当输入法和EditText建立连接的时候会通过这个方法返回一个InputConnection。
     * 我们需要代理这个方法的父类方法生成的InputConnection并返回我们自己的代理类。
     */
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        mInputConnection.setTarget(super.onCreateInputConnection(outAttrs));
        return mInputConnection;
    }

    public void setBackSpaceListener(InputIntercept.BackspaceListener backSpaceListener) {
        mInputConnection.setBackspaceListener(backSpaceListener);
    }
}
