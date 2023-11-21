package com.lt.server.request;

/**
 * 上传 精细学习数据
 * path:/app/main/upgradeProtocol
 */
public class OrderUpgradeProtocolRequest {
    /**
     * 航迹协议 对应的设备guid
     */
    public String deviceGuid;
    /**
     * 当前工单设备 对应的分组guid
     */
    public String groupGuid;
    /**
     * 工单guid
     */
    public String orderGuid;
    /**
     * 巡检性质 0非红外 1红外
     */
    public int workType;

    /**
     * 采集该协议的 飞机型号 文字描述
     */
    public String deviceDisplayName;
    /**
     * 协议实体
     */
    public String protocolData;
}
