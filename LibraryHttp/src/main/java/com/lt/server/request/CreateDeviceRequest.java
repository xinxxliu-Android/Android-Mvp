package com.lt.server.request;

/**
 * 新建设备接口
 * app请求新建设备
 */
public class CreateDeviceRequest {
    /**
     * 分组名
     * 如 蓝天一线 参考{@link com.lt.server.response.Group}中字段
     */
    public String groupName;
    /**
     * 分组类型 参考 {@link com.lt.server.response.Group}中字段
     */
    public int groupType;
    /**
     * 如果当前是在已有的分组中新建设备，那么该字段有值
     * 否则，为空 参考 {@link com.lt.server.response.Group}中字段
     */
    public String groupGuid;
    /**
     * 设备名 如： #3
     * 参考 {@link com.lt.server.response.Device}中字段
     */
    public String deviceName;
    /**
     * 设备类型
     * 参考 {@link com.lt.server.response.Device}中字段
     */
    public int deviceType;

    /**
     * 设备电压等级
     * 参考 {@link com.lt.server.response.Device}中字段
     */
    public String voltageLevel;
}
