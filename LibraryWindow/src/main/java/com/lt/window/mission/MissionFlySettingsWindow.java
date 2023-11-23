package com.lt.window.mission;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.NumberUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lt.config.MissionConfig;
import com.lt.config.WindowDimConfig;
import com.lt.func.ICallBack;
import com.lt.widget.v.SeekBar;
import com.lt.widget.v.TextView;
import com.lt.window.R;
import com.lt.window.R2;
import com.lt.window.base.BasePopupWindow;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 精细巡检设置弹窗
 */
public class MissionFlySettingsWindow extends BasePopupWindow<MissionFlySettingsWindow> {
    /**
     * 跨塔相对航点高度
     */
    @BindView(R2.id.tv_module_fragment_compare_height)
    TextView mTvCompareHeight;
    /**
     * 跨塔相对航点高度seekbar
     */
    @BindView(R2.id.seek_module_fragment_compare_height)
    SeekBar mSeekCompareHeight;
    /**
     * 拍摄方式
     */
    @BindView(R2.id.tv_module_fragment_shot_mode)
    TextView mTvShotMode;

    /**
     * 拍照距离
     */

    @BindView(R2.id.seek_module_window_shoot_distance)
    SeekBar mSeekShotDistance;
    /**
     * 拍照距离展示
     */
    @BindView(R2.id.tv_module_window_shoot_distance)
    TextView tvModuleWindowShootDistance;
    /**
     * 拍照点云台角度
     */
    @BindView(R2.id.tv_module_fragment_shot_angle)
    TextView mTvShotAngle;
    /**
     * 拍照点云台角度seekbar
     */
    @BindView(R2.id.seek_module_fragment_shot_angle)
    SeekBar mSeekShotAngle;
    /**
     * 拍照点水平差
     */
    @BindView(R2.id.tv_module_fragment_shoot_hor_distance)
    TextView mTvShootHorDistance;
    /**
     * 拍照点高度差
     */
    @BindView(R2.id.tv_module_fragment_shoot_ver_distance)
    TextView mTvShootVerDistance;
    FlyShootTypeChoiceWindow flyShotTypeChoiceWindow;

    ICallBack<Boolean> callBack;

    public MissionFlySettingsWindow(Context context) {
        super(context);
    }

    @Override
    protected void initAttributes() {
        @SuppressLint("InflateParams") View contentView = inflater.inflate(R.layout.module_window_window_mission_fly_settings, null);
        setContentView(contentView)
                .setWidth((int) (ScreenUtils.getScreenWidth() * 0.8))
                .setHeight((int) (ScreenUtils.getScreenHeight() * 0.8))
                .setFocusAndOutsideEnable(false)
                .setBackgroundDimEnable(true)
                .setDimValue(WindowDimConfig.DIM_3);
    }

    @Override
    protected void initViews(View view, MissionFlySettingsWindow popup) {
        super.initViews(view, popup);
        initSeekBarListener();
        checkCompareHeight();
        checkShootDistance();
        checkShotAngle();
        checkShotMode();
    }


    /**
     * 检测跨塔相对航点高度
     */
    private void checkCompareHeight() {
        mSeekCompareHeight.setProgress(MissionConfig.mission1_configure_above_height);
        mTvCompareHeight.setText(MissionConfig.mission1_configure_above_height + "米");
    }

    /**
     * 检测拍摄方式
     */
    private void checkShotMode() {
        switch (MissionConfig.mission1_configure_net_shoot_mode) {
            case 1:
                mTvShotMode.setText("前后拍摄");
                break;
            case 2:
                mTvShotMode.setText("左右拍摄");
                break;
            case 3:
                mTvShotMode.setText("前后左右拍摄");
                break;
        }
    }


    /**
     * 检测拍照距离
     */
    private void checkShootDistance() {
        mSeekShotDistance.setProgress(MissionConfig.mission1_configure_shoot_distance);
        tvModuleWindowShootDistance.setText(MissionConfig.mission1_configure_shoot_distance + "米");
        calShootVerAndHorDistance();
    }

