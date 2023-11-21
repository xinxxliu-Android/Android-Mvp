package com.lt.log;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.dianping.logan.Logan;
import com.dianping.logan.LoganConfig;
import com.dianping.logan.SendLogCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Create by wangpeng in 2022/11/28.
 * Describe: 日志管理方法，全局唯一入口，负责日志工具的初始化，
 *           参数配置，以及日志信息的写入，保存和上传。
 *           所有的日志操作均通过此类
 *           单例实现
 */
public class LogManager {

    private volatile static LogManager logManager;
    private  LogManager() {}

    /**
     * 单例模式构造唯一对。
     * 保证线程安全。
     * volatile: 保证 logManager = new LogManager()时指令按顺序执行，防止指令重排序优化。
     *           防止多线程下，出现 logManager 不为空却还没有初始化的问题。
     *
     * @return
     */
    public static LogManager getInstance(){
        if (null == logManager){
            synchronized (LogManager.class){
                if (null == logManager){
                    logManager = new LogManager();
                }
            }
        }
        return logManager;
    }

    /**************************************以下为参数配设置和初始化方法*************************************/
    //mmap缓存路径
    private String cachePath;
    //日志文件保存路径
    private String filePath;
    private Context context;
    //日志日期
    private static SimpleDateFormat simpleDateFormat;
    //对外提供设置方法，可以设置mmap文件的缓存路径，如不设置，则按照默认路径保存
    public void setCachePath(String cachePath){
        this.cachePath = cachePath;
    }

    //对外提供方法,可以得到当前mmap文件的保存路径
    public String getCachePath(){
        return cachePath;
    }

    //对外提供设置方法，可以设置log文件的保存路径，如不设置，则按照默认路径保存
    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    //对外提供方法,可以得到当前日志文件的保存路径
    public String getFilePath(){
        return filePath;
    }


    /**
     * 日志工具初始化方法
     */
    public void init(Context context){

        this.context = context;

        /**
         * 设置默认路径
         */
        if (null == cachePath){
            cachePath = context.getFilesDir().getAbsolutePath();

        }
        /**
         * 设置默认路径
         */
        if (null == filePath){
            filePath = context.getExternalFilesDir(null).getAbsolutePath()
                    + File.separator +"logan";
        }
        //日志配置类
        LoganConfig loganConfig = new LoganConfig.Builder()
                .setCachePath(cachePath)
                .setPath(filePath)
                .setEncryptIV16("3232318612399072".getBytes())
                .setEncryptKey16("3232318612399072".getBytes())
                .setMaxFile(20)
                .build();
        //初始化
        Logan.init(loganConfig);
        //初始化成功后，将设备信息写入文件
        log(DeviceDetailInfo.getDevicesInfo(context),DEVICE_INFO);


    }

    private static SimpleDateFormat getSdf() {
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss.SSS ", Locale.getDefault());
        }

        return simpleDateFormat;
    }

    /*****************************************以下为日志输出到文件的方法***********************************/
    //日志类型: 平板设备信息
    private final int DEVICE_INFO = 10;
    //日志类型: 无人机设备信息
    private final int UVA_INFO = 11;
    //日志类型: 用户信息信息
    private final int USER_INFO = 12;
    //日志类型: 网络请求信息
    private final int NETWORK_INFO = 13;
    //日志类型: 程序异常错误信息
    private final int ERROR_INFO = 14;
    //todo  其他类型的日志可在此添加，如：航点航迹信息，巡检信息，FPV信息等等

    public void log(String logInfo,int logType){
        String logMessage = getSdf().format(new Date()) + "  " + logInfo;

        Logan.w(logMessage,logType);
    }

    /*****************************************以下为日志文件上传的方法*************************************/

    private final String url = "http://124.206.76.16:28080/logan-web/logan/upload.json";
    //当前登录的飞手名
    private String name;

    public void setName(String name){
        if (TextUtils.isEmpty(name)){
            this.name = "user not logged in";
        }else {
            this.name = name;
        }
    }

    public void upload(){
        //如果是debug模式，则不进行上传
        if (LogUtils.isDebuggable()){
            return;
        }
        Logan.s(url, //服务器地址
                formatDate("yyyy-MM-dd HH:mm:ss"),  //上传日期
                BuildConfig.PROJECT_PACKAGE_NAME, //app包名
                name,
                Build.ID,
                BuildConfig.VERSION_CODE,
                BuildConfig.VERSION_NAME,
                new SendLogCallback() {
                    @Override
                    public void onLogSendCompleted(int statusCode, byte[] data) {
                        LogUtils.i("日志文件上传结果:"+statusCode);
                    }
                }
        );
    }


    public void upload(UploadResult result){
        //如果是debug模式，则不进行上传
        if (LogUtils.isDebuggable()){
            result.result(-1);
            return;
        }
        Logan.s(url, //服务器地址
                formatDate("yyyy-MM-dd HH:mm:ss"),  //上传日期
                BuildConfig.PROJECT_PACKAGE_NAME, //app包名
                name,
                Build.ID,
                BuildConfig.VERSION_CODE,
                BuildConfig.VERSION_NAME,
                new SendLogCallback() {
                    @Override
                    public void onLogSendCompleted(int statusCode, byte[] data) {
                        Log.e("上传结果",statusCode+",");
                        result.result(statusCode);
                    }
                }
        );
    }


    private String formatDate(String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String date = dateFormat.format(new Date());
        return date;
    }

    public interface UploadResult{
        void result(int code);
    }
}