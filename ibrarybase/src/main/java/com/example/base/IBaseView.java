package com.example.base;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;

import com.lt.func.ICallBack;

public interface IBaseView extends IAbstractBaseView, LifecycleOwner {
    /**
     * 启动导航
     * 导航器 高级使用方式，参考 {@link com.lt.router.RouterExample}
     *
     * @param action 目标界面标记
     */
    void launch(String action);

    /**
     * 启动导航
     * 导航器 高级使用方式，参考 {@link com.lt.router.RouterExample}
     *
     * @param clazz 目标界面类
     */
    void launch(Class<? extends Context> clazz);

    LifecycleOwner getLifecycleOwner();

    /**
     * 显示加载中
     */
    void startLoading();

    /**
     * 显示加载中加入回调
     */
    void startLoading(ICallBack<Integer> callBack);

    /**
     * 当前是否显示弹窗
     *
     * @return x
     */
    boolean isShowing();

    /**
     * 更新加载中的描述
     * title使用最近一次更新的title
     *
     * @param info 描述
     */
    void updateLoading(String info);

    /**
     * 结束加载 消失加载中弹窗
     */
    void endLoading();

    /**
     * 结束当前界面
     */
    void finishActivity();

    View getContentView();
}
