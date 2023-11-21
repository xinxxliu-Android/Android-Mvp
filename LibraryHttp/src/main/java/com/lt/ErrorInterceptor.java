package com.lt;

import androidx.annotation.NonNull;

import com.lt.config.UrlConfig;
import com.lt.encrypt.AESCrypt;
import com.lt.http.HttpException;
import com.lt.log.LogUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Create by wangpeng in 2023/7/20.
 * Describe: 错误码拦截器
 */
public class ErrorInterceptor implements Interceptor {
    private int getSignError = 0; //保存获取sign失败的次数
    private String getSignParam = ""; //保存getSign接口的参数
    private String SIGN = "";   //接口地址

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();
//        LogUtils.d("网络拦截器", "请求数据Url------" + url);
        RequestBody body = request.body();
        if (null == body) {
            return chain.proceed(request);
        }
        //判断URL是否是getSign接口，如果是则保存接口参数留着后用
        if (url.contains(UrlConfig.SIGN_REGISTER)) {
            SIGN = url;
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            String bodyString = buffer.readUtf8();
            buffer.clear();
            buffer.close();
            //从 bodyString 中去除字符param=
            String param = bodyString.substring(6, bodyString.length());
            LogUtils.d("网络拦截器", "请求数据body" + param);
            String decryptBody = AESCrypt.decrypt(param);
            getSignParam = param;
        }
        //取得响应体
        Response response = chain.proceed(request);
        //获取响应码
        int code = response.code();
        if (code == 448) {
            getSignError++;
            //如果两次失败，则抛出异常
            if (getSignError == 2) {
                //重置计数
                getSignError = 0;
                throw new HttpException(448, "网络错误");
            } else {
                /**
                 * 重新请求 getSign 接口
                 */
                body = new FormBody.Builder().add("param", getSignParam).build();
                Request newRequest = request.newBuilder().url(SIGN).post(body).build();
                return chain.proceed(newRequest);
            }
        }
        return response;
    }
}
