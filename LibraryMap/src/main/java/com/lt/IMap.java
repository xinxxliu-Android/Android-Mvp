package com.lt;


import android.view.View;

import androidx.annotation.NonNull;

import com.lt.func.IReleasable;

/**
 * 地图View 抽象类。
 * 用以抽象不同view，并进行控制
 */
@SuppressWarnings("unused")
public interface IMap extends IReleasable {
    /**
     * 艹做地图的 命令工具
     * 如 移动视角 切换卫星图/普通2d图
     *
     * @return
     */
    @NonNull
    IMapCommon mapCommon();

    /**
     * 配置地图设置
     * 包含 地图构建后的 初始化配置
     *
     * @return
     */
    @NonNull
    IMapSettings mapSettings();

    /**
     * 地图生命周期 全程托管 外部无需调用
     *
     * @return
     */
    @NonNull
    IMapLifecycle lifecycle();

    /**
     * 艹做地图画面工具
     * 如：绘制图形，marker 等
     *
     * @return
     */
    @NonNull
    IMapUi mapUi();

    default View asView() {
        return (View) this;
    }
}
