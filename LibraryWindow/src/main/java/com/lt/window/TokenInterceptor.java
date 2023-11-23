package com.lt.window;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.lt.entity.HttpEntity;
import com.lt.log.LogUtils;
import com.lt.utils.MGson;
import com.lt.utils.RxSchedulers;

import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.rxjava3.core.Flowable;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 本地区 网络请求拦截器
 * 用以 登录被挤
 */
final class TokenInterceptor implements Interceptor {

    TokenErrorWindow tokenErrorWindow;

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        //body.string()用完后 response对象会失效，所以需要复制一份response对象
        ResponseBody b = response.body();
        if (b == null) {
            LogUtils.e(request.url() + "");
        }
        ResponseBody body = response.peekBody(5000 * 5000);
        String string = body.string();
        try {
            HttpEntity httpEntity = new HttpEntity();
            JSONObject jsonObject = new JSONObject(string);
            httpEntity.code = jsonObject.getInt("code");
            httpEntity.msg = jsonObject.has("msg") ? jsonObject.getString("msg") : "";
            Object data = jsonObject.has("data") ? jsonObject.get("data"):null;
            httpEntity.data = data == null ? "{}" : data.toString();
            //账号被挤
            if (httpEntity.code == 401) {
                if (tokenErrorWindow == null) {
                    tokenErrorWindow = new TokenErrorWindow(ActivityUtils.getTopActivity());
                }
                Flowable.just(1)
                        .observeOn(RxSchedulers.main())
                        .doOnNext((it) -> {
                            if (!tokenErrorWindow.isShowing())
                                tokenErrorWindow.show("异常提醒", "当前账号已在其他设备登录");

                        })
                        .subscribe();
                //throw new HttpException(CodeConfig.NET_ERROR_UNKNOWN,"当前账号已在其他设备登录");
            }
        } catch (Exception e) {
            LogUtils.e("JSON",
                    "url=" + request.url() +
                            "\n" + "bodyString=" + string
            );
        }

        return response;
    }
}
