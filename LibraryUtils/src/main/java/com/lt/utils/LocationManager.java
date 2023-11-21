package com.lt.utils;

import android.annotation.SuppressLint;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lt.log.LogUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * 位置信息管理类
 */
public class LocationManager {
    private static final LocationManager m = new LocationManager();
    /**
     * 最后一个 信号最好的 位置信息 GPS位置
     */
    static Location lastBestLocationGPS;
    /**
     * 最后一个 信号最好的 位置信息 网络位置
     */
    static Location lastBestLocationNET;
    android.location.LocationManager locationManager = PluginUtils.mAppContext.getSystemService(android.location.LocationManager.class);
    /**
     * 信号最好的 位置渠道
     */
    String bestProvider;

    private LocationManager() {
    }

    @SuppressLint("MissingPermission")
    public void launchLocationService() {
        if (locationManager != null)
            return;
        //每秒获取位置服务器 有gps就拿gps 没gps就拿网络
        Disposable subscribe = Flowable.interval(500, 1000, TimeUnit.MILLISECONDS)
                .subscribeOn(RxSchedulers.request())
                .subscribe(this::requestProviders);
        int MIN_TIME = 1;
        int MIN_DISTANCE = 1;

        if (hasGpsProvider())
//            locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, location -> {
//                LogUtils.d("获取到网GPS位数据-"+location);
//            });
            locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {

                }

                @Override
                @Deprecated
                public void onStatusChanged(String provider, int status, Bundle extras) {
//                    LocationListener.super.onStatusChanged(provider, status, extras);
                }
            });

        if (hasNetworkProvider()) {
//            locationManager.requestLocationUpdates(android.location.LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, location -> {
//                LogUtils.d("获取到网络定位数据-"+location);
//            });
            locationManager.requestLocationUpdates(android.location.LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    LogUtils.d("获取到网络定位数据-" + location);
                }

                @Override
                @Deprecated
                public void onStatusChanged(String provider, int status, Bundle extras) {
//                    LocationListener.super.onStatusChanged(provider, status, extras);
                }
            });
        }
        LogUtils.d("启动位置服务 subscribe" + subscribe);
    }

    public boolean hasNetworkProvider() {
        List<String> providers = locationManager.getProviders(true);
        if (providers == null || providers.isEmpty())
            return false;
        return providers.contains(android.location.LocationManager.NETWORK_PROVIDER);
    }

    public boolean hasGpsProvider() {
        List<String> providers = locationManager.getProviders(true);
        if (providers == null || providers.isEmpty())
            return false;
        return providers.contains(android.location.LocationManager.GPS_PROVIDER);
    }

    private void requestProviders(Long aLong) {
        Criteria criteria = new Criteria();
        //设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        //设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(true);
        //设置是否需要方位信息
        criteria.setBearingRequired(false);
        //设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        bestProvider = locationManager.getBestProvider(criteria, true);
        if (bestProvider == null) {
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        }
        bestProvider = locationManager.getBestProvider(criteria, true);
        //         LogUtils.d("定位方式：-"+bestProvider);
//        Toast.makeText(ActivityUtils.getTopActivity(), bestProvider, Toast.LENGTH_SHORT).show();
    }

    public static LocationManager getInstance() {
        return m;
    }

    @SuppressLint("MissingPermission")
    public @Nullable
    Location getLastBestLocation() {
        //获取 当前定位 支持的定位方式
        List<String> providers = locationManager.getProviders(true);
        Location lastKnownLocation = null;
        for (String provider : providers) {
            Location lastKnownLocation1 = locationManager.getLastKnownLocation(provider);
            if (lastKnownLocation1 == null)
                continue;

            if (lastKnownLocation == null || lastKnownLocation1.getAccuracy() < lastKnownLocation.getAccuracy()) {
                lastKnownLocation = lastKnownLocation1;
            }
        }
//        if (providers == null || providers.isEmpty() )
//            return null;
//        //拿最好的信号的定位数据 有可能 拿到的最好的定位方式 不支持 那么就走else 一般情况下 该函数在现场是可用的
//        if (providers.contains(bestProvider)){
//            lastKnownLocation = locationManager.getLastKnownLocation(bestProvider);
//        }else {
//            //拖地方案
//            for (String provider : providers) {
//                Location ll = locationManager.getLastKnownLocation(provider);
//                if (ll != null)
//                    return ll;
//            }
//        }
        return lastKnownLocation;
    }
}
