package com.lt.server.response;

/**
 * 航线文件实体类
 * 关联：LineDownloadNewResponse
 */
public class LineData {

    /**
     * 自定义标识
     */
    public String customType;

    /**
     * 航迹文件实体数据
     */
    public String data;

    /**
     * 设备识别码
     */
    public String deviceGuid;

    /**
     * 航迹文件名称
     */
    public String deviceFileName;
}
