package com.lt.config;

import com.blankj.utilcode.BuildConfig;

/**
 * 巡检模式 常量
 */
public interface MissionModeConfig {
    /**
     * 手动采集
     */
    int mission0 = 0;
    /**
     * 精细巡检
     */
    int mission1 = 1;
    /**
     * 精细巡检-红外
     */
    int mission2 = 2;
    /**
     * 手动巡检
     */
    int mission3 = 3;
    /**
     * 手动巡检-红外
     */
    int mission4 = 4;
    /**
     * 通道巡检
     */
    int mission5 = 5;
    /**
     * 通道巡检-红外
     */
    int mission6 = 6;
    /**
     * 快速通道巡检
     */
    int mission7 = 7;
    /**
     * 快速通道巡检-红外
     */
    int mission8 = 8;
    /**
     * 树障巡视
     */
    int mission9 = 9;
    /**
     * 树障巡视-红外
     */
    int mission10 = 10;
    /**
     * 设备坐标修正
     */
    int mission11 = 11;
    /**
     * 新建分组/设备
     * 已过时 该巡检模式 不适用于APP端
     */
    @Deprecated
    int mission12 = 12;
    /**
     * 点云采集(可见光)
     */
    int mission13 = 13;
    /**
     * 弓字航带
     */
    int mission14 = 14;
    /**
     * 配网直线巡视
     */
    int mission15 = 15;
    /**
     * 配网直线塔学习
     */
    int mission16 = 16;
    /**
     * 点云采集-激光 界面
     */
    int mission17 = 17;
    /**
     * 手动通道巡检
     * 暂未开发
     */
    int mission18 = 18;
    /**
     * 光伏巡检
     */
    int mission19 = 19;
    /**
     * 风机巡检
     */
    int mission20 = 20;
    /**
     * 网格化巡检自主
     */
    int mission21 = 21;

    /**
     * 航带巡检
     * 只支持 两个点间 长方形巡检
     */
    int mission23 = 23;
    /**
     * 风机巡检
     */
    int mission24 = 24;

    /**
     * 配网自主巡检
     */
    int mission25 = 25;
}
