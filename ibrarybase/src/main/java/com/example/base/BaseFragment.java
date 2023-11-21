package com.example.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import com.lt.func.ICallBack;
import com.lt.router.Router;
import com.lt.window.AppLoading;
import com.lt.window.IAppLoading;

public abstract class BaseFragment<T extends IBasePresenter<?>> extends AbstractBaseFragment<T>
        implements IBaseView {
    protected IAppLoading appLoading;

    /**
     * 这个变量用来控制是否是第一次启动。
     * 在onResume中进行判断。
     */
    private boolean isFirstStart = true;

    @Override
    public void launch(String action) {
        Router.launch(this, action);
    }

    @Override
    public void launch(Class<? extends Context> clazz) {
        Router.launch(this, clazz);
    }

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        appLoading = createLoading();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //如果是第一次启动，那么执行firstPerformResume()函数
        if (isFirstStart) {
            isFirstStart = false;
            firstPerformResume();
        }
    }

    /**
     * 第一次执行onResume函数
     */
    protected void firstPerformResume() {
        //由子类实现
    }

    protected IAppLoading createLoading() {
        return new AppLoading(mContext);
    }

    @Override
    public void onDestroyView() {
        if (appLoading != null)
            appLoading.release();
        appLoading = null;
        super.onDestroyView();
    }

    @Override
    public void finishActivity() {
        FragmentActivity activity = getActivity();
        if (activity == null)
            return;
        activity.finish();
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

    @Override
    public boolean isShowing() {
        if (appLoading != null)
            return appLoading.isShow();
        else
            return false;
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
    public View getContentView() {
        return containerView;
    }
}
