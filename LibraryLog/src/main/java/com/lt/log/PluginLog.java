package com.lt.log;

import android.app.Application;
import android.content.Context;

/**
 * 日志模块
 */
public final class PluginLog {
    static Context appContext;
    //注册日志，直接进行初始化、配置等

    /**
     * 使用 Application时，会导致获取路径的方法为空
     * @param app
     */
    public static void install(Context app){
        appContext = app;
        initInstall();
    }

    /**
     * 初始化日志模块，开始工作
     */
    public static void initInstall() {
        //初始化日志框架
        LogUtils.initialize(appContext,BuildConfig.DEBUG);
        //设置日志级别
        LogUtils.setLogLevel(-1);
        //设置日志保留时间
        LogToFile.setFileSaveDays(1);
        //输出设备详细信息
        LogUtils.wtf(DeviceDetailInfo.getDevicesInfo(appContext));
        // 判断是否超过设置日志文件保留时长
        if (LogToFile.isDeleteLogFile()) {
            LogToFile.deleteFile();
        }
    }
}
