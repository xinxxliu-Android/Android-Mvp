package com.lt.server.response;

import java.util.List;

/**
 * 工单关联设备查询
 * path：app/workOrder/orderDetails
 * 即 工单详情信息
 */
public class OrderDeviceResponse {
    /**
     * 工单内需要巡检的分组的集合
     */
    public List<Group> data;
}
