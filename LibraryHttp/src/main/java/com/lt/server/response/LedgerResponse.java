package com.lt.server.response;

/**
 * 台账相关
 * 获取设备、设备航迹等信息
 * 即 获取杆塔信息
 * path：app/lineLedger/LedgerInfo
 * 该接口响应示例为 LedgerRequest
 *
 * 注意 该接口以文件下载形式，直接下载所有当前飞手有权限获取的所有设备数据、及设备绑定的航迹数据
 */
public class LedgerResponse {

    public String lineType;
}
