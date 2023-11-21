package com.lt.server.request;

/**
 * 查询app新版本
 * path:app/version/versionInfo
 * 获取新版本接口
 * {@link  com.lt.server.response.AppUpdateResponse}
 * 校核时间：2023-0213 11：31
 */
public class AppUpdateRequest {
    /**
     * 当前版本名称
     */
    public String versionName;
    /**
     * 当前版本号
     */
    public int versionCode;
    /**
     * 当前版本发布时间
     * yyyy-MM-dd HH:mm:ss
     */
    public String updateTime;
}
