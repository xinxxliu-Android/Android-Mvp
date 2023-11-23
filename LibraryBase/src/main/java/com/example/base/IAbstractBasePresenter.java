package com.example.base;

import com.lt.func.IBaseHelper;

public interface IAbstractBasePresenter<T extends IAbstractBaseView> extends IBaseHelper<T> {
    /**
     * 绑定当前页面
     * @param t 当前页面对象  具体实现 为 {@link AbstractBaseActivity} 或 {@link AbstractBaseFragment}的子实现类
     */
    void attach(T t);

    /**
     * 释放当前页面
     */
    void detach();
}
