package com.lt.window;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.lt.func.ICallBack;
import com.lt.widget.v.ImageView;
import com.lt.widget.v.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * app 全局loading
 */
@SuppressLint("NonConstantResourceId")
public final class AppLoading extends Dialog
        implements IAppLoading {
    Context mContext;
    View contentView;
    Unbinder unBinder;
    boolean isShowing;
    @BindView(R2.id.tv_loading_content)
    TextView mLoadingContent;
    @BindView(R2.id.iv_loading_icon)
    ImageView mLoadingIcon;
    @BindView(R2.id.close)
    ImageView close;
    ICallBack<Integer> callBack;
    private WindowManager.LayoutParams attributes;

    public AppLoading(@NonNull Context context) {
        super(context, R.style.app_dialog);
        this.mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentView();
        //背景透明处理
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setDimAmount(0f);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != callBack) {
                    callBack.callback(0);
                }
                dismiss();
            }
        });
    }

    @Override
    public void show() {
        if (Thread.currentThread() == Looper.getMainLooper().getThread())
            realShow();
        else
            ThreadUtils.getMainHandler().post(this::realShow);
    }

    @Override
    public void show(ICallBack<Integer> iCallBack) {
        callBack = iCallBack;
        if (Thread.currentThread() == Looper.getMainLooper().getThread())
            realShow();
        else
            ThreadUtils.getMainHandler().post(this::realShow);
    }

    @SuppressWarnings("deprecation")
    private void realShow() {
        if (isShowing || isShowing())
            return;
        isShowing = true;
        /**
         * 保证在弹出dialog时不会弹出虚拟按键且事件不会穿透。
         */
        if (this.getWindow() != null) {

            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,

                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        }
        super.show();
        if (attributes != null) {
            update("正在加载...");
            if (mLoadingIcon != null)
                mLoadingIcon.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_loading));
            return;
        }
        Window window = getWindow();
        attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.gravity = Gravity.CENTER;
        window.setAttributes(attributes);
        if (mLoadingIcon != null)
            mLoadingIcon.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_loading));
        update("正在加载...");
        if (Build.VERSION.SDK_INT >= 30)
            hideSystemUi30();
        else {
            hideSystemUi23();
        }
    }

    @SuppressWarnings("deprecation")
    private void hideSystemUi23() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.INVISIBLE;

        uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        Window window = getWindow();
        if (window == null)
            return;
        View decorView = window.getDecorView();
        if (decorView == null)
            return;
        decorView.setSystemUiVisibility(uiFlags);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void hideSystemUi30() {
        Window window = getWindow();
        if (window == null)
            return;
        View decorView = window.getDecorView();
        if (decorView == null)
            return;
        WindowInsetsController controller = decorView.getWindowInsetsController();
        if (controller == null)
            return;
        controller.hide(WindowInsets.Type.systemBars());
        controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
        window.setDecorFitsSystemWindows(false);
    }

    @SuppressLint("InflateParams")
    private void loadContentView() {
        LayoutInflater systemService = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = systemService.inflate(R.layout.library_base_loading, null, false);
        unBinder = ButterKnife.bind(this, contentView);
        Window window = getWindow();
        if (window != null)
            window.setBackgroundDrawableResource(R.color.transparent);
        addContentView(contentView, new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
        //setCanceledOnTouchOutside(false);
        //设置返回键无效
        //设置点击外部不消失
        setCancelable(false);
    }

    @Override
    public void dismiss() {
        isShowing = false;
        Window window = getWindow();
        if (window == null)
            return;
        if (window.isActive())
            return;
        if (!isShowing())
            return;
        super.dismiss();
    }

    @Override
    public void release() {
        if (isShowing())
            dismiss();
        if (unBinder != null)
            unBinder.unbind();
        attributes = null;
        unBinder = null;
        contentView = null;
        mContext = null;
        callBack = null;
    }

    @Override
    public void update(@NonNull String text) {
        ThreadUtils.getMainHandler().post(() -> {
            if (mLoadingContent == null)
                return;
            mLoadingContent.setVisibility(StringUtils.isTrimEmpty(text) ? View.GONE : View.VISIBLE);
            mLoadingContent.setText(text);
        });
    }

    @Override
    public boolean isShow() {
        return isShowing();
    }
}
