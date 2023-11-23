package com.lt.config;

/**
 * 安全交互平台及互联网大区URL
 */
public interface SafetyUrlConfig {

    // -------------   重庆地区  --------------
    /**
     * 安全连接ip地址
     */
    String SAFETY_ADDRESS = "222.178.134.15";
    /**
     * 安全连接端口
     */
    String SAFETY_PORT = "20082";
    /**
     * 互联网大区连接Url
     * 重庆测试   http://172.30.34.210:18080/
     * 重庆正式  http://172.30.33.237:18080/
     */
    String INTERNET_REGION_URL = "http://172.30.33.237:18080/";
}
