package com.wenyou.baselibrary.thread;

import com.wenyou.baselibrary.utils.YLogUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description 线程池工厂
 * @date: 2021/12/16 13:37
 * @author: jy
 */
public class ThreadPoolFactory {

    public static ExecutorService createExecutor(int corePoolSize,
                                                 int maximumPoolSize,
                                                 int threadPriority,
                                                 long keepAliveTime,
                                                 TimeUnit unit,
                                                 BlockingQueue<Runnable> workQueue) {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                createThreadFactory(threadPriority, "uil-pool-"), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

                YLogUtils.INSTANCE.e("线程池已拒绝该任务", r);
            }
        });
    }


    public static ThreadFactory createThreadFactory(int threadPriority, String threadNamePrefix) {
        return new DefaultThreadFactory(threadPriority, threadNamePrefix);
    }

    /**
     * 核心线程数量和线程池最多持有线程数都是依据 CPU 核数来决定的，至少是minCount个
     *
     * @return
     */
    public static int calculateBestThreadCount(int minCount) {
        return Math.min(minCount, RuntimeCompat.availableProcessors());
    }

    private static class DefaultThreadFactory implements ThreadFactory {

        private static final AtomicInteger poolNumber = new AtomicInteger(1);

        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
        private final int threadPriority;

        DefaultThreadFactory(int threadPriority, String threadNamePrefix) {
            this.threadPriority = threadPriority;
            group = Thread.currentThread().getThreadGroup();
            namePrefix = threadNamePrefix + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) t.setDaemon(false);
            t.setPriority(threadPriority);
            return t;
        }
    }
}
