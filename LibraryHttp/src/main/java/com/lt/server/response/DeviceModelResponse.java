package com.lt.server.response;

import java.util.List;

/**
 * 获取台账，即当前通用设备精细学习模板
 * path:app/lineLedger/ledgerDeviceModel
 */
public class DeviceModelResponse {
    /**
     * 设备采集航迹模板集合
     */
    public List<String> modelData;
}
