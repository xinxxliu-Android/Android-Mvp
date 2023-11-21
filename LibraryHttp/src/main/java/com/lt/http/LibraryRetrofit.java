package com.lt.http;

import android.util.ArrayMap;

import com.google.gson.Gson;
import com.lt.config.BaseUrlConfig;

import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

final class LibraryRetrofit implements IRetrofit {
    static final Map<String, IRetrofit> retrofitMap = new ArrayMap<>();
    static final Map<String, Retrofit> rm = new ArrayMap<>();
    static IRetrofit retrofit;
    Retrofit re;
    private LibraryRetrofit(String url) {
        re = rm.get(url);
        if (re == null)
            re = createRetrofit(url);
    }

    private Retrofit createRetrofit(String url) {
        return new Retrofit.Builder().
                client(LibraryOkHttp.create().client())//客户
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())//添加适配器
                .addConverterFactory(GsonConverterFactory.create(new Gson()))//添加转换
                .baseUrl(url)//设置基本网址
                .build();
    }

    static IRetrofit create() {
        return create(BaseUrlConfig.BASE_URL);
    }

    @Override
    public <T> T build(Class<T> clazz) {
        return re.create(clazz);
    }

    @Override
    public Retrofit rebuild(String url) {
        retrofitMap.remove(url);
        return LibraryRetrofit.create(url).retrofit();
    }

    static IRetrofit create(String url) {
        if (retrofit == null || !retrofitMap.containsKey(url))
            synchronized (LibraryRetrofit.class) {
                retrofit = retrofitMap.get(url);
                if (retrofit == null)
                    retrofit = new LibraryRetrofit(url);
                retrofitMap.put(url, retrofit);
            }
        return retrofit;
    }

    @Override
    public Retrofit retrofit() {
        return retrofit(BaseUrlConfig.BASE_URL);
    }

    @Override
    public Retrofit retrofit(String url) {
        return re;
    }
}
