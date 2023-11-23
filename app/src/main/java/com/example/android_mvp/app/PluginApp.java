package com.example.android_mvp.app;

import android.app.Application;

import com.lt.log.LogUtils;

public class PluginApp {
    private static final String TAG = "PluginApp";
    static Application appContext;

    public static void install(Application app) {
        appContext = app;
        installBugly();
    }

    private static void installBugly() {
        LogUtils.d("初始化Bugly");
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(appContext);
//        strategy.setAppVersion(BuildConfig.VERSION_NAME);
//        strategy.setAppChannel(BuildConfig.CHANNEL_KEY);
//        strategy.setAppPackageName(BuildConfig.APPLICATION_ID);
//        //设置当前设备是否开发设备 当true时，崩溃信息会实时上传
//        if (!BuildConfig.DEBUG){
//            CrashReport.setIsDevelopmentDevice(appContext, true);
//        }
//        // 初始化Bugly
//        CrashReport.initCrashReport(appContext,"5666447400",BuildConfig.DEBUG, strategy);
//        //收集rxjava异常
//        RxJavaPlugins.setErrorHandler((e)->{
//            LogUtils.e("setErrorHandler: ", e);
//            CrashReport.postCatchedException(e);
//        });
    }
}
