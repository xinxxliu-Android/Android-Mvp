package com.lt.server.request;

/**
 * 特殊工单 接受当前工单接口
 * 接受后 其他人不可再执行该工单
 * path：待定
 */
public final class OrderConfirmRequest {
    /**
     * 工单guid
     */
    public String orderGuid;
    /**
     * 飞手guid
     */
    public String workerGuid;
}
