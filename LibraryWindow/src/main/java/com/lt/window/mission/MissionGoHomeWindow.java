package com.lt.window.mission;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.lt.config.WindowDimConfig;
import com.lt.dj.flight.DeviceFlightManager;
import com.lt.dj.state.FlightState;
import com.lt.func.ICallBack;
import com.lt.widget.v.TextView;
import com.lt.window.R;
import com.lt.window.R2;
import com.lt.window.base.BasePopupWindow;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 返航弹窗
 */
@SuppressLint("NonConstantResourceId")
public final class MissionGoHomeWindow extends BasePopupWindow<MissionGoHomeWindow> {
    /**
     * 以文字显示
     * 当前返航高度如
     * 当前返航高度为:120米
     */
    @BindView(R2.id.info)
    TextView info;
    private ICallBack<Boolean> callback;

    public MissionGoHomeWindow(Context context) {
        super(context);
    }

    @Override
    protected void initAttributes() {
        View contentView = inflater.inflate(R.layout.module_window_window_mission_gohome, null);
        setContentView(contentView)
                .setWidth((int) (ScreenUtils.getScreenWidth() * 0.6))
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

    @SuppressLint("SetTextI18n")
    public void show(ICallBack<Boolean> callBack) {
        showAtAnchorView(ActivityUtils.getTopActivity().getWindow().getDecorView(), YGravity.CENTER, XGravity.CENTER);
        FlightState flightState = DeviceFlightManager.getInstance().getState().state();
        int goHomeHeight = flightState.getGoHomeHeight();
        info.setText("当前返航高度为：" + goHomeHeight + " 米");
        this.callback = callBack;
    }
}
