package com.lt.window;

import com.lt.func.ICallBack;
import com.lt.func.IReleasable;

public interface IAppLoading extends IReleasable {
    void show();
    void show(ICallBack<Integer> iCallBack);
    boolean isShow();
    void dismiss();
    void update(String text);
}
