package com.lt.window;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Space;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.lt.func.ICallBack;
import com.lt.func.IReleasable;
import com.lt.widget.v.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * app 全局loading
 */
@SuppressLint("NonConstantResourceId")
public final class WarningDialog extends Dialog implements IReleasable {
    Context mContext;
    View contentView;
    Unbinder unBinder;

    /**
     * 警告信息
     */
    @BindView(R2.id.info)
    TextView info;
    @SuppressLint("NonConstantResourceId")
    @BindView(R2.id.title)
    TextView title;

    @BindView(R2.id.cancel)
    android.widget.TextView cancelView;
    @BindView(R2.id.cancel_space)
    Space cancelSpace;

    @BindView(R2.id.confirm)
    android.widget.TextView confirmView;
    private ICallBack<Boolean> callback;

    private WindowManager.LayoutParams attributes;

    public WarningDialog(@NonNull Context context) {
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
    }

    @Override
    public void show() {
        ThreadUtils.getMainHandler().post(this::realShow);
    }

    @SuppressWarnings("deprecation")
    private void realShow() {
        if (isShowing())
            return;

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
            return;
        }
        Window window = getWindow();
        attributes = window.getAttributes();
        attributes.width = (int) (ScreenUtils.getScreenWidth() * 0.8);
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.gravity = Gravity.CENTER;
        window.setAttributes(attributes);
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
        contentView = systemService.inflate(R.layout.module_window_window_mission_gohome, null, false);
        unBinder = ButterKnife.bind(this, contentView);
        Window window = getWindow();
        if (window != null)
            window.setBackgroundDrawableResource(R.color.transparent);
        addContentView(contentView, new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
        //setCanceledOnTouchOutside(false);
        //设置返回键无效
        //设置点击外部不消失
        setCancelable(false);
        setOnDismissListener((it) -> callback = null);
    }

    public void show(String cancelString, String confirmString, String title, String info, ICallBack<Boolean> callBack) {
        show(title, info, callBack);
        cancelView.setText(cancelString);
        confirmView.setText(confirmString);
    }

    @SuppressLint("SetTextI18n")
    public void show(String warnInfo, ICallBack<Boolean> callBack) {
        if (isShowing())
            return;
        realShow();
        info.setText(warnInfo);
        title.setText("警告");
        this.callback = callBack;
    }

    public void updateCancelBtnVisible(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        cancelView.setVisibility(visibility);
        cancelSpace.setVisibility(visibility);
    }

    @SuppressLint("SetTextI18n")
    public void show(String title, String warnInfo, ICallBack<Boolean> callBack) {
        realShow();
        info.setText(warnInfo);
        this.title.setText(title);
        this.callback = callBack;
    }

    @OnClick(R2.id.confirm)
    void confirmClicked() {
        callback.callback(true);
        dismiss();
    }

    @OnClick(R2.id.cancel)
    void cancelClicked() {
        dismiss();
    }

    @Override
    public void dismiss() {
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
    }
}
