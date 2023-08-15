package com.wenyou.web.apiModule;

/**
 * @description api方法接口
 * @date: 2021/12/16 10:41
 * @author: jy
 */
public interface IApiModule {

    interface IJSCallback {
        void invokeCallback(String param);

        void invokeCallback(String... param);
    }

    interface IApiMethod {
        String invoke(String param, IJSCallback callback);
    }

    String moduleName();

    String invoke(String method, String param, IJSCallback callback);

    void release();
}
