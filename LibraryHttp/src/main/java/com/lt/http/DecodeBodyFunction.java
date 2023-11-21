package com.lt.http;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.JsonParseException;
import com.lt.config.CodeConfig;
import com.lt.encrypt.AESCrypt;
import com.lt.entity.HttpEntity;
import com.lt.log.LogUtils;
import com.lt.utils.MGson;

import org.json.JSONObject;
import org.reactivestreams.Publisher;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Function;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * 网络请求响应自动解析
 * 500+ 服务器错误
 * 400+ path错误 一般为编程错误
 * 200-300 正常响应
 * 300-399 请求转发等，也属于正常响应
 * <p>
 * 如果响应码正常，但是响应体为空，或者响应体未就绪，属于网络未知错误。 一般不会出现
 * <p>
 * 请求成功后，自动将response 通过 飞巡默认后台aes解密key进行解密并转换对象
 */
@SuppressWarnings("unchecked")
public abstract class DecodeBodyFunction<T> implements Function<Response<ResponseBody>, Publisher<T>> {
    private static final String TAG = "DecodeBodyFunction";

    @Override
    public Publisher<T> apply(Response<ResponseBody> response) throws Throwable {
        int code = response.code();
        String errMsg = null;
        int ecode = 0;
        if (code >= 500) {
            errMsg = "服务器崩溃啦，错误码->" + code;
            ecode = CodeConfig.NET_ERROR_SERVER;
        } else if (code >= 400) {
            errMsg = "网络请求失败啦！请检查请求PATH->" + code;
            ecode = CodeConfig.NET_ERROR_PATH;
        }
        ResponseBody body = response.body();
        String str = response.raw().toString();
        String url = str.substring(str.indexOf("url=") + 4, str.length() - 1);
        // 请求接口名
        String interfeceName = url.substring(url.indexOf("/", 8)+1);
        if (body == null || !response.isSuccessful())
            errMsg = "网络请求异常，响应体未就绪->" + interfeceName + "->" + code;
        if (errMsg != null) {
            ResponseBody errorBody = response.errorBody();
            String erst = null;
            if (errorBody != null) {
                erst = errorBody.string();
            }
            throw new HttpException(ecode, errMsg + "-" + erst);
        }
        String string = body.string();
        HttpEntity o;
        try {
            HttpEntity httpEntity = new HttpEntity();
            JSONObject jsonObject = new JSONObject(string);
            httpEntity.code = jsonObject.getInt("code");
            httpEntity.msg = jsonObject.has("msg") ? jsonObject.getString("msg"):"";
            Object data = jsonObject.has("data") ? jsonObject.get("data") : null;
            httpEntity.data = (data == null || StringUtils.equals("null",data.toString())) ? "" : data.toString();
            o = httpEntity;
        } catch (JsonParseException e) {
            //解析异常，字符串有问题啦
            LogUtils.e(interfeceName + ", ERROR_PATH->" + response.errorBody().string(), e);
            throw new HttpException(CodeConfig.NET_ERROR_PARSE, "响应串解析异常！！");
        }
        if (!o.isSuccess()) {
            LogUtils.e("服务器响应数据异常->" +interfeceName +"->" + o.getMsg());
            if (!StringUtils.isTrimEmpty(string))
                LogUtils.e("服务器响应体数据异常->" + string);
            throw new HttpException(o.code, o.getMsg() + "");
        }
        //数据校验 如果当前接口响应正常，但是data为null 则赋值data为{} 该格式已确定 且 定版于 api中
        String decrypt = o.isSuccess() && StringUtils.isTrimEmpty(o.data) ? "{}" : AESCrypt.decrypt(o.data);
        //解密失败
        if (StringUtils.isTrimEmpty(decrypt)) {
            LogUtils.e("解密后数据为空->"+interfeceName +"->" + o.data);
            throw new HttpException(CodeConfig.NET_ERROR_UNKNOWN, "解密后数据为空->" + o.data);
        }
        LogUtils.d("网络响应", "okhttp 接口-->" + interfeceName + "--> 响应数据为-->" + MGson.toJson(o));
        LogUtils.d("网络响应", "okhttp 接口-->" +interfeceName  + "--> 解析data-->" + decrypt);
        //反射获取当前对象 范型类型 type对象
        Type genericSuperclass = getClass().getGenericSuperclass();
        assert genericSuperclass != null;
        Class<T> t = (Class<T>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        T item;
        try {
            if (String.class.equals(t))
                item = (T) decrypt;
            else
                item = MGson.fromJson(decrypt, t);
        } catch (JsonParseException e) {
            //解析异常，字符串有问题啦
            throw new HttpException(CodeConfig.NET_ERROR_PARSE, "响应串解析异常->" + decrypt);
        }
        if (item == null)
            throw new HttpException(CodeConfig.NET_ERROR_PARSE, "响应串解析异常->" + decrypt);
        return Flowable.just(item);
    }
}
