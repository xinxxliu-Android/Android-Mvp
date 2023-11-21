package com.lt.server.request;

/**
 * 修正设备，修正设备信息接口
 * path：app/main/updateDevice
 */
public final class UpdateDeviceRequest {
    /**
     * 设备坐标 纬度
     * 84坐标系
     */
    public String la;
    /**
     * 设备坐标 经度
     * 84坐标系
     */
    public String lo;
    /**
     * 设备坐标 海拔高度
     */
    public String al;
    /**
     * 设备朝向
     * 下发非精细巡检工单时，该值为必要值
     * 取值范围： 180 - -180
     * 取值基准： 磁北方向
     */
    public double radius;
    /**
     * 电压等级 kv
     */
    public String voltageLevel;
    /**
     * 设备类型 0输电 1配电 2风机 3光伏
     */
    public int deviceType;
    /**
     * 当前设备 设备名
     */
    public String deviceName;
    public String deviceGuid;
    /**
     *  设备所属分组id
     */
    public String groupGuid;

    /**
     * 当前设备的 Rfid
     */
    public String deviceRfid;
}
