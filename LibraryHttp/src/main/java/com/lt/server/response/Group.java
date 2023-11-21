package com.lt.server.response;

import java.util.List;

/**
 * 设备分组对象
 * 其中：台账数据 与 工单数据统一至该对象
 * 20220831更新
 */
public final class Group {
    /**
     * 线路/分组 识别码
     */
    public String groupGuid;
    /**
     * 分组名
     */
    public String groupName;
    /**
     * 分组类型
     * 杆塔 风机 光伏等
     * 其中：
     * 0：输电分组
     * 1：配电分组
     * 2：风机分组
     * 3：光伏分组
     */
    public int groupType;

    /**
     * 分组内的设备 guid集合 全部数据
     * 获取台账数据时，无法一次性获取分组内的所有设备数据
     * 所以，使用该字段，遍历获取，方式数据量过大
     */
    public List<String> deviceNumbers;
    /**
     * 网格化数据
     */
    public GridZone gridZone;

    /**
     * 线路上的杆塔的 杆塔对象集合
     * 仅供工单使用
     */
    public List<Device> devices;
    /**
     * 抽象空间类型
     */
    public static class Zone {
        /**
         * 网格废弃
         * 只有当前空域为 多边形 才有效
         * 当前空域经纬度集合
         * 格式为：纬度,经度
         */
        public List<String> locations;
        /**
         * 网格
         * 只有当前空域为 多边形 才有效
         * 当前空域经纬度集合
         * 格式为：纬度,经度
         */
        public String gridLocation;

        /**
         * 图形类型 其中：
         * 1：多边形    由多个坐标点相连构成的区域
         * 2：圆形     由坐标点中心 结合半径 构成的区域
         * 3：椭圆     由 坐标中心点，短半径，长半径构成的区域
         */
        public int graphicsType;

        /**
         * 只有当前空域为 圆形 或 椭圆形才有效
         * 如果为 圆形 或 椭圆时，空域坐标中心点
         * 格式为：纬度,经度
         */
        public String centerLocation;
        /**
         * 只有当前空域为 圆形 或 椭圆形才有效
         * 如果当前空域为圆形，则为空域半径 单位：米
         * 如果当前空域为椭圆，则为短半径 单位：米
         */
        public String radius;
        /**
         * 只有当前空域为 椭圆形时，才有效
         * 当前椭圆空域的 长半径
         */
        public String radiusLong;
        /**
         * 空域名称 用以标识空域
         */
        public String zoneName;

        /**
         * 空域 guid
         */
        public String zoneGuid;
        /**
         * 限制高度 海拔
         * 只有在限飞区 或者禁飞区 该字段才有效
         */
        public String height;

        /**
         * 有效开始时间
         * 如果时间未知，可以为0 则始终有效
         */
        public String startTime;
        /**
         * 有效结束时间
         * 如果时间未知，可以为0 则始终有效
         */
        public String endTime;
    }

    /**
     * 网格数据
     */
    public static class GridZone extends Zone {

    }
}
