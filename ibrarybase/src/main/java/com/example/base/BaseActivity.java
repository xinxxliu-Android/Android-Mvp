package com.example.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import com.hc.socket.event.ISocketManager;
import com.hc.socket.event.ISocketTask;
import com.hc.socket.task.SocketMsgTask;
import com.lt.config.IntentConfig;
import com.lt.func.ICallBack;
import com.lt.log.LogUtils;
import com.lt.router.Router;
import com.lt.rtmp.IPusher;
import com.lt.rtmp.RtmpPushManager;
import com.lt.window.AppLoading;
import com.lt.window.IAppLoading;

import butterknife.OnClick;
import butterknife.Optional;

@SuppressLint("NonConstantResourceId")
public abstract class BaseActivity<T extends IBasePresenter<?>> extends AbstractBaseActivity<T>
        implements IBaseView, ISocketManager {
    protected IAppLoading appLoading;

    @Nullable
    @Override
    protected ViewGroup.LayoutParams layoutParams() {
        return null;
    }

    @Override
    protected View layoutView() {
        return null;
    }

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return this;
    }

    @Override
    public void launch(String action) {
        Router.launch(this, action);
    }

    @Override
    public void launch(Class<? extends Context> clazz) {
        Router.launch(this, clazz);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keepScreenOn();
    }

    private boolean isScreenOn;

    protected final void keepScreenOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1)
            setTurnScreenOn(true);
        else
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        isScreenOn = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //统一 响应该事件进行关闭当前界面
        if (resultCode == IntentConfig.Code.FINISH_ACTIVITY)
            finishActivity();
        IPusher iPusher = RtmpPushManager.getInstance().nowPusher();
        if (iPusher == null)
            return;
        iPusher.onActivityResult(requestCode, data, resultCode);
    }

    @Override
    protected void onStart() {
        super.onStart();
        appLoading = createLoading();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected IAppLoading createLoading() {
        return new AppLoading(this);
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
    public boolean isShowing() {
        if (appLoading != null)
            return appLoading.isShow();
        else
            return false;
    }

    @Override
    protected void onDestroy() {
        LogUtils.d("onDestroy: ");
        if (appLoading != null)
            appLoading.release();
        appLoading = null;
        if (isScreenOn)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1)
                setTurnScreenOn(false);
            else
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onDestroy();
    }

    @Override
    public View getContentView() {
        return getWindow().getDecorView();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Optional
    @OnClick(R2.id.back)
    void backClicked() {
        finishActivity();
    }

    @NonNull
    @Override
    public ISocketTask getSocketTask() {
        //SocketCallback
        return SocketMsgTask.getSocketMsgTask(this);
    }
}
