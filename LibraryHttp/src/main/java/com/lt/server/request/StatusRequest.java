package com.lt.server.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 性能统计接口
 * path: app/uavData/reportPerfData
 */
public class StatusRequest {
    /**
     * path:obj
     * path：最近一次 该path接口的请求、响应信息
     */
    Map<String,Status> statusList;

    static class Status{
        /**
         * 当前 最近 请求当前接口 的 耗时
         */
        long timeStep;

        // 开始时间戳。暂时无用
        long beginAt;
        // 结束时间戳。暂时无用
        long endAt;
        /**
         * 最近一次接口请求响应码
         * 如果无网络 为-1
         */
        int code;
        /**
         * 如果接口请求失败。则为 失败信息
         */
        int msg;
        // 业务数据主键数组。根据不同的业务：若为拉取工单列表业务，则为工单GUID集合；若为上传图片业务，则为此业务的工单GUID。
        /**
         * 最近一次接口请求及响应的数据量
         * 如果接口请求失败，则为-1
         */
        long byteLength;
        List<String> perfLogBizDataIds = new ArrayList<>();
    }

    /**
     * 工单的 json对象
     * path:app/workOrder/appQueryTower
     */
    static class InspectList extends Status{
        /**
         * 工单上传需要的所有信息
         */
    }

    /**
     * 飞机状态上传
     * path app/uavData/appReceiveAircraft
     */
    static class AircraftStatus extends Status{
        /**
         * 飞机上传  需要的所有信息
         */
        String modelName;
    }
}
