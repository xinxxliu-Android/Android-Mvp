package com.lt.config;

/**
 * 内存中保存飞手信息
 * 作用：由于每次打开关闭飞手界面会导致WorkerEntity置空
 * 特此在内存中存储一份飞手信息界面数据
 */
public final class WorkerConfig {

    // 飞手名称
    public static String userName;
    // 登录时长
    public static double loginDuration;
    // 飞行总时长
    public static double totalFlightTime;
    // 最大飞行高度
    public static int maximumFlightAltitude;
    // 飞行总里程
    public static long totalFlightMileage;
    // 巡检总里程
    public static long totalinspectionMileage;
    // 巡检次数
    public static int inspectionTimes;
    // 使用期限
    public static long workerOutDateTime;
    //    创建时间
    public static long workerCreateTime;

}
