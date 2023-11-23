package com.example.base;

import android.content.Context;

public interface IAbstractBaseModel {
    /**
     * 绑定当前页面 并持有当前页面引用
     * 该函数可多次调用
     * @param c 当前页面引用
     */
    void attach(Context c);

    /**
     * 释放当前页面
     */
    void detach();
}
