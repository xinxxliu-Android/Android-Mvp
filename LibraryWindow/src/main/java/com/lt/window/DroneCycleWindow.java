package com.lt.window;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.lt.config.WindowDimConfig;
import com.lt.entity.welcome.SignEntity;
import com.lt.func.ICallBack;
import com.lt.widget.v.ImageView;
import com.lt.widget.v.TextView;
import com.lt.window.base.BasePopupWindow;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 无人机维保周期弹窗
 */
public class DroneCycleWindow extends BasePopupWindow<DroneCycleWindow> {

    @BindView(R2.id.content)
    TextView content;

    private ICallBack<Object> callBack;

    public DroneCycleWindow(Context context) {
        super(context);
    }

    public void setCallBack(ICallBack<Object> callBack) {
        this.callBack = callBack;
    }

    @OnClick(R2.id.close)
    void closeWindow() {
        dismiss();
    }


    @Override
    protected void initAttributes() {
        View contentView = inflater.inflate(R.layout.module_window_window_drone_cycle, null);
        setContentView(contentView)
                .setWidth((int) (ScreenUtils.getScreenWidth() * 0.5))
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchable(false)
                .setBackgroundDimEnable(true)
                .setDimValue(WindowDimConfig.DIM_3);
    }

    @SuppressLint("SetTextI18n")
    public void show(SignEntity signEntity) {
        if (isShowing())
            return;
        showAtAnchorView(ActivityUtils.getTopActivity().getWindow().getDecorView(), YGravity.CENTER, XGravity.CENTER);
        String s = "";
        if (signEntity.inspecCount > 0 && signEntity.totalPeriod > 0 && signEntity.warnText > 0) {
            s = "您的无人机已累计使用 " + signEntity.warnText + " 架次，使用时间为 " + signEntity.totalPeriod + " 天，累计巡检杆塔 " + signEntity.inspecCount + " 基，即将达到维保周期，请及时交回进行维护保养。";
        } else if (signEntity.warnText > 0 && signEntity.totalPeriod > 0){
            s = "您的无人机已累计使用 " + signEntity.warnText + " 架次，使用时间为 " + signEntity.totalPeriod + " 天，即将达到维保周期，请及时交回进行维护保养。";
        } else if (signEntity.inspecCount > 0 && signEntity.totalPeriod > 0){
            s = "您的无人机已累计巡检杆塔 " + signEntity.inspecCount + " 基，使用时间为 " + signEntity.totalPeriod + " 天，即将达到维保周期，请及时交回进行维护保养。";
        } else if (signEntity.warnText > 0){
            s = "您的无人机已累计使用 " + signEntity.warnText + " 架次，即将达到维保周期，请及时交回进行维护保养。";
        } else if (signEntity.inspecCount > 0){
            s = "您的无人机已累计巡检杆塔 " + signEntity.inspecCount + " 基，即将达到维保周期，请及时交回进行维护保养。";
        } else if (signEntity.totalPeriod > 0){
            s = "您的无人机已累计使用时间 " + signEntity.totalPeriod + " 天，即将达到维保周期，请及时交回进行维护保养。";
        } else {
            s = "您的无人机即将达到维保周期使用时长，请及时交回进行维护保养。";
        }
        content.setText(s);
    }

    @Override
    public void onDismiss() {
        callBack.callback("");
        super.onDismiss();
    }
}
