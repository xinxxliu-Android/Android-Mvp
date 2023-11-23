package com.lt.window;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.lt.config.WindowDimConfig;
import com.lt.func.ICallBack;
import com.lt.widget.v.TextView;
import com.lt.window.base.BasePopupWindow;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 全局 警告信息弹窗
 */
public final class WarningWindow extends BasePopupWindow<WarningWindow> {
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

    @BindView(R2.id.confirm)
    android.widget.TextView confirmView;
    @BindView(R2.id.cancel_space)
    Space cancelSpace;
    private ICallBack<Boolean> callback;

    public WarningWindow(Context context) {
        super(context);
    }

    @Override
    protected void initAttributes() {
        View contentView = inflater.inflate(R.layout.module_window_window_mission_gohome, null);
        setContentView(contentView)
                .setWidth((int) (ScreenUtils.getScreenWidth() * 0.7))
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchable(false)
                .setBackgroundDimEnable(true)
                .setDimValue(WindowDimConfig.DIM_3);
    }

    @OnClick(R2.id.confirm)
    void confirmClicked() {
        callback.callback(true);
        dismiss();
    }

    @OnClick(R2.id.cancel)
    void cancelClicked() {
        callback.callback(false);
        dismiss();
    }

    @Override
    public void onDismiss() {
        this.callback = null;
        super.onDismiss();
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
        showAtAnchorView(ActivityUtils.getTopActivity().getWindow().getDecorView(), YGravity.CENTER, XGravity.CENTER);
        info.setText(warnInfo);
        title.setText("警告");
        this.callback = callBack;
    }


    @SuppressLint("SetTextI18n")
    public void show(String title, String warnInfo, ICallBack<Boolean> callBack) {
        if (isShowing())
            return;
        showAtAnchorView(ActivityUtils.getTopActivity().getWindow().getDecorView(), YGravity.CENTER, XGravity.CENTER);
        info.setText(warnInfo);
        this.title.setText(title);
        this.callback = callBack;
    }

    @SuppressLint("SetTextI18n")
    public void show(String title, String warnInfo, boolean isShowCancel, ICallBack<Boolean> callBack) {
        if (isShowing())
            return;
        showAtAnchorView(ActivityUtils.getTopActivity().getWindow().getDecorView(), YGravity.CENTER, XGravity.CENTER);
        int visibility = isShowCancel ? View.VISIBLE : View.GONE;
        cancelView.setVisibility(visibility);
        cancelSpace.setVisibility(visibility);
        info.setText(warnInfo);
        this.title.setText(title);
        this.callback = callBack;
    }

    public void updateInfo(String s) {
        info.setText(s);
    }
}
