package com.lt.server.request;

/**
 * 台账相关
 * 获取设备位置等信息
 * 即 获取杆塔信息
 * path：app/lineLedger/deviceInfoList
 */
public class LedgerDeviceRequest {
    /**
     * 分组/线路识别码
     */
    public String groupGuid;
    /**
     * 页数 默认0
     */
    public int pageNo;
    /**
     * 单页长度 默认10
     */
    public int pageSize;

    public String getGroupGuid() {
        return groupGuid;
    }

    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
