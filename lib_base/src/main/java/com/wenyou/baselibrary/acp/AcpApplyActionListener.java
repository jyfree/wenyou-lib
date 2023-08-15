package com.wenyou.baselibrary.acp;

import android.content.Context;

public interface AcpApplyActionListener {

    String getRationalMessage();

    Context getContext();

    void onConfirm();

    void onCancel();
}
