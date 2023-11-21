package com.lt.server.request;

/**
 * 查询工单信息
 * path：app/workOrder/orderList
 * 获取工单列表
 */
public class OrderListRequest {
    /**
     * 当前页 0开始
     */
    public int pageNo;
    /**
     * 请求数 默认10
     */
    public int pageSize;
    /**
     *  0 未完成  1 已完成
     */
    public int isFinish;
}
