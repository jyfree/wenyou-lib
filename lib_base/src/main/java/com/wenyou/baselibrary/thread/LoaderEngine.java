package com.wenyou.baselibrary.thread;

import java.util.concurrent.ExecutorService;

/**
 * @description 加载器
 * @date: 2021/12/16 13:36
 * @author: jy
 */
public class LoaderEngine {

    private ExecutorService taskExecutor;


    public void init(LoaderConfiguration configuration) {
        taskExecutor = configuration.taskExecutor;

    }

    public ExecutorService getTaskExecutor() {
        return taskExecutor;
    }


    public void submit(Thread task) {
        taskExecutor.execute(task);
    }

    public void submit(Runnable task) {
        taskExecutor.execute(task);
    }


}
