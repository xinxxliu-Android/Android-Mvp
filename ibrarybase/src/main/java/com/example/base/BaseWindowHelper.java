package com.example.base;

import com.lt.func.ICallBack;
import com.lt.window.WarningWindow;

/**
 * 抽象的 弹窗辅助管理器
 * 统一定制：警告消息弹窗
 * @param <T>
 */
public abstract class BaseWindowHelper<T extends IBaseView> implements IBaseWindowHelper<T> {
    protected T mView;
    private WarningWindow warnWindow;

    @Override
    public void showWarnWindow(String warnInfo, ICallBack<Boolean> callBack) {
        if (warnWindow == null)
            warnWindow = new WarningWindow(mView.getContext());
        warnWindow.show(warnInfo,callBack);
    }

    @Override
    public void attach(T t) {
        this.mView = t;
    }

    @Override
    public void detach() {
        this.mView = null;
    }
}
