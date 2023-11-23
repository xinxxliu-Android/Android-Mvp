package com.lt.window.settings.gimbal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import com.lt.config.WindowDimConfig;
import com.lt.func.ICallBack;
import com.lt.window.R;
import com.lt.window.R2;
import com.lt.window.base.BasePopupWindow;

import butterknife.OnClick;

/**
 * 云台工作模式Window
 */
public final class GimbalTypeChoiceWindow extends BasePopupWindow<GimbalTypeChoiceWindow> {

    private ICallBack<Integer> callback;

    public GimbalTypeChoiceWindow(Context context) {
        super(context);
    }

    @Override
    protected void initAttributes() {
        @SuppressLint("InflateParams") View contentView = inflater.inflate(R.layout.module_window_window_settings_choice_gimbal, null);
        setContentView(contentView)
                .setFocusAndOutsideEnable(true)
                .setBackgroundDimEnable(true)
                .setDimValue(WindowDimConfig.DIM_3);
    }

    public void show(View parent, ICallBack<Integer> callBack) {
        this.callback = callBack;
        showAsDropDown(parent);
    }

    @Override
    public void onDismiss() {
        this.callback = null;
        super.onDismiss();
    }

    @OnClick({ R2.id.type_1, R2.id.type_2, R2.id.type_3})
    void choiceType(View v) {
        Object tag = v.getTag();
        int position = Integer.parseInt(tag.toString());
        callback.callback(position);
        dismiss();
    }
}
