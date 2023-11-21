package com.lt.server.response;

/**
 * 设备对象
 * 工单设备对象 及 台账设备对象，全部为该对象
 * 20220831日更新api 统一设备对象为该对象
 * 其中：所有字段 理论上都来自于台账数据
 */
public final class Device {
    /**
     * 设备所属分组 名
     *
     * xxxx线
     * xxx工区
     * xxxx工区
     * xx2号网格
     */
    public boolean isSelect;
    public String groupName;
    /**
     * 设备所属分组id
     */
    public String groupGuid;

    /**
     * 分组类型
     * 杆塔 风机 光伏等
     * 其中：
     * 0：杆塔分组
     * 1：风机分组
     * 2：光伏分组
     * 3：网格分组(多种设备构成的分组，分组内可同时拥有多个设备)
     */
    public int groupType;

    /**
     * 设备识别码
     */
    public String deviceGuid;

    /**
     * 设备编号
     * 可用于 杆塔编号/风机编号等
     */
    public String deviceNumber;

    /**
     * 设备名
     * 如：xxx号杆塔  或 xxx号风机  或 xxx号太阳能板 等
     * 设备完全限定名 此字段在重命名、工单使用时会进行展示
     * TODO 暂未使用该字段
     */
    public String deviceName;

    /**
     * 设备备注
     */
    public String deviceMarker;

    /**
     * 0输电，1配电，2风机，3光伏
     */
    public int deviceType;

    /**
     * 电压等级
     */
    public String voltageLevel;

    /**
     * 设备 PMS编码
     */
    public String pmsCode;

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
     *  设备是否存在精细巡检航迹
     */
    public boolean hasProtocol;

    /**
     * 设备的 RFID 标识
     */
    public String rfid;

    /**
     * 当前设备是否已巡检
     * 默认0;0未巡检;1已巡检
     */
    public int inspect;
    /**
     * 风机塔桶高度
     * 必填
     */
    public String deviceAltitude;

    /**
     * 叶片长度
     * 必填
     */
    public String bladeLength;
}
