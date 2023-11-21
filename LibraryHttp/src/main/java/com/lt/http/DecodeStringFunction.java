package com.lt.http;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.JsonParseException;
import com.lt.config.CodeConfig;
import com.lt.log.LogUtils;
import com.lt.utils.MGson;

import org.reactivestreams.Publisher;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Function;

/**
 * 网络请求响应自动解析
 * <p>
 * 解析 {@link String} 为指定对象
 */
@SuppressWarnings("unchecked")
public abstract class DecodeStringFunction<T> implements Function<String, Publisher<T>> {
    private static final String TAG = "DecodeEntityFunction";

    @Override
    public final Publisher<T> apply(String decrypt) throws Throwable {
//        //飞巡 接口加密
//        String decrypt = AESCrypt.decrypt(str);
//        //解密失败
//        if (StringUtils.isTrimEmpty(decrypt)) {
//            LogUtils.e("解密后数据为空->" + str);
//            throw new HttpException(CodeConfig.NET_ERROR_UNKNOWN, "解密后数据为空->" + str);
//        }
//        LogUtils.d("okhttp.OkHttpClient:REQUEST DECRYPT-->" + decrypt);
        //反射获取当前对象 范型类型 type对象
        Type genericSuperclass = getClass().getGenericSuperclass();
        assert genericSuperclass != null;
        Type t = (Type) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        T item;
        //修复 版本更新接口 响应数据异常问题
        if ("java.util.List<com.lt.server.response.AppUpdateResponse>".equals(t.toString()) && StringUtils.equals("{}",decrypt)) {
            item = (T) new ArrayList<com.lt.server.response.AppUpdateResponse>();
            return Flowable.just(item);
        }
        try {
            if (String.class.equals(t))
                item = (T) decrypt;
            else
                item = MGson.fromJson(decrypt, t);
        } catch (JsonParseException e) {
            //解析异常，字符串有问题啦
            LogUtils.e("JSON PARSE ERROR->" + decrypt, e);
            throw new HttpException(CodeConfig.NET_ERROR_PARSE, "响应串解析异常->" + decrypt);
        }
        if (item == null)
            throw new HttpException(CodeConfig.NET_ERROR_PARSE, "响应串解析异常->" + decrypt);
        return Flowable.just(item);
    }
}
