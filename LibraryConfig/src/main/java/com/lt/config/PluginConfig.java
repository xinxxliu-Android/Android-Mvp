package com.lt.config;

import android.app.Application;

import com.lt.utils.SPEncrypted;

import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;

/**
 * 该类 定义了 一系列当前项目的状态信息函数
 * 包含：
 * 当前是否是debug模式
 * 当前是否需要连接安全交互平台
 * 当前是否是已集成i国网项目
 * 当前是否是单机版应用
 */
@SuppressWarnings("unused")
public final class PluginConfig {
    static Application mAppContext;

    public static void install(Application app) {
        mAppContext = app;
        PhotoConfig.init();
//        //屏幕适配
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(true)
                .setSupportSP(true)
                .setSupportSubunits(Subunits.NONE);

        //初始化 缓存的 飞机sn码
        if (!isDevelopMode())
            UserConfig.deviceUuid = null;
    }

    /**
     * 当前是否是测试模式
     * 测试模式 与 生产模式逻辑相同
     *
     * @return x
     */
    public static boolean isBetaMode() {
        return BuildConfig.CHANNEL_TYPE <= 1;
    }

    /**
     * 当前 是否是开发模式。
     * 如果当前是测试模式，那么 所有数据及跳转通道全部打开。
     * 会有数据缺失崩溃问题  仅供开发调试使用
     * 如需修改，编辑 Project.channel文件 修改 ChannelType字段即可
     * 开发模式 飞机sn码固定写死 注意
     *
     * @return x
     */
    public static boolean isDevelopMode() {
        return BuildConfig.CHANNEL_TYPE <= 0;
    }

    /**
     * 是否是单机版本
     * 如果当前为单机版吧 则 所有网络操作全部屏蔽。
     *
     * @return x
     */
    public static boolean isAlone() {
        return BuildConfig.IS_ALONE;
    }

    /**
     * 是否需要连接安全交互平台
     *
     * @return x
     */
    public static boolean useSafeChannel() {
        return BuildConfig.SAFE_CHANNEL;
    }

    /**
     * 当前是否是已集成i国网项目
     *
     * @return x
     */
    public static boolean useIgw() {
        return BuildConfig.USE_IGW;
    }

    /**
     * 当前是否集成i国网并使用I国网进行登陆
     *
     * @return x
     */
    public static boolean useIgwLogin() {
        return BuildConfig.USE_IGW_LOGIN;
    }

    /**
     * 当前是否取消连接无人机功能点击事项
     *
     * @return x
     */
    public static boolean useIsAirLimit() {
        return BuildConfig.IS_AIRCRAFT_LIMIT;
    }

    /**
     * 获取当前DJI SDK版本
     */
    public static int getDjiVersion(){
        return BuildConfig.DJI_VERSION;
    }

    public static SignMode signMode() {
        return BuildConfig.SIGN_MODE.equals("SLAB") ? SignMode.SLAB : SignMode.AIRCRAFT;
    }

    public static boolean isEncryptType() {
        return BuildConfig.ENCRYPT_TYPE == 0;
    }

    public static boolean isHttps() {
        return BuildConfig.HTTP_TYPE == 1;
    }

    public static boolean isEnergy() {
        return BuildConfig.IS_ENERGY;
    }

    /**
     * 当前 是否处于 离线化状态
     * @return  x 根据sp配置获取
     */
    public static boolean isOffline(){
        return OfflineConfig.isOfflineMode();
    }
    /**
     * app 授权模式
     */
    public enum SignMode {
        AIRCRAFT,
        SLAB
    }
}
