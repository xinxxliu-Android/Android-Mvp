package com.lt;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lt.config.BaseUrlConfig;
import com.lt.config.CustomUrlConfig;
import com.lt.config.UrlConfig;
import com.lt.log.LogUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 本地区 网络请求拦截器
 * 用以 当地地区 特殊数据处理
 * 如：统一path拼接 请求头添加 等
 */
public class LocalInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url();
        String path = url.encodedPath();
        LogUtils.d(path);
        //判断 当前是否是 自定义接口 如果是自定义接口 执行path转换
        String custom_type = filterPath(path, request.header("custom_type"));
        //两个字符串不相同 说明当前是自定义接口 从request中移除custom_type这个header
        if (!StringUtils.equalsIgnoreCase(path,custom_type))
            request = request.newBuilder().removeHeader("custom_type").build();
        path = custom_type;
        //请求授权接口 做地址转换
        if (path.contains(UrlConfig.SIGN_REGISTER) || UrlConfig.SIGN_REGISTER.contains(path)) {
            // 如果是外网请求，使用北京授权
            if (NetworkUtils.isAvailable()){
                HttpUrl parse = HttpUrl.parse(BaseUrlConfig.SIGN_BASE_URL);
                request = request.newBuilder().url(parse + "android/system/getSign").build();
                return chain.proceed(request);
            }
        }
        //将 path 进行拼接
        request = request.newBuilder().url(url.scheme() + "://" + url.host() + ":" + url.port() + "/" + BaseUrlConfig.URL_SERVER + path).build();
        return chain.proceed(request);
    }

    /**
     * 通过反射 注解 {@link  CustomUrlConfig.PathConfig#value()}获取到
     * 当前 customtype参数对应的path 执行path 转换
     * @param path  当前path 如果是自定义接口，那么就是 {@link CustomUrlConfig#PATH_BASE}
     * @param custom_type   当前请求头
     * @return  x
     */
    protected String filterPath(String path, String custom_type) {
        if (custom_type == null || custom_type.trim().isEmpty())
            return path;
        if (!StringUtils.equalsIgnoreCase("/"+CustomUrlConfig.PATH_BASE, path))
            return path;
        Field[] fields = CustomUrlConfig.ChongQing.class.getFields();
        List<Field> fs = new ArrayList<>();
        for (Field field : fields) {
            Class<?> type = field.getType();
            if (type != int.class)
                continue;
            fs.add(field);
        }
        for (Field f : fs) {
            f.setAccessible(true);
            try {
                Object o = f.get(null);
                if (o == null)
                    return path;
                int path_type = (int) o;
                if (path_type != Integer.parseInt(custom_type))
                    continue;
                //拿到该值 对应的字段 的注释
                CustomUrlConfig.PathConfig annotation = f.getAnnotation(CustomUrlConfig.PathConfig.class);
                assert annotation != null;
                String value = annotation.value();
                if (!StringUtils.isTrimEmpty(value))
                    return value;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
