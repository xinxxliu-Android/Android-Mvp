package com.example.base;

import android.os.Handler;
import android.os.Looper;

import com.lt.func.IBaseHelper;

/**
 * 默认的 自定义handler
 * 可作为当前页面的消息处理器。
 * 如需使用，自行继承实现即可
 */
public class BaseHandler<T> extends Handler implements IBaseHelper<T> {
    BaseHandler(){
        super(Looper.getMainLooper());
    }
    protected T t;
    @Override
    public void attach(T t) {
        this.t = t;
    }

    @Override
    public void detach() {
        this.t = null;
    }
}
