package com.lt;

import android.view.View;

import com.lt.func.IReleasable;

/**
 * 地图配置
 * 一般用于 初始化地图 或 批量配置地图信息
 */
public interface IMapSettings extends IReleasable {
    /**
     * 地图初始化配置
     */
    void initSettings();

    /**
     * 设置图层缩放到最大
     */
    void setMinZoomLevel();

    /**
     * 设置图层缩放到最小
     */
    void setMaxZoomLevel();

    void setMapClickListener(View.OnClickListener listener);
}
