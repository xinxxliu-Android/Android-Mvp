package com.lt;

import android.app.Application;
import android.content.Context;

import com.lt.config.BuildConfig;
import com.lt.config.PathConfig;
import com.lt.utils.thread.ThreadPoolUtils;

import org.osmdroid.config.Configuration;

import java.io.File;

/**
 * 地图模块
 * 在欢迎界面 进行注册
 */
public class PluginMap {
    public static Application appContext;

    public static void install(Application app) {
        appContext = app;
    }

    /**
     * 初始化高德
     * 默认 同意高德隐私协议
     * 否则  地图无法正常显示，定位无法正常获取
     */
    public static void initInstall() {
        ThreadPoolUtils.commitSingle(() -> {
            //缓存路径 所有文件路径
            Configuration.getInstance().load(appContext, appContext.getSharedPreferences("osmdroid.sp", Context.MODE_PRIVATE));
            Configuration.getInstance().setOsmdroidBasePath(new File(PathConfig.getOfflineMapPath(), "osmdroid"));
            File cachePath = new File(PathConfig.getOfflineMapPath() + File.separator + "osmdroid" + File.separator + "tiles");
            Configuration.getInstance().setOsmdroidTileCache(cachePath);
            //设置用户标识
//            Configuration.getInstance().setUserAgentValue("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
            //设置请求头，不然天地图会拦截
            Configuration.getInstance().setUserAgentValue(BuildConfig.PROJECT_PACKAGE_NAME + "/" + BuildConfig.VERSION_NAME);
            //是否测试模式
            boolean developMode = false;
            Configuration.getInstance().setDebugMode(developMode);
            Configuration.getInstance().setDebugMapTileDownloader(developMode);
            Configuration.getInstance().setDebugMapView(developMode);
            Configuration.getInstance().setDebugTileProviders(developMode);
            Configuration.getInstance().setTileDownloadMaxQueueSize(Short.MAX_VALUE);
            Configuration.getInstance().setTileFileSystemMaxQueueSize(Short.MAX_VALUE);
            //限制线程池数
            Configuration.getInstance().setTileDownloadThreads((short) 20);
            Configuration.getInstance().setTileFileSystemThreads((short) 20);
            //地图 瓦片缓存数量 即 屏幕上显示的瓦片数缓存数
            Configuration.getInstance().setCacheMapTileCount((short) (8 * 8));
            //设置 文件缓存上限为 2g
            Configuration.getInstance().setTileFileSystemCacheMaxBytes(2048L * 1024 * 1024);
            //2g
            Configuration.getInstance().setTileFileSystemCacheTrimBytes(2048L * 1024 * 1024);
        });
    }

//    public static void initNoNetInstall() {
//        Configuration.getInstance().setOsmdroidBasePath(new File(PathConfig.MAP_PATH, "osmdroid"));
//        File cachePath = new File(PathConfig.MAP_PATH + "/osmdroid/", "map.mbtiles");
//        if (NetworkUtils.isConnected() || !cachePath.exists()) {
//            cachePath = new File(PathConfig.MAP_PATH + "/osmdroid/tiles");
//        }
//    }
}
