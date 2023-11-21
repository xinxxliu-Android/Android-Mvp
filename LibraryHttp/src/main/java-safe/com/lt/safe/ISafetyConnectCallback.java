package com.lt.safe;

/**
 * 安全交互连接回调
 */
public interface ISafetyConnectCallback {

    /**
     * 连接结果
     *
     * @param result  true连接成功、false连接失败
     * @param address 建立安全连接后地址
     * @param port    建立安全连接后端口号
     */
    void onConnectResult(boolean result, String address, String port);

    /**
     * 终止连接
     *
     * @param address 连接地址
     * @param port    端口号
     */
    void onConnectStop(String address, String port);

    /**
     * 建立ISCP连接时 Log
     *
     * @param message
     * @param address
     * @param port
     */
    void onIscpLog(String message, String address, String port);
}
