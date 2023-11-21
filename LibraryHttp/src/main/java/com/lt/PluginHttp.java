package com.lt;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ReflectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lt.config.UserConfig;
import com.lt.http.LibraryHttp;
import com.lt.utils.thread.ThreadPoolUtils;

import java.util.Date;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;


public final class PluginHttp {
    public static Application mAppContext;

    public static void install(Application app) {
        LibraryHttp.addInterceptor(new OfflineCacheInterceptor());
        LibraryHttp.addInterceptor(chain -> {
            Request request = chain.request();
            request = request.newBuilder().addHeader("Date", new Date(System.currentTimeMillis()).toString()).build();
            //如果登录平台账号，则所有接口添加 token到请求头
            if (!StringUtils.isTrimEmpty(UserConfig.SignToken))
                request = request.newBuilder().addHeader("sign", UserConfig.SignToken).build();
            //如果登录平台账号，则所有接口添加 token到请求头
            if (!StringUtils.isTrimEmpty(UserConfig.token)) {
                request = request.newBuilder().addHeader("token", UserConfig.token).build();
                request = request.newBuilder().addHeader("Allcore-Auth", UserConfig.token).build();
            }
            //如果已登陆飞手，则所有接口添加 飞手guid到请求头
            if (!StringUtils.isTrimEmpty(UserConfig.workerUuid))
                request = request.newBuilder().addHeader("workerGuid", UserConfig.workerUuid).build();
            //如果已连接飞机，则所有接口添加 飞机serialNumber
            if (!StringUtils.isTrimEmpty(UserConfig.deviceUuid))
                request = request.newBuilder().addHeader("deviceGuid", UserConfig.deviceUuid).build();
            request = request.newBuilder().addHeader("Connection", "keep-alive").build();
            return chain.proceed(request);
        });
        /**
         * ErrorInterceptor 必须要添加到 EncryptInterceptor 之后
         */
        //加密拦截器
        LibraryHttp.addInterceptor(new EncryptInterceptor());
        //地区 特殊加密拦截器 用以转换加密方式
//        if (BuildConfig.ENCRYPT_TYPE == 0) {
//            LibraryHttp.addInterceptor(new EncryptInterceptor0());
//        }
//        if (BuildConfig.ENCRYPT_TYPE == 1) {
//            LibraryHttp.addInterceptor(new EncryptInterceptor1());
//        }
//        if (BuildConfig.ENCRYPT_TYPE == 2) {
//            LibraryHttp.addInterceptor(new EncryptInterceptor2());
//        }
        LibraryHttp.addInterceptor(new HttpLoggingInterceptor(s ->
                Log.i("网络请求日志拦截器", s)));
        //修改地址拦截器
        LibraryHttp.addInterceptor(new LocalInterceptor());
        //错误码拦截器
        LibraryHttp.addInterceptor(new ErrorInterceptor());
        //rxjava线程优化 减少调度
        Scheduler f = Schedulers.from(ThreadPoolUtils.cachePool());
        Schedulers.computation().shutdown();
        Schedulers.newThread().shutdown();
        Schedulers.io().shutdown();
        //释放线程对象
        ReflectUtils.reflect(Schedulers.class).field("COMPUTATION", null);
        ReflectUtils.reflect(Schedulers.class).field("IO", null);
        ReflectUtils.reflect(Schedulers.class).field("NEW_THREAD", null);

        RxJavaPlugins.setComputationSchedulerHandler((it) -> f);
        RxJavaPlugins.setIoSchedulerHandler((it) -> f);
        RxJavaPlugins.setNewThreadSchedulerHandler((it) -> f);
        mAppContext = app;

    }
}
