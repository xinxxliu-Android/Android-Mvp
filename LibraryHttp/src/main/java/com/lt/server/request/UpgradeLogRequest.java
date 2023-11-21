package com.lt.server.request;

/**
 * app日志上传接口
 *     path:app/system/upgradeLog
 */
public final class UpgradeLogRequest {
    /**
     * 未加密的 log日志 最大量为1m
     */
    public String data;
}
