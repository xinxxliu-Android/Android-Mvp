package com.lt.config;

import com.blankj.utilcode.util.StringUtils;

/**
 * 当前应用 登陆管理配置类
 * 数据存储，每次启动应用后，将数据初始化到该类中
 */
public final class UserConfig {
    //飞机 uuid
    public static String deviceUuid = DeviceUuidConfig.LT_DEVICE_ID;
    //飞手 uuid
    public static String workerUuid;
    //用户使用期限
    public static String usePeriod;
    /**
     * 飞手名
     */
    public static String workerName;
    //飞手token
    public static String token;
    //授权token
    public static String SignToken;
    //授权使用期限，非飞手
    public static String signPeriod;
    //大疆账号
    public static String djiUserName;
    /**
     * 当前用户 是否拥有 查看实时视频权限
     * 0:无 1：有
     * 注意 本字段 目前 仅支持 蒙东地区 融合渠道
     */
    public static int userVideoPower;
    public static boolean isLogin(){
        return !StringUtils.isTrimEmpty(token);
    }
}
