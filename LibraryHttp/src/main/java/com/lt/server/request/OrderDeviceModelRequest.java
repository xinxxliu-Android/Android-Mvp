package com.lt.server.request;

/**
 * 获取工单设备精细学习模板
 * path：/app/main/orderDeviceModel
 * 功能：下发精细学习工单时，需对应绑定下发当前设备对应的杆塔学习模板
 * 注意：下发精细学习工单时，可从平台配置下发 一个设备 多个学习模板形式
 */
public class OrderDeviceModelRequest {
    /**
     * 当前需要采集航迹的设备 guid
     */
    public String deviceGuid;
    /**
     * 当前需要采集航迹的设备 所对应的分组 的guid
     */
    public String groupGuid;
    /**
     * 当前工单 guid
     */
    public String orderGuid;
}
