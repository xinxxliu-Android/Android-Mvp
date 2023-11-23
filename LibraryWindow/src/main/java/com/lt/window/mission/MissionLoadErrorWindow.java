package com.lt.window.mission;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.lt.config.WindowDimConfig;
import com.lt.widget.v.TextView;
import com.lt.window.R;
import com.lt.window.R2;
import com.lt.window.base.BasePopupWindow;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 航线任务执行前检查出任务无法执行
 */
@SuppressLint("NonConstantResourceId")
public final class MissionLoadErrorWindow extends BasePopupWindow<MissionLoadErrorWindow> {

    @BindView(R2.id.info)
    TextView info;
    public MissionLoadErrorWindow(Context context) {
        super(context);
    }

    @Override
    protected void initAttributes() {
        @SuppressLint("InflateParams") View contentView = inflater.inflate(R.layout.module_window_window_mission_loaderror, null);
        setContentView(contentView)
                .setWidth((int) (ScreenUtils.getScreenWidth() * 0.7))
                .setOutsideTouchable(false)
                .setFocusAndOutsideEnable(false)
                .setBackgroundDimEnable(true)
                .setDimValue(WindowDimConfig.DIM_3);
    }

    @OnClick(R2.id.finish)
    void performFinish(){
        dismiss();
    }
    public void show(String message) {
        showAtAnchorView(ActivityUtils.getTopActivity().getWindow().getDecorView(), YGravity.CENTER, XGravity.CENTER);
        info.setText(message);
    }
}
