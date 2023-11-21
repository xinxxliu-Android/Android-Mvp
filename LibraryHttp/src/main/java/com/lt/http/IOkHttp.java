package com.lt.http;

import androidx.annotation.NonNull;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public interface IOkHttp {

   @NonNull OkHttpClient client();

   @NonNull OkHttpClient.Builder newBuilder();

   @NonNull OkHttpClient addInterceptor(Interceptor interceptor);
}
