package com.lt.config;

/**
 * 安全交互平台连接状态
 */
public class SafeConnectedConfig {

    private static boolean isSafeConnected = false;

    /**
     * 更新连接状态
     * @param isSafeConnected x
     */
    public static void updateSafeConnected(boolean isSafeConnected) {
        SafeConnectedConfig.isSafeConnected = isSafeConnected;
    }

    /**
     * 获取连接状态
     * @return x
     */
    public static boolean isIsSafeConnected() {
        return isSafeConnected;
    }
}
