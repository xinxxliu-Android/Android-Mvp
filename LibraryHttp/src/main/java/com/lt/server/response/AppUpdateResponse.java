package com.lt.server.response;

/**
 * 查询app新版本列表
 * path:app/version/versionInfo
 * 获取新版本接口
 */
public class AppUpdateResponse {
    /**
     * 1641866024000 版本发布时间
     */
    public long upTime;
    /**
     * 文件路径
     * group1/M00/02/27/wKgBw2Hc43iAfWamAAAAPvLtZGo340.apk
     * 使用时，将使用 当前api的 host 拼接该路径
     * 如：
     * {@link com.lt.config.BaseUrlConfig#BASE_URL}/group1/M00/02/27/wKgBw2Hc43iAfWamAAAAPvLtZGo340.apk
     */
    public String filePath;
    /**
     * 版本更新日志
     * 从app当前版本--该版本
     * 注意：使用换行符隔开没个条目
     */
    public String description;

    /**
     * 版本名 如 2。2。1
     */
    public String versionName;
    /**
     * 版本号 如 99
     */
    public int versionCode;

    /**
     * 否强制更新 0 非强制 1 强制
     */
    public int compel;
}
