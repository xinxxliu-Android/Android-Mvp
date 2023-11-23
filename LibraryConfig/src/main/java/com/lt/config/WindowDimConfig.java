package com.lt.config;

import android.graphics.drawable.Drawable;

/**
 * window 透明度配置
 * {@link android.widget.PopupWindow#setBackgroundDrawable(Drawable)}中{@link Drawable#setAlpha(int)}的值
 */
public interface WindowDimConfig {
    /**
     * 透明度
     */
    float DIM_6 = 0.6f;
    float DIM_7 = 0.7f;
    float DIM_8 = 0.8f;
    float DIM_5 = 0.5f;
    float DIM_4 = 0.4f;
    float DIM_3 = 0.3f;
}
