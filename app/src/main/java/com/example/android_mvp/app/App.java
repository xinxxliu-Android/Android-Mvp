package com.example.android_mvp.app;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.example.base.PluginBase;
import com.lt.PluginHttp;
import com.lt.PluginMap;
import com.lt.config.PluginConfig;
import com.lt.log.LogManager;
import com.lt.utils.CrashHandler;
import com.lt.utils.PluginUtils;
import com.lt.utils.RxSchedulers;
import com.lt.window.PluginWindow;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Flowable;

/**
 * 项目 Application对象。
 * 唯一对象
 * 所有模块 Module Library  必须在此类中进行赋值初始化。
 * 必须显式调用
 */
public class App extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //解决65535问题
//        MultiDex.install(this);
//        Helper.install(this);
    }

    /**
     * 初始化各大模块。
     * 注意：此处所有install函数 只做赋值处理，故无耗时操作。
     * 如模块内需耗时处理，自行使用线程池进行处理，禁止阻塞install函数。
     */
    @Override
    public void onCreate() {
        super.onCreate();
//        PluginChannel.install(this);
        CrashHandler.getInstance().init(this);
        /**
         * 初始化日志框架
         * 因为在 PluginLog 中调用了此框架，
         * 所以此框架需要在 PluginLog.install(this)之前
         */
        LogManager.getInstance().init(this);
        /**
         * 项目 日志模块
         * 因为其他模块使用了日志模块，所以日志模块的初始化要放在使用日志的模块之前，
         * 避免出现日志获取存储路径的空指针异常。
         * 因为日志模块中调用了日志管理框架，所以需要在日志管理框架之后，其他所有模块之前进行初始化，
         * 避免出现空指针。
         * 此初始化顺序很重要
         */
        //工具类模块
        PluginUtils.install(this);
        //mvp 及 相关helper handler等抽象基类模块
        PluginBase.install(this);
        //项目统一配置模块
        PluginConfig.install(this);
        //项目  核心 大疆模块 包含大疆sdk 及 大疆ux控件
        //项目 数据库模块
        //项目加密模块
        //项目所有实体类模块
        //项目网络模块
        PluginHttp.install(this);
        //项目 图片加载、处理模块
        //项目 通用抽象接口模块
        //开启日志上传的定时任务
        uploadLogTask();
        //项目 地图模块 高德地图及 osmdroid地图
        PluginMap.install(this);
        //项目 文字转语音播放模块
        //项目 工单管理模块 工单服务相关
        //项目 路由模块，自定义Router
        //项目 弹窗Window、dialog抽象界面。
        PluginWindow.install(this);
        //项目 当前模块 app模块
        PluginApp.install(this);


        //启用严格模式
        initStrictMode();
    }

    /**
     * 启用严格模式。
     * 严格模式只有在debug模式下才可使用，用以检测内存泄露、非法函数、弹窗、api调用等 非法操作。
     * 正常的应用，在debug模式 + 严苛模式当正常运行。并无警告提示。否则需针对性处理
     * 内存、性能优化核心方案之一
     * 详细设置 参考：
     * <p>
     * https://blog.csdn.net/qq_21430549/article/details/51183103
     * <p>
     * <p>
     * 当前 大疆sdk存在内存泄露，无法开启严苛模式。后续sdk版本更新后查看。
     * 该手段为 检测代码编写规范的流程之一
     */
    int timeCount;

    private void initStrictMode() {
        //线程计数
        //调用gc
        Flowable.interval(1000, 2000, TimeUnit.MILLISECONDS)
                .subscribeOn(RxSchedulers.computation())
                .filter((it) -> {
                    // Log.e("APP", "---->" + Thread.activeCount());
                    //手动gc 尝试释放资源  当前项目线程过多 基本100左右
                    if (timeCount == 10) {
                        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
                        Set<Thread> obj = allStackTraces.keySet();
                        ArrayList<String> names = new ArrayList<>(obj.size());
                        for (Thread thread : obj) {
                            names.add("-" + thread.getName() + "-");
                        }
                        // LogUtils.d(MGson.toJson(names));
                        //  Log.e("APP1", "---->" + names.size()+"");
                        timeCount = 0;
                        //手动回收。 如果放开此函数，应用在返回后台后，会被立即回收
//                    Runtime.getRuntime().gc();
                    }

                    timeCount++;
                    return false;
                }).subscribe();
        if (true)
            return;
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }

    /**
     * 日志上传的定时任务，根据飞手执行任务的时长，暂定一小时上传一次
     */
    private void uploadLogTask() {
        long period = 1000 * 60 * 60;  //1小时
        Flowable.interval(period, TimeUnit.MILLISECONDS)
                .observeOn(RxSchedulers.computation())
                .subscribe(it -> {
                            LogManager.getInstance().upload();
                        }
                );
    }
}
