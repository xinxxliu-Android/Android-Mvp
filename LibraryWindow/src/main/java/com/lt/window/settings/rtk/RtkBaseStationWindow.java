package com.lt.window.settings.rtk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.lt.config.WindowDimConfig;
import com.lt.dj.rtk.RtkBaseStationInfor;
import com.lt.func.ICallBack;
import com.lt.log.LogUtils;
import com.lt.window.R;
import com.lt.window.R2;
import com.lt.window.base.BasePopupWindow;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Rtk基站展示列表
 */
public class RtkBaseStationWindow extends BasePopupWindow<RtkBaseStationWindow> {

    @BindView(R2.id.rtk_recyclerview)
    RecyclerView recyclerView;


    @OnClick(R2.id.rtk_close_window)
    void closeWindow() {
        dismiss();
    }

    private RtkBaseStationAdapter adapter;

    private ICallBack<RtkBaseStationInfor> callBack;

    public RtkBaseStationWindow(Context context) {
        super(context);
    }

    @Override
    protected void initAttributes() {
        @SuppressLint("InflateParams") View contentView = inflater.inflate(R.layout.module_window_window_settings_base_station_rtk, null);
        setContentView(contentView)
                .setWidth((int) (ScreenUtils.getScreenWidth() * 0.6))
                .setHeight((int) (ScreenUtils.getScreenHeight() * 0.6))
                .setFocusAndOutsideEnable(false)
                .setBackgroundDimEnable(true)
                .setDimValue(WindowDimConfig.DIM_3);
    }

    @Override
    protected void initViews(View view, RtkBaseStationWindow popup) {
        super.initViews(view, popup);
        adapter = new RtkBaseStationAdapter(mContext);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        adapter.setClickListener(infor -> {
            LogUtils.d("选择使用名称为-->" + infor.getBaseStationName() + "<--的rtk基站，id-->" + infor.getBaseStationID());
            if (callBack == null)
                return;
            callBack.callback(infor);
            dismiss();
        });
    }

    public void show(List<RtkBaseStationInfor> rtkBaseStationInfors, ICallBack<RtkBaseStationInfor> callBack) {
        this.callBack = callBack;
        showAtAnchorView(ActivityUtils.getTopActivity().getWindow().getDecorView(), YGravity.CENTER, XGravity.CENTER);
        if (adapter != null) {
            adapter.setList(rtkBaseStationInfors);
        }
    }

    @Override
    public void release() {
        super.release();
        adapter.setClickListener(null);
        adapter = null;
        callBack = null;
        if (recyclerView != null) {
            recyclerView.setLayoutManager(null);
            recyclerView.setAdapter(null);
        }
    }
}
