package com.lt.utils.thread;

import com.lt.log.LogUtils;
import com.lt.utils.RxSchedulers;
import org.reactivestreams.Subscription;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
/**
 * 线程池分发管理类
 * 老项目写法
 *
 * TODO 20220814 修改 全局去除线程池，全部使用rxjava写法调用线程池
 */
public class ThreadDispatcherUtils {
    public static void runOnStreamThread(Runnable run) {
        Flowable.just(run)
                .map((it)->{
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
                        LogUtils.d("执行推流成功 FPV");
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.e(t);
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d("执行推流完毕 FPV");
                    }
                });
    }
}
