package com.lt;

import androidx.annotation.NonNull;

import com.lt.entity.LocationEntity;
import com.lt.func.IReleasable;

/**
 * 操作地图控件命令
 * 如：
 * 移动视角到指定位置
 * 设置缩放
 */
public interface IMapCommon extends IReleasable {
    /**
     * 添加地图内标记
     *
     * @param le 需要移动到的点。
     *           <p>
     *           <p>
     *           该函数经常用以移动视角，会被频繁调用
     */
    void moveCamera(@NonNull LocationEntity le);

    void moveCamera(@NonNull LocationEntity le, boolean isClick);
    /**
     * 设置当前地图是否可以进行控制移动
     * <p>
     * 该函数经常用以移动视角，会被频繁调用
     */
    void setCamera(boolean touch);
}
