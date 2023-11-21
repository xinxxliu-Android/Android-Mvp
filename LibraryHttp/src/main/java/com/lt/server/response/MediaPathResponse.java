package com.lt.server.response;

/**
 * app获取流媒体服务地址
 * path：    app/validate/streamMediaUrl
 * 根据 无人机识别码，获取当前飞机的推流地址。
 * rtmp地址 需要转换格式的话 在后台操作
 */
public class MediaPathResponse {
    /**
     * 推流地址
     */
    public String value;
}
