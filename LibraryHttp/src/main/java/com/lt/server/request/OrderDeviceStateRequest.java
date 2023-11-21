package com.lt.server.request;

import java.util.List;
import java.util.Map;

/**
 * app巡检完设备同步
 * path：app/workOrder/updateInspectStatus
 * 当前杆塔巡检完毕后，调用接口上传当前巡检状态
 */
public class OrderDeviceStateRequest {
    /**
     * 当前工单guid
     */
    public String inspectGuid;

    /**
     * 架次guid
     */
    public String flyGuid;
    /**
     * 设备所在分组guid
     */
    public String groupGuid;
    /**
     * 设备所在线路名称
     */
    public String groupName;
    /**
     * 设备guid
     */
    public String deviceGuid;

    /**
     * 设备 RFID
     */
    public String deviceRfid;
    /**
     * 自主(yes)手动(no)
     */
    public String selfInspection;
    /**
     * 巡检模式
     */
    public int inspectMode;
    /**
     * 设备名称
     */
    public String deviceName;
    /**
     * 设备编号
     */
    public String deviceNumber;

    /**
     * 当前任务类型
     * 0-实时任务
     * 1-补传任务
     */
    public int taskType;

}
