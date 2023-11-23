package com.lt.config;

import com.blankj.utilcode.BuildConfig;

/**
 * 蒙东
 */
public class BaseUrlConfig {

    //http://39.98.109.194:8099/   外网地址
    public static String URL_DEBUG = "http://192.168.18.153";
    public static String URL_RELEASE = "http://192.168.18.153";
    public static String PORT = "8099";
    public static String BASE_URL = UserSettingsConfig.getServerUrl(BuildConfig.DEBUG ? URL_DEBUG : URL_RELEASE + ":" + PORT + "/");

    /**
     * 蒙东授权请求地址：
     * 由于蒙东地区使用平台是全景开发，平台上面没有录入授权功能，所以调用北京授权地址来授权
     */
    public static String SIGN_URL_RELEASE = "http://124.206.76.16";
    public static String SIGN_PORT = "28181";
    public static String SIGN_BASE_URL =  SIGN_URL_RELEASE + ":" + SIGN_PORT + "/";

    /**
     * Socket 地址 ws://127.0.0.1:30003/websocket/uav
     */
    public static String SOCKET_URL_HOST = "ws//";
    public static String SOCKET_URL_SCHERE = "127.0.0.1";
    public static String SOCKET_URL_POST = "30003";
    public static String SOCKET_URL_PATH = "websocket/uav";
    public static String SOCKET_URL = SOCKET_URL_HOST + SOCKET_URL_SCHERE + ":" + SOCKET_URL_POST + "/" + SOCKET_URL_PATH;
}
