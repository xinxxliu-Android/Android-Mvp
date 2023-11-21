package com.lt.utils.thread;

import com.blankj.utilcode.util.ThreadUtils;
import com.lt.log.LogUtils;
import com.lt.utils.R;
import com.lt.utils.RxSchedulers;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;

import org.reactivestreams.Subscription;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;

/**
 * 项目 线程池操作 工具类。
 * 项目中 所有子线程任务，如果需要使用到线程，使用该类进行操作；
 * <p>
 * 20220814全局线程池 修改为使用rxjava线程池复用，不再另建线程池
 */
public class ThreadPoolUtils {

    private static ThreadPoolExecutor cachePool;
    private static ThreadPoolExecutor djiPool;

    private static int thread_index = 0;

    /**
     * 提交任务
     *
     * @param task 任务对象
     */
    public static void commitSingle(Runnable task) {
        Flowable.just(task)
                .map((it) -> {
                    it.run();
                    return 1;
                }).subscribeOn(RxSchedulers.request())
                .subscribe(new FlowableSubscriber<>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable t) {
                        CrashReport.postCatchedException(t);
                        LogUtils.e(t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 全项目 全局使用线程池
     * 包含：大疆rxjiava 大疆第二套rxjava 项目使用的rxjava
     *
     * @return  x
     */
    public synchronized static ExecutorService cachePool() {
        if (cachePool == null)
            cachePool = new ThreadPoolExecutor(5, 35,
                    30L, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>(), r -> {
                Thread thread = new Thread(r, "ProjectPoolService" + (thread_index++));
                thread.setDaemon(false);
                thread.setPriority(Thread.MAX_PRIORITY);
                thread.setUncaughtExceptionHandler((t, e) -> {
                    t.setUncaughtExceptionHandler(null);
                    LogUtils.e(e);
                    //bugly 异常上传
                    CrashReport.postCatchedException(e);
//                        BuglyLog.e("线程异常---","xxxxx",e);
                });
                return thread;
            });
        return cachePool;
    }

    /**
     * 大疆框架线程池
     *
     * @return
     */
    public synchronized static ExecutorService djiPool() {
        if (djiPool == null)
            djiPool = new ThreadPoolExecutor(5, 15,
                    60L, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>(), r -> {
                Thread thread = new Thread(r, "DJI_POOL_" + (thread_index++));
                thread.setDaemon(false);
                thread.setPriority(Thread.MAX_PRIORITY);
                thread.setUncaughtExceptionHandler((t, e) -> {
                    t.setUncaughtExceptionHandler(null);
                    LogUtils.e(e);
                    //bugly异常上传
                    CrashReport.postCatchedException(e);
//                    BuglyLog.e("线程异常---", "xxxxx", e);
                });
                return thread;
            });
        return djiPool;
    }
}
