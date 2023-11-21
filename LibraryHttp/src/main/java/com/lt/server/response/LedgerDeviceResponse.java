package com.lt.server.response;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * 台账相关
 * 获取分组中设备信息
 * path：app/lineLedger/deviceInfoList
 */
public class LedgerDeviceResponse {
    /**
     * 分组内的设备信息
     */
    public List<Device> devices;

}
