package com.wenyou.baselibrary.acp;

import java.util.List;

/**
 * @description
 * @date: 2021/12/16 11:28
 * @author: jy
 */
public interface AcpListener {
    /**
     * 同意
     */
    void onGranted();

    /**
     * 拒绝
     */
    void onDenied(List<String> permissions);
}
