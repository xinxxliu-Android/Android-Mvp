package com.lt.server.response;

/**
 *  获取交跨点信息
 *  android/main/flightCrossInfo
 */
public class FlightCrossInfoResponse {
    /**
     * 交跨航线id
     */
    public String crossSectionLineGuid;
    /**
     *  交跨点经度
     */
    public String crossLongitude;
    /**
     *  交跨点纬度
     */
    public String crossLatitude;
    /**
     *  杆塔id
     */
    public String towerGuid;
}