    /**
     * 检测拍照点云台角度
     */
    private void checkShotAngle() {
        mSeekShotAngle.setProgress(Math.abs(MissionConfig.mission1_configure_shoot_gimbal_angle));
        mTvShotAngle.setText(String.valueOf(MissionConfig.mission1_configure_shoot_gimbal_angle));
        calShootVerAndHorDistance();
    }

    /**
     * 计算拍照点和杆塔水平和垂直距离
     */
    private void calShootVerAndHorDistance() {
        //无人机与杆塔的水平距离
        double hor_distance = Math.abs(Math.cos(Math.toRadians(MissionConfig.mission1_configure_shoot_gimbal_angle)) * MissionConfig.mission1_configure_shoot_distance);
        //无人机与杆塔的垂直距离
        double ver_distance = Math.abs(Math.sin(Math.toRadians(MissionConfig.mission1_configure_shoot_gimbal_angle)) * MissionConfig.mission1_configure_shoot_distance);
        mTvShootVerDistance.setText("拍照高程差：" + NumberUtils.format(ver_distance, 2));
        mTvShootHorDistance.setText("拍照水平差：" + NumberUtils.format(hor_distance, 2));
    }

    @OnClick({R2.id.tv_module_fragment_shot_mode, R2.id.cancel, R2.id.confirm})
    public void onClickListener(View v) {
        if (v.getId() == R.id.tv_module_fragment_shot_mode) {
            if (null == flyShotTypeChoiceWindow)
                flyShotTypeChoiceWindow = new FlyShootTypeChoiceWindow(mContext);
            flyShotTypeChoiceWindow.show(mTvShotMode, new ICallBack<Integer>() {
                @Override
                public void callback(Integer integer) {
                    MissionConfig.mission1_configure_net_shoot_mode = integer;
                    checkShotMode();
                }
            });

        } else if (v.getId() == R.id.cancel) {
            if (null != callBack) {
                callBack.callback(false);
            }
            dismiss();
        } else if (v.getId() == R.id.confirm) {
            if (null != callBack) {
                callBack.callback(true);
            }
            dismiss();
        }
    }


    private void initSeekBarListener() {
        mSeekCompareHeight.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
                mTvCompareHeight.setText(progress + "米");
            }

            @Override
            public void onStartTrackingTouch(android.widget.SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
                if (seekBar.getProgress() < 1) {
                    ToastUtils.showLong("跨塔高度最少为1米");
                    checkCompareHeight();
                    return;
                }
                MissionConfig.mission1_configure_above_height = seekBar.getProgress();
                checkCompareHeight();
            }
        });
        mSeekShotDistance.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
                tvModuleWindowShootDistance.setText(progress + "米");
            }

            @Override
            public void onStartTrackingTouch(android.widget.SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
                if (seekBar.getProgress() < 2) {
                    ToastUtils.showLong("拍照距离最少为2米");
                    checkShootDistance();
                    return;
                }
                MissionConfig.mission1_configure_shoot_distance = seekBar.getProgress();
                checkShootDistance();
            }
        });
        mSeekShotAngle.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
                mTvShotAngle.setText(String.valueOf(-progress));
            }

            @Override
            public void onStartTrackingTouch(android.widget.SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
                if (seekBar.getProgress() < 20) {
                    seekBar.setProgress(Math.abs(MissionConfig.mission1_configure_shoot_gimbal_angle));
                    ToastUtils.showLong("云台角度不可大于-20度");
                    return;
                }
                MissionConfig.mission1_configure_shoot_gimbal_angle = -seekBar.getProgress();
                checkShotAngle();
            }
        });

    }

    public void show(ICallBack<Boolean> callBack) {
        showAtAnchorView(ActivityUtils.getTopActivity().getWindow().getDecorView(), YGravity.CENTER, XGravity.CENTER);
        this.callBack = callBack;
    }

    @Override
    public void onDismiss() {
        callBack = null;
        flyShotTypeChoiceWindow = null;
        super.onDismiss();
    }
}
