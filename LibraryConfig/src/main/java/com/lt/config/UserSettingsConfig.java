package com.lt.config;

import com.blankj.utilcode.util.StringUtils;
import com.lt.utils.SPEncrypted;

public class UserSettingsConfig {

    private static String serverUrl;

    public static String getServerUrl(String defaultBaseUrl) {
        if (StringUtils.isTrimEmpty(serverUrl)) {
            // 先读取缓存，缓存为空，从 SP 去读
            serverUrl = SPEncrypted.getInstance(SPConfig.Settings.NAME).getString(SPConfig.Settings.User.SERVER_URL);
            if (StringUtils.isTrimEmpty(serverUrl)) {
                // 若SP 也为空，使用默认
                serverUrl = defaultBaseUrl;
                // 更新SP
                SPEncrypted.getInstance(SPConfig.Settings.NAME).put(SPConfig.Settings.User.SERVER_URL, serverUrl, true);
            }
        }
        return serverUrl;
    }

    public static void updateServerUrl(String url) {
        serverUrl = url;
        BaseUrlConfig.BASE_URL = serverUrl;
        SPEncrypted.getInstance(SPConfig.Settings.NAME).put(SPConfig.Settings.User.SERVER_URL, serverUrl, true);
    }

}
