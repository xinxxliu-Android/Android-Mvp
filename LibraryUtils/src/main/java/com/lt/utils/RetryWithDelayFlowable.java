package com.lt.utils;


import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Function;

/**
 * Flowable 重试机制
 *
 * @author Y
 */
public class RetryWithDelayFlowable implements Function<Flowable<Throwable>, Publisher<?>> {

    /**
     * 最大重试数量
     */
    private final int maxRetries;
    /**
     * 延时重试时间
     */
    private final int retryDelayMillis;
    /**
     * 当前重连次数
     */
    private int retryCount;

    public RetryWithDelayFlowable(final int maxRetries, final int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
        this.retryCount = 0;
    }

    @Override
    public Publisher<?> apply(Flowable<Throwable> throwableFlowable) throws Throwable {
        return throwableFlowable.flatMap(new Function<Throwable, Publisher<?>>() {
            @Override
            public Publisher<?> apply(Throwable throwable) throws Exception {
                if (++retryCount < maxRetries) {
                    return Flowable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                }
                return Flowable.error(throwable);
            }
        });
    }
}
