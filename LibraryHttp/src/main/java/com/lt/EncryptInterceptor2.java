//package com.lt;
//
//import androidx.annotation.NonNull;
//
//import com.blankj.utilcode.util.StringUtils;
//import com.lt.config.UrlConfig;
//import com.lt.encrypt.SMUtils;
//import com.lt.http.HttpException;
//import com.lt.log.LogUtils;
//import com.lt.utils.MGson;
//
//import java.io.IOException;
//
//import okhttp3.FormBody;
//import okhttp3.Interceptor;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//import okio.Buffer;
//
//final class EncryptInterceptor2 implements Interceptor {
//    @NonNull
//    @Override
//    public Response intercept(@NonNull Chain chain) throws IOException {
//        Request request = chain.request();
//        String path = request.url().url().getPath();
//        //上传图片接口 不加密
//        if (path.contains(UrlConfig.UPLOAD_PIC) || UrlConfig.UPLOAD_PIC.contains(path))
//            return chain.proceed(request);
//        Buffer buffer = new Buffer();
//        RequestBody body = request.body();
//        if (body == null)
//            return chain.proceed(request);
//        body.writeTo(buffer);
//        byte[] bytes = buffer.readByteArray(body.contentLength());
//        buffer.clear();
//        buffer.close();
//        //对byte数组加密
//        String s = null;
//        s = SMUtils.SM2Encrypt(bytes)+","+SMUtils.SM3Encrypt(new String(bytes));
//        LogUtils.i("okhttp.OkHttpClient：headers：" + MGson.toJson(new String(bytes)));
//        LogUtils.d("okhttp 请求体加密-->" + s);
//        if (StringUtils.isTrimEmpty(s))
//            throw new HttpException(-1, "加密失败，请检查");
//        body = new FormBody.Builder().add("param", s).build();
//
//        Request build = request.newBuilder().post(body).build();
//        return chain.proceed(build);
//    }
//}
