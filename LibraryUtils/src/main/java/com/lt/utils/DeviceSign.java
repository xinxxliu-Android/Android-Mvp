package com.lt.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;

public class DeviceSign {

    /**
     * 获取设备序列号
     *
     * @param context 上下文
     * @return 设备序列号
     */
    @SuppressWarnings("deprecation")
    public static String getDeviceSerialNumber(Context context) {
        String deviceSerialNumber = "";

        // 获取 TelephonyManager 实例
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (telephonyManager != null) {
            // 获取设备序列号
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                deviceSerialNumber = telephonyManager.getImei();
            } else {
                deviceSerialNumber = telephonyManager.getDeviceId();
            }
        }

        return deviceSerialNumber;
    }
}
