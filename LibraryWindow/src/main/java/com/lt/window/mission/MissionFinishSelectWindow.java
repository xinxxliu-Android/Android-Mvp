package com.lt.window.mission;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.lt.config.WindowDimConfig;
import com.lt.func.ICallBack;
import com.lt.window.R;
import com.lt.window.R2;
import com.lt.window.base.BasePopupWindow;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import butterknife.OnClick;

/**
 * 手动终止任务选择弹窗
 * 两个按钮：一个取消、一个确认
 * 注：防止失误点击造成任务终止
 */
public class MissionFinishSelectWindow extends BasePopupWindow<MissionFinishSelectWindow> {

    private ICallBack<Boolean> callBack;

    public MissionFinishSelectWindow(Context context) {
        super(context);
    }

    @OnClick({R2.id.finish, R2.id.cancel_finish})
    void onClicked(View v) {
        int id = v.getId();
        if (callBack != null && id == R.id.finish)
            callBack.callback(true);
        dismiss();
    }

    @Override
    protected void initAttributes() {
        @SuppressLint("InflateParams") View contentView = inflater.inflate(R.layout.module_window_window_mission_finish_selected, null);
        setContentView(contentView)
                .setWidth((int) (ScreenUtils.getScreenWidth() * 0.6))
                .setOutsideTouchable(false)
                .setFocusAndOutsideEnable(false)
                .setBackgroundDimEnable(true)
                .setDimValue(WindowDimConfig.DIM_3);
    }

    public void show(ICallBack<Boolean> callBack) {
        this.callBack = callBack;
        showAtAnchorView(ActivityUtils.getTopActivity().getWindow().getDecorView(), YGravity.CENTER, XGravity.CENTER);
    }

    @Override
    public void onDismiss() {
        super.onDismiss();
        callBack = null;
    }
}
