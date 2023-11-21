package com.lt.server.response;

/**
 * 下载航线文件
 * path:app/lineLedger/deviceProtocol
 */
public class LineDownloadResponse {
    /**
     * 最后更新时间
     */
    public String updateTime;
    /**
     * 更新操作员
     */
    public String updater;
    /**
     * 设备识别码
     */
    public String deviceGuid;
    /**
     * 设备所属线路/分组id
     */
    public String groupGuid;
    /**
     * 设备类型
     */
    public int deviceType;

    /**
     * 设备坐标 台账数据 高度为 海拔高度
     */
    public String la, lo, al;
    /**
     * 协议文件数据实体
     */
    public String data;
}
