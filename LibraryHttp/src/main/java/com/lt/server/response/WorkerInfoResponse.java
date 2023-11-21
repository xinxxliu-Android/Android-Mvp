package com.lt.server.response;

public class WorkerInfoResponse {
    /**
     * 飞行总里程  单位：米
     */
    public int flyInstance;
    /**
     * 执飞总架次
     */
    public int workCount;
    /**
     * 飞行总时长 分
     */
    public double flyTime;
    /**
     * 巡检总里程 单位 米
     * 不包含无工单飞行
     */
    public int workOrderInstance;
    /**
     * 飞手唯一识别码 所有其他与用户有关的接口，全部添加该字段值的请求头
     * 其中 请求头为：workerGuid  值为该字段的值
     */
    public String workerGuid;
    /**
     * 飞手名字
     */
    public String workerName;
    /**
     * 飞行最大高度 米
     */
    public int flyHeightMax;
    /**
     * 用户创建时间 long
     */
    public long workerCreateTime;
    /**
     * 用户过期时间
     * 用以满足临时账户需要
     * 如果该字段为0  则为永久账户 long
     */
    public long workerOutDateTime;
    /**
     * 当前用户 是否拥有 查看实时视频权限
     * 0:无 1：有
     */
    public int videoPower;
    /**
     * 登陆总时长 单位：分
     */
    public double workTime;
}
