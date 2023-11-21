package com.lt.server.response;

import java.util.List;

/**
 * 获取工单设备精细学习模板
 * path：/app/workOrder/orderDeviceModel
 * 功能：下发精细学习工单时，需对应绑定下发当前设备对应的杆塔学习模板
 * 注意：下发精细学习工单时，可从平台配置下发 一个设备 多个学习模板形式
 */
public class OrderDeviceModelResponse {
    /**
     * 设备采集航迹模板
     * 单个设备可能存在绑定的多个模板
     * 9cebddaa79cad752ac
     */
    public List<String> modelData;
}
