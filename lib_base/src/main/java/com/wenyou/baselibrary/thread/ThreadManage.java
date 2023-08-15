package com.wenyou.baselibrary.thread;

/**
 * @description 全局线程管理
 * 使用前必须初始化，如：
 * ThreadManage.getInstance().getLoaderEngine().init(LoaderConfiguration.beginBuilder().build());
 * @date: 2021/12/16 13:36
 * @author: jy
 */
public class ThreadManage {
    private LoaderEngine loaderEngine;

    private ThreadManage() {
        loaderEngine = new LoaderEngine();
    }

    public static ThreadManage getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        private static final ThreadManage instance = new ThreadManage();
    }

    public LoaderEngine getLoaderEngine() {
        return loaderEngine;
    }
}
