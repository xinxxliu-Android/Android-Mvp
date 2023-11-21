package com.lt.http;


import com.lt.entity.HttpEntity;

import org.reactivestreams.Publisher;

import java.util.concurrent.Future;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Function;

/**
 * 自动转换 将 {@link HttpEntity<T>} 转换输出为{@link T}对象
 * @param <T>   内部解析对象
 */
//public final class EntityFunction<T> implements Function<HttpEntity<T>, Publisher<T>> {
//
//    @Override
//    public Publisher<T> apply(HttpEntity<T> tHttpEntity) throws Exception {
//        if (tHttpEntity.isSuccess())
//            return Flowable.just(tHttpEntity.data);
//        else
//            throw new HttpException(tHttpEntity.code, tHttpEntity.getMessage());
//    }
//}
