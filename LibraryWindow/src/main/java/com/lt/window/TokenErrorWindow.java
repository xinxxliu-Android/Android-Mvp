package com.lt.window;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.lt.config.ActionConfig;
import com.lt.config.PluginConfig;
import com.lt.config.SPConfig;
import com.lt.config.UserConfig;
import com.lt.config.WindowDimConfig;
import com.lt.config.WorkerConfig;
import com.lt.utils.SPEncrypted;
import com.lt.widget.v.TextView;
import com.lt.window.base.BasePopupWindow;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 航线任务执行前检查出任务无法执行
 */
@SuppressLint("NonConstantResourceId")
public final class TokenErrorWindow extends BasePopupWindow<TokenErrorWindow> {

    @BindView(R2.id.info)
    TextView info;
    @BindView(R2.id.title)
    TextView title;
    private Context context;

    public TokenErrorWindow(Context context) {
        super(context);
        this.context = context;
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
    void performFinish() {
        //清除sp缓存的飞手信息
        SPEncrypted userInfo = SPEncrypted.getInstance(SPConfig.Worker.NAME);
        userInfo.clear(true);
        //TODO 所有从该管理器中获取到的库，全部清空
        UserConfig.workerUuid = null;
        WorkerConfig.userName = "";
        WorkerConfig.loginDuration = 0.00;
        WorkerConfig.inspectionTimes = 0;
        WorkerConfig.maximumFlightAltitude = 0;
        WorkerConfig.totalFlightMileage = 0;
        WorkerConfig.totalFlightTime = 0;
        WorkerConfig.workerOutDateTime = -1;
        WorkerConfig.totalinspectionMileage = 0;
        WorkerConfig.workerCreateTime = 0;
        dismiss();
        if (PluginConfig.useIgwLogin()) {
            System.exit(0);
        } else {
            Intent intent = new Intent();
            intent.setAction(ActionConfig.Main.ACTION_FLY_WORKER);
            intent.putExtra("tokenError", "tokenError");
            context.startActivity(intent);
        }
    }

    public void show(String title, String message) {
        showAtAnchorView(ActivityUtils.getTopActivity().getWindow().getDecorView(), YGravity.CENTER, XGravity.CENTER);
        info.setText(message);
        this.title.setText(title);
    }
}
