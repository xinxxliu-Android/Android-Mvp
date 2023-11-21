package com.lt;

import androidx.annotation.NonNull;

import com.lt.config.BaseUrlConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetWorkerInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        HttpUrl oldHttpurl = request.url();
        HttpUrl newBaseUrl = HttpUrl.parse(BaseUrlConfig.BASE_URL);
        HttpUrl newHttpUrl = oldHttpurl.newBuilder()
                .scheme(newBaseUrl.scheme())
                .host(newBaseUrl.host())
                .port(newBaseUrl.port())
                .build();
        return chain.proceed(builder.url(newHttpUrl).build());
    }
}
