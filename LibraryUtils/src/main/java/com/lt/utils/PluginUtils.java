package com.lt.utils;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import com.blankj.utilcode.util.Utils;
import com.lt.log.DeviceDetailInfo;
import com.lt.log.LogUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PluginUtils {
    static Application mAppContext;

    public static void install(Application app) {
        mAppContext = app;
        Utils.init(app);
    }

    /**
     * 获取 Android设备 唯一标识ID
     *
     * @return 唯一标识ID
     */
    public static String getAndroidDeviceId() {
//        String deviceSerialNumber = DeviceSerialUtils.getInstance().getDeviceSerialNumber();
//        LogUtils.e(deviceSerialNumber);
//        return deviceSerialNumber;
        return "";
    }
}
