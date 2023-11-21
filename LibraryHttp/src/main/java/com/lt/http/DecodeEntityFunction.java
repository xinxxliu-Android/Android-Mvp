package com.lt.http;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.JsonParseException;
import com.lt.config.CodeConfig;
import com.lt.encrypt.AESCrypt;
import com.lt.entity.HttpEntity;
import com.lt.log.LogUtils;
import com.lt.utils.MGson;

import org.reactivestreams.Publisher;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Function;

/**
 * 网络请求响应自动解析
 * <p>
 * 解析 {@link HttpEntity} 为指定对象
 */
@SuppressWarnings("unchecked")
public abstract class DecodeEntityFunction<T> implements Function<HttpEntity, Publisher<T>> {
    private static final String TAG = "DecodeEntityFunction";

    @Override
    public final Publisher<T> apply(HttpEntity httpEntity) throws Throwable {
        if (!httpEntity.isSuccess())
            throw new HttpException(httpEntity.code,httpEntity.getMsg());
        //飞巡 接口加密
        String decrypt = AESCrypt.decrypt(httpEntity.data);
        //解密失败
        if (StringUtils.isTrimEmpty(decrypt)) {
            LogUtils.e("解密后数据为空->" + httpEntity.data);
            throw new HttpException(CodeConfig.NET_ERROR_UNKNOWN, "解密后数据为空->" + httpEntity.data);
        }
        LogUtils.d("okhttp.OkHttpClient:REQUEST DECRYPT-->" + decrypt);
        //反射获取当前对象 范型类型 type对象
        Type genericSuperclass = getClass().getGenericSuperclass();
        assert genericSuperclass != null;
        Type t = (Type) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        T item;
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
