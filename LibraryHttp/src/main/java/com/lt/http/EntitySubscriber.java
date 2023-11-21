package com.lt.http;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.lt.log.LogUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@SuppressWarnings("unused")
public abstract class EntitySubscriber<T> implements Subscriber<T>, LifecycleObserver {
    protected Subscription s;
    protected LifecycleOwner lifecycleOwner;
    private final String TAG = "EntitySubscriber";

    /**
     * 普通网络请求使用该类实现
     * 无法获取{@link LifecycleOwner}对象时，使用该构造函数构建对象
     */
    public EntitySubscriber() {
    }

    /**
     * 一般页面请求，使用该构造函数构建对象，防止内存泄漏。
     *
     * @param lifecycleOwner activity 或 fragment对象，即当前实现IBaseView接口的类
     */
    public EntitySubscriber(LifecycleOwner lifecycleOwner) {
        if (lifecycleOwner == null)
            return;
        this.lifecycleOwner = lifecycleOwner;
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        lifecycle.addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        LogUtils.d("ON_START");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        LogUtils.d("ON_RESUME");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        LogUtils.d("ON_PAUSE");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        LogUtils.d("ON_STOP");
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        LogUtils.d("ON_DESTROY");
        if (s != null) {
            s.cancel();
        }
        s = null;
        if (lifecycleOwner != null)
            lifecycleOwner.getLifecycle().removeObserver(this);
        lifecycleOwner = null;
    }

    public final void requestNext() {
        if (s != null) {
            s.request(1);
        }
    }

    public final void cancel() {
        if (s != null) {
            s.cancel();
        }
    }

    @Override
    public void onSubscribe(@NonNull Subscription s) {
        this.s = s;
        s.request(1);
    }

}
