package com.lt.server.response;

/**
 * 申请进入/退出离线模式
 * path：xxx 暂未确定
 * 功能：申请进入离线模式指定时间
 */
public final class OfflineResponse {

    /**
     * 最长可保持离线模式时间 天
     */
    public long maxTime;
}
