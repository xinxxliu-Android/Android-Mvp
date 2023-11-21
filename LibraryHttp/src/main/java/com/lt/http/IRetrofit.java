package com.lt.http;

import android.provider.SyncStateContract;

import retrofit2.Retrofit;
import retrofit2.http.POST;

public interface IRetrofit {
    Retrofit retrofit();
    Retrofit retrofit(String url);
    <T> T build(Class<T> clazz);

    /**
     * 强制 刷新retrofit对象
     * @param url   x
     * @return      x
     */
    Retrofit rebuild(String url);
}
