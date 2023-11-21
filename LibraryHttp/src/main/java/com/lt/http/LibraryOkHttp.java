package com.lt.http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lt.CustomHttpLoggingInterceptor;
import com.lt.config.PluginConfig;
import com.lt.config.TimeConfig;
import com.lt.log.LogUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * ok网络请求
 */
public final class LibraryOkHttp implements IOkHttp {
    static IOkHttp iOkHttp;
    OkHttpClient client;
    OkEventListener listener;

    private LibraryOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //ok线程池长度
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(10);
        builder.dispatcher(dispatcher);
        ConnectionPool connectionPool = new ConnectionPool(10, 20 * 1000, TimeUnit.MILLISECONDS);
        builder.connectionPool(connectionPool);
        /**
         * 自定义日志拦截器
         */
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(s -> {
//            //在此拦截日志，进行打印或者输出到文件
//            LogUtils.i("网络请求", s);
//        });
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        CustomHttpLoggingInterceptor customHttpLoggingInterceptor = new CustomHttpLoggingInterceptor(HttpLoggingInterceptor.Level.HEADERS);
//        builder.addNetworkInterceptor(customHttpLoggingInterceptor); //log拦截器
        //添加自定义拦截器
        if (LibraryHttp.interceptors.size() > 0) {
            for (Interceptor interceptor : LibraryHttp.interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        builder.readTimeout(TimeConfig.HTTP_READ, TimeUnit.MILLISECONDS);//读取超时
        builder.writeTimeout(TimeConfig.HTTP_WRITE, TimeUnit.MILLISECONDS);//写入超时
        builder.connectTimeout(TimeConfig.HTTP_CONNECT, TimeUnit.MILLISECONDS);//连接超时
        builder.callTimeout(TimeConfig.HTTP_CALL, TimeUnit.MILLISECONDS);//通话超时
        if (PluginConfig.isHttps()) {
            builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getX509TrustManager());
            builder.hostnameVerifier(SSLSocketClient.getHostnameVerifier());
        }
        //okhttp所有事件
        listener = new OkEventListener();
        builder.eventListener(listener);//事件监听
        client = builder.build();//返回客户端
    }

    final static class OkEventListener extends EventListener {
        @Override
        public void cacheConditionalHit(@NonNull Call call, @NonNull Response cachedResponse) {
            super.cacheConditionalHit(call, cachedResponse);
        }

        @Override
        public void cacheHit(@NonNull Call call, @NonNull Response response) {
            super.cacheHit(call, response);
        }

        @Override
        public void cacheMiss(@NonNull Call call) {
            super.cacheMiss(call);
        }

        @Override
        public void callEnd(@NonNull Call call) {
            super.callEnd(call);
        }

        @Override
        public void callFailed(@NonNull Call call, @NonNull IOException ioe) {
            super.callFailed(call, ioe);
            LogUtils.e("接口错误", "callFailed-->call失败：" + call.request().url().toString() + "\r\n详细错误信息为：" + ioe.getMessage());
        }

        @Override
        public void callStart(@NonNull Call call) {
            super.callStart(call);
        }

        @Override
        public void canceled(@NonNull Call call) {
            super.canceled(call);
        }

        @Override
        public void connectEnd(@NonNull Call call, @NonNull InetSocketAddress inetSocketAddress, @NonNull Proxy proxy,
                               @Nullable Protocol protocol) {
            super.connectEnd(call, inetSocketAddress, proxy, protocol);
        }

        @Override
        public void connectFailed(@NonNull Call call, @NonNull InetSocketAddress inetSocketAddress, @NonNull Proxy proxy,
                                  @Nullable Protocol protocol, @NonNull IOException ioe) {
            super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
            LogUtils.e("call", "connectFailed-->连接失败：" + call.request().url() + "\r\n详细错误信息为：" + ioe.getMessage());
        }

        @Override
        public void connectStart(@NonNull Call call, @NonNull InetSocketAddress inetSocketAddress, @NonNull Proxy proxy) {
            super.connectStart(call, inetSocketAddress, proxy);
        }

        @Override
        public void connectionAcquired(@NonNull Call call, @NonNull Connection connection) {
            super.connectionAcquired(call, connection);
        }

        @Override
        public void connectionReleased(@NonNull Call call, @NonNull Connection connection) {
            super.connectionReleased(call, connection);
        }

        @Override
        public void dnsEnd(@NonNull Call call, @NonNull String domainName, @NonNull List<InetAddress> inetAddressList) {
            super.dnsEnd(call, domainName, inetAddressList);
        }

        @Override
        public void dnsStart(@NonNull Call call, @NonNull String domainName) {
            super.dnsStart(call, domainName);
        }

        @Override
        public void proxySelectEnd(@NonNull Call call, @NonNull HttpUrl url, @NonNull List<Proxy> proxies) {
            super.proxySelectEnd(call, url, proxies);
        }

        @Override
        public void proxySelectStart(@NonNull Call call, @NonNull HttpUrl url) {
            super.proxySelectStart(call, url);
        }

        @Override
        public void requestBodyEnd(@NonNull Call call, long byteCount) {
            super.requestBodyEnd(call, byteCount);
        }

        @Override
        public void requestBodyStart(@NonNull Call call) {
            super.requestBodyStart(call);
        }

        @Override
        public void requestFailed(@NonNull Call call, @NonNull IOException ioe) {
            super.requestFailed(call, ioe);
            LogUtils.e("requestFailed-->请求失败：" + call.request().url().toString() + "\r\n详细错误信息为：" + ioe.getMessage());
        }

        @Override
        public void requestHeadersEnd(@NonNull Call call, @NonNull Request request) {
            super.requestHeadersEnd(call, request);
        }

        @Override
        public void requestHeadersStart(@NonNull Call call) {
            super.requestHeadersStart(call);
        }

        @Override
        public void responseBodyEnd(@NonNull Call call, long byteCount) {
            super.responseBodyEnd(call, byteCount);
        }

        @Override
        public void responseBodyStart(@NonNull Call call) {
            super.responseBodyStart(call);
        }

        @Override
        public void responseFailed(@NonNull Call call, @NonNull IOException ioe) {
            super.responseFailed(call, ioe);
            LogUtils.e("接口错误", "responseFailed-->响应失败：" + call.request().url() + "\r\n详细错误信息为：" + ioe.getMessage());
        }

        @Override
        public void responseHeadersEnd(@NonNull Call call, @NonNull Response response) {
            super.responseHeadersEnd(call, response);
        }

        @Override
        public void responseHeadersStart(@NonNull Call call) {
            super.responseHeadersStart(call);
        }

        @Override
        public void satisfactionFailure(@NonNull Call call, @NonNull Response response) {
            super.satisfactionFailure(call, response);
        }

        @Override
        public void secureConnectEnd(@NonNull Call call, @Nullable Handshake handshake) {
            super.secureConnectEnd(call, handshake);
        }

        @Override
        public void secureConnectStart(@NonNull Call call) {
            super.secureConnectStart(call);
        }
    }

    public static IOkHttp create() {
        if (iOkHttp == null)
            synchronized (LibraryHttp.class) {
                if (iOkHttp == null)
                    iOkHttp = new LibraryOkHttp();
            }
        return iOkHttp;
    }

    @NonNull
    @Override
    public OkHttpClient addInterceptor(Interceptor interceptor) {
        boolean contains = false;
        //过滤 防止重复添加
        List<Interceptor> interceptors = client.interceptors();
        if (!interceptors.isEmpty())
            for (Interceptor it : interceptors) {
                if (it.getClass() == interceptor.getClass()) {
                    contains = true;
                }
            }
        interceptors = client.networkInterceptors();
        if (!interceptors.isEmpty())
            for (Interceptor it : interceptors) {
                if (it.getClass() == interceptor.getClass()) {
                    contains = true;
                }
            }
        if (contains)
            return this.client;
        return this.client = client().newBuilder().addInterceptor(interceptor).build();
    }

    @NonNull
    @Override
    public OkHttpClient.Builder newBuilder() {
        return client.newBuilder();
    }

    @Override
    public @NonNull
    OkHttpClient client() {
        return client;
    }
}
