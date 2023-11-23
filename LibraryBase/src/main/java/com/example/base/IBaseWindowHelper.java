package com.example.base;

import com.lt.func.IBaseHelper;
import com.lt.func.ICallBack;

/**
 * 抽象的 window辅助类
 */
public interface IBaseWindowHelper<T extends IBaseView> extends IBaseHelper<T> {
    void showWarnWindow(String warnInfo, ICallBack<Boolean> callBack);
}
