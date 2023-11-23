package com.lt.window.mission;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lt.config.WindowDimConfig;
import com.lt.window.R;
import com.lt.window.R2;
import com.lt.window.base.BasePopupWindow;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 航线任务执行结束后的弹窗
 * 两个按钮，一个返航 一个取消返航
 * content为 结束航迹的原因
 */
@SuppressLint("NonConstantResourceId")
public final class MissionFinishedWindow extends BasePopupWindow<MissionFinishedWindow> {
    /**
     * 结束任务的弹窗
     * 如果正常结束飞行任务 正常提示 结束航迹任务
     * 否则，显示错误信息
     */
    @BindView(R2.id.finish_msg)
    TextView finishMsgView;

    private String finishMsg;

    public MissionFinishedWindow(Context context) {
        super(context);
    }

    @Override
    protected void initAttributes() {
        @SuppressLint("InflateParams") View contentView = inflater.inflate(R.layout.module_window_window_mission_finished, null);
        setContentView(contentView)
                .setWidth((int) (ScreenUtils.getScreenWidth() * 0.6))
                .setOutsideTouchable(false)
                .setFocusAndOutsideEnable(false)
                .setBackgroundDimEnable(true)
                .setDimValue(WindowDimConfig.DIM_3);
    }

    @OnClick(R2.id.finish)
    void finishMission(){
        dismiss();
    }

    public void show(){
        showAtAnchorView(ActivityUtils.getTopActivity().getWindow().getDecorView(), YGravity.CENTER, XGravity.CENTER);
    }


    @SuppressLint("SetTextI18n")
    public void show(String finishMsg){
        this.finishMsg = finishMsg;
        show();
        if (finishMsgView == null)
            return;
        if (!StringUtils.isTrimEmpty(finishMsg))
            finishMsgView.setText(finishMsg + "\n"+"结束执行航迹任务！！");
        else
            finishMsgView.setText("航线任务已结束！！");
    }
}
