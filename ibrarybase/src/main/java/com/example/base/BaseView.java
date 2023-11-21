package com.example.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lt.func.ICallBack;
import com.lt.router.Router;
import com.lt.window.AppLoading;
import com.lt.window.IAppLoading;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * V层的另一种实现：
 * 当当前界面控件比较多，不便管理时，可拆出view块，并实现该类的子类对象进行拆分构建。
 * 通常 在V层中实现该子View对象。而后调用attach进行绑定，在释放时，调用detach进行释放
 *  TODO 在项目真正使用该类时，开会讨论具体实现及注释说明
 *
 * @param <T> 可以是当前界面的presenter传过来赋值，也可以是该子view块自行实现的p层接口类型
 */
@SuppressWarnings("unchecked")
public abstract class BaseView<T extends IBasePresenter> implements IBaseView {
    protected Activity mContext;
    protected T mPresenter;
    protected IAppLoading appLoading;
    protected View contentView;
    protected boolean presenterDetach;
    protected Unbinder unbinder;

    @SuppressWarnings("all")
    @CallSuper
    public void attach(@NonNull View contentView, @Nullable T mPresenter) {
        mContext = (Activity) contentView.getContext();
        if (mContext == null)
            throw new RuntimeException("当前view已被销毁，状态不对");
        this.contentView = contentView;
        this.mPresenter = mPresenter;
        presenterDetach = mPresenter == null;
        if (presenterDetach)
            this.mPresenter = createPresenter();
        if (mPresenter == null)
            throw new RuntimeException("您必须 实现/传入 一个P层对象");
        mPresenter.attach(this);
        appLoading = createLoading();
        unbinder = ButterKnife.bind(this, contentView);
    }

    @CallSuper
    protected @Nullable
    IAppLoading createLoading() {
        return new AppLoading(mContext);
    }

    @CallSuper
    public void detach() {
        mContext = null;
        contentView = null;
        if (appLoading != null)
            appLoading.dismiss();
        appLoading = null;
        if (presenterDetach)
            mPresenter.detach();
        mPresenter = null;
        unbinder.unbind();
        unbinder = null;
    }

    protected T createPresenter() {
        return null;
    }

    @Override
    public void launch(String action) {
        Router.launch(mContext, action);
    }

    @Override
    public void launch(Class<? extends Context> clazz) {
        Router.launch(mContext, clazz);
    }

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return this;
    }


    @Override
    public void startLoading() {
        post(() -> {
            if (appLoading != null)
                appLoading.show();
        });
    }

    @Override
    public void startLoading(ICallBack<Integer> callBack) {
        post(() -> {
            if (appLoading != null)
                appLoading.show(callBack);
        });
    }

    /**
     * @param loadingText
     */
    @Override
    public void updateLoading(String loadingText) {
        post(() -> {
            if (appLoading != null)
                appLoading.update(loadingText);
        });
    }

    @Override
    public void endLoading() {
        post(() -> {
            if (appLoading != null)
                appLoading.dismiss();
        });
    }


    @Override
    public void finishActivity() {
        mContext.finish();
    }

    @Override
    public View getContentView() {
        return contentView;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mContext instanceof AppCompatActivity ? ((AppCompatActivity) mContext).getLifecycle() : null;
    }

    @Override
    public void showMessage(String message) {
        realShowMsg(message, false);
    }

    private void realShowMsg(String msg, boolean longShow) {
        if (longShow)
            ToastUtils.showLong(msg);
        else
            ToastUtils.showShort(msg);
    }

    @Override
    public void showMessage(@StringRes int messageRes) {
        showMessage(mContext.getString(messageRes));
    }

    @Override
    public void showLongMessage(String message) {
        realShowMsg(message, true);
    }

    @Override
    public void showLongMessage(@StringRes int messageRes) {
        showLongMessage(mContext.getString(messageRes));
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void post(Runnable runnable) {
        ThreadUtils.getMainHandler().post(runnable);
    }

    @Override
    public void postDelay(Runnable runnable, long timeMillis) {
        ThreadUtils.getMainHandler().postDelayed(runnable, timeMillis);
    }
}
