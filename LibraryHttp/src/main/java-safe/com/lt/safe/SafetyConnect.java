package com.lt.safe;

import android.text.TextUtils;

import com.lt.config.BaseUrlConfig;
import com.lt.config.SafeConnectedConfig;

/**
 * 建立安全交互平台连接
 */
public class SafetyConnect {

    private static volatile SafetyConnect safetyConnect;

    private boolean connect = false;

    public boolean isConnect() {
        return this.connect;
    }

    /**
     * 开始连接
     *
     * @param address           安全交互平台连接地址
     * @param port              安全交互平台端口号
     * @param internetRegionUrl 互联网大区ip地址
     * @param connectCallback   连接结果回调
     */
    public void connect(String address, String port, String internetRegionUrl, IConnectCallback connectCallback) {
        SafetyConnectManager.getInstance().connect(address, port, new ISafetyConnectCallback() {
            @Override
            public void onConnectResult(boolean result, String address, String port) {
                if (!result) {
                    connect = false;
                    return;
                }
                connect = true;
                // 开始连接互联网大区
                String url = IpPortConvert.enter2Safety(internetRegionUrl);
                if (!TextUtils.isEmpty(url)) {
                    BaseUrlConfig.BASE_URL = url + "/";
                    String newUrl = url.replace("http", "ws");
                    BaseUrlConfig.SOCKET_URL = newUrl + "/" + BaseUrlConfig.SOCKET_URL_PATH;
                    SafeConnectedConfig.updateSafeConnected(true);
                    connectCallback.connectSuccess();
                } else {
                    connectCallback.connectFail();
                }
            }

            @Override
            public void onConnectStop(String address, String port) {
                connect = false;
                SafeConnectedConfig.updateSafeConnected(false);
                connectCallback.connectStop();
            }

            @Override
            public void onIscpLog(String message, String address, String port) {

            }
        });
    }

    public static SafetyConnect getInstance() {
        if (safetyConnect == null) {
            synchronized (SafetyConnect.class) {
                if (null == safetyConnect) {
                    safetyConnect = new SafetyConnect();
                }
            }
        }
        return safetyConnect;
    }
}
