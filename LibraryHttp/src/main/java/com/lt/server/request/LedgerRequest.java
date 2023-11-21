package com.lt.server.request;

/**
 * 台账相关
 * 获取设备、设备航迹等信息
 * 即 获取杆塔信息
 * path：app/lineLedger/LedgerInfo
 */
public class LedgerRequest {
    /**
     * 本地数据库 最后更新时间
     * 响应数据后，只响应 该时间节点后更新的数据内容
     *
     * 注意 时间 只看 设备更新时间 不看航迹时间
     */
    public long updateTime;

    /**
     * 工单guid
     * 如果当前字段为空，则获取为台账数据
     * 否则如果当前字段不为空，则返回 当前工单内的所有分组、及设备信息
     */
    public String orderGuid;
}
