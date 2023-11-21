package com.lt.server.response;

import java.util.List;
import java.util.Map;

/**
 * APP查询空域数据
 * path：app/fly/flyAirSpace
 * 获取 当前用户相关的 禁飞区 限飞区 适航区等数据
 */
public class FlyZoneResponse {
    /**
     * 适航区
     */
    public List<FlyZone> flyZones;
    /**
     * 限飞区
     */
    public List<WarningZone> warningZones;
    /**
     * 禁飞区
     */
    public List<InterceptZone> interceptZones;
    /**
     * 网格化数据
     */
    public List<GridZone> gridZones;

    /**
     * 抽象空间类型
     */
    public static class Zone {
        /**
         * 只有当前空域为 多边形 才有效
         * 当前空域经纬度集合
         * 格式为：纬度,经度
         */
        public List<String> locations;

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
     * 适航区
     */
    public static class FlyZone extends Zone {

    }

    /**
     * 限飞区
     */
    public static class WarningZone extends Zone {

    }

    /**
     * 禁飞区
     */
    public static class InterceptZone extends Zone {

    }

    /**
     * 网格数据
     */
    public static class GridZone extends Zone {

    }
}
