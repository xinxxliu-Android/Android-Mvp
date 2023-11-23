package com.lt.window.settings.rtk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import com.lt.config.PluginConfig;
import com.lt.config.WindowDimConfig;
import com.lt.func.ICallBack;
import com.lt.widget.v.TextView;
import com.lt.window.R;
import com.lt.window.R2;
import com.lt.window.base.BasePopupWindow;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * rtk类型选择弹窗
 */
public final class RtkTypeChoiceWindow extends BasePopupWindow<RtkTypeChoiceWindow> {
    private ICallBack<Integer> callback;

    @BindView(R2.id.tv_3)
    TextView tv_3;
    @BindView(R2.id.type_3)
    TextView type_3;

    public RtkTypeChoiceWindow(Context context) {
        super(context);
    }

    @Override
    protected void initAttributes() {
        @SuppressLint("InflateParams") View contentView = inflater.inflate(R.layout.module_window_window_settings_choicertk, null);
        setContentView(contentView)
                .setFocusAndOutsideEnable(true)
                .setBackgroundDimEnable(true)
                .setFocusable(true)
                .setDimValue(WindowDimConfig.DIM_3);
    }

    @Override
    protected void initViews(View view, RtkTypeChoiceWindow popup) {
        super.initViews(view, popup);
        if (PluginConfig.getDjiVersion() == 4){
            tv_3.setVisibility(View.GONE);
            type_3.setVisibility(View.GONE);
        }
    }

    public void show(View parent, ICallBack<Integer> callBack){
        this.callback = callBack;
        showAsDropDown(parent);
    }

    @Override
    public void onDismiss() {
        this.callback = null;
        super.onDismiss();
    }

    @OnClick({R2.id.type_0,R2.id.type_1, R2.id.type_2,R2.id.type_3,R2.id.type_4})
    void choiceType(View v){
        Object tag = v.getTag();
        int position = Integer.parseInt(tag.toString());
        callback.callback(position);
        dismiss();
    }
}
