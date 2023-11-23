package com.lt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lt.func.IReleasable;

/**
 * 地图控件配置设置
 * 一般为地图的配置如：
 * 初始化配置
 */
public interface IMapLifecycle extends IReleasable {

    void lifeOnSaveInstanceState(@NonNull Bundle outState);

    void lifeOnStop();

    void lifeOnDestroy();

    void lifeOnPause();

    void lifeOnResume();

    void lifeOnRestoreInstanceState(@NonNull Bundle savedInstanceState);

    void lifeOnStart();

    void onLowMemory();

    void lifeOnCreate(@Nullable Bundle savedInstanceState);
}
