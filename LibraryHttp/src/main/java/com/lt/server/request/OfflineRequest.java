package com.lt.server.request;

/**
 * 申请进入/退出离线模式
 * path：xxx 暂未确定
 * 功能：申请进入离线模式指定时间
 */
public final class OfflineRequest {
    /**
     * 无人机唯一识别码
     * 部分地区 可能使用多个飞机
     */
    public String deviceGuid;

    /**
     * 遥控器 唯一识别码
     * 部分地区 可能使用多个飞机
     */
    public String remoteGuid;

    /**
     * 进入离线模式 0
     * 退出离线模式 1
     */
    public int offlineType;

    /**
     * 申请时长 毫秒 比如 1月 1 * 30 * 24 * 60 * 60 * 1000
     */
    public long time;
}
