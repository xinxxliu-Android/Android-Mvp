package com.lt.server.request;

/**
 * 台账相关
 * 查询所有线路
 * path：app/lineLedger/lineInfoList
 */
public class LedgerLineRequest {
    /**
     * 页数 默认0
     */
    public int pageNo;
    /**
     * 单页数据量 默认10
     */
    public int pageSize;

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
