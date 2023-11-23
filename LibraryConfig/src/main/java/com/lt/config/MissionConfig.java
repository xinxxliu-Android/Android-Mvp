package com.lt.config;

import android.annotation.SuppressLint;

import androidx.annotation.IntRange;

/**
 * 通道巡检飞行准备设置
 */
public class MissionConfig {
    /**
     * 相对设备高度
     * 单位：m
     */
    public static int height = 15;
    /**
     * 拍照间隔
     * 单位：2s
     */
    public static int photo_Interval = 2;
    /**
     * 飞行速度
     * 单位：5m/s
     */
    public static int flight_speed = 5;
    /**
     * 云台角度
     */
    public static int gimbal_angle = -90;

    /**
     * 通道类型巡检 是否执行拍照
     */
    public static boolean captureEnable = true;

    /**
     * 巡检时的配置信息
     * 不同模式 解析为 不同对象
     * 目前 用于 23 航带巡检 MissionZoneEntity对象
     */
    public static String missionConfig;

    /**
     * 精细巡检时（配网）拍照模式
     * 1 前后 2左右 3前后左右
     */
    public static int mission1_configure_net_shoot_mode = 1;

    /**
     * 精细巡检时（配网）拍照距离
     * 默认值5米
     */
    public static int mission1_configure_shoot_distance = 5;

    /**
     * 精细巡检时（配网）拍照时云台角度
     * -20 ～-70 默认值-45
     */
    public static int mission1_configure_shoot_gimbal_angle = -45;

    /**
     * 精细巡检时（配网）跨塔相对航点高度
     */
    public static int mission1_configure_above_height = 3;

    /**
     * 当前是否是风机巡检
     * 注意 当前临时使用 后期调整设计
     */
    public static boolean isFeng = false;
}
