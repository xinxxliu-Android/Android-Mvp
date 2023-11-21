package com.lt.server.request;

/**
 * 无人机 推流状态更新接口
 * path：暂未确定
 * 功能：当无人机开启/关闭推流时，调用该接口
 */
public final class MediaStateUpdateRequest {
    /**
     * 当前无人机推流的状态
     * 0：推流已关闭
     * 1：推流已开启
     */
    public int streamStatus;
}
