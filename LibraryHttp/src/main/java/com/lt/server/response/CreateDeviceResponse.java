package com.lt.server.response;

import java.util.List;

/**
 * 新建设备接口
 * app请求新建设备
 */
public class CreateDeviceResponse {
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
     * 网格化数据
     * 当前只有重庆需要
     */
    public Group.GridZone gridZone;

    /**
     * 新建成功后的 设备对象
     * 标准、通用的设备对象
     */
    public Device device;
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
    public static class GridZone extends Group.Zone {

    }
}
