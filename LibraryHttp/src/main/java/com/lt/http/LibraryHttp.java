package com.lt.http;

import android.util.ArrayMap;

import com.lt.IServiceApi;
import com.lt.ServiceApiManager;
import com.lt.config.BaseUrlConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;

/**
 * 网络请求统筹管理器
 * <p>
 * 所有网络请求相关操作，由本类开始
 * 详细使用方式 参考{@link HttpExample}
 */
public final class LibraryHttp {
    static List<Interceptor> interceptors = new ArrayList<>(20);
    static Map<String, Object> headers = new ArrayMap<>();

    public static IRetrofit retrofitRequest(String url) {
        if (url == null)
            return LibraryRetrofit.create(BaseUrlConfig.BASE_URL);
        return LibraryRetrofit.create(url);
    }

    public static IRetrofit retrofitRequest() {
        return retrofitRequest(null);
    }

    /**
     * 对外暴漏 添加拦截器的函数
     * 必须在使用 {@link LibraryHttp#retrofitRequest()} 或者{@link LibraryHttp#retrofitRequest(String)}之前调用
     *
     * @param interceptor 自定义拦截器
     */
    public static void addInterceptor(Interceptor interceptor) {
        boolean contains = false;
        if (interceptors.isEmpty())
            interceptors.add(interceptor);
        else {
            for (Interceptor it : interceptors) {
                Class<? extends Interceptor> aClass = it.getClass();
                if (interceptor.getClass() == aClass) {
                    contains = true;
                    break;
                }
            }
            //防止多次重复添加拦截器
            if (!contains)
                interceptors.add(interceptor);
        }
    }

    /**
     * 对外报漏 添加请求头
     * 只有在 key 和 value都不为null时才会将map的值放进去
     *
     * @param gHeaders 自定义的请求头
     */
    public static void addHeaders(Map<String, Object> gHeaders) {
        headers.putAll(gHeaders);
    }
}
