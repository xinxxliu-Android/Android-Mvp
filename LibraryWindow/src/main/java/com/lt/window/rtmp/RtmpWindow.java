package com.lt.window.rtmp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.lt.config.SPConfig;
import com.lt.func.ICallBack;
import com.lt.log.LogUtils;
import com.lt.utils.SPEncrypted;
import com.lt.widget.v.EditText;
import com.lt.window.R;
import com.lt.window.R2;
import com.lt.window.base.BasePopupWindow;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 直播推流 开启弹窗
 */
@SuppressLint("NonConstantResourceId")
public class RtmpWindow extends BasePopupWindow<RtmpWindow> {

    @BindView(R2.id.radio_group)
    RadioGroup radioGroup;
    /**
     * 屏幕推流选择框
     */
    @BindView(R2.id.screen_stream_rb)
    RadioButton screenStream;
    /**
     * FPV推流选择框
     */
    @BindView(R2.id.fpv_stream_rb)
    RadioButton fpvStream;
    /**
     * 推流地址输入框
     */
    @BindView(R2.id.input_url)
    EditText inputUrl;
    /**
     * 推流地址
     */
    static String pushUrl;
    /**
     * 此处只回调 url地址
     */
    ICallBack<String> callBack;

    public RtmpWindow(Context context) {
        super(context);
    }

    @OnCheckedChanged({R2.id.screen_stream_rb, R2.id.fpv_stream_rb})
    void chooseStreamType(CompoundButton view, boolean isChecked) {
        if (view.getId() == R.id.screen_stream_rb) {
            if (isChecked)
                SPEncrypted.getInstance().put(SPConfig.RtmpStream.RTMP_STREAM_KEY, SPConfig.RtmpStream.RTMP_STREAM_SCREEN);
        } else if (view.getId() == R.id.fpv_stream_rb) {
            if (isChecked)
                SPEncrypted.getInstance().put(SPConfig.RtmpStream.RTMP_STREAM_KEY, SPConfig.RtmpStream.RTMP_STREAM_FPV);
        } else {
            ToastUtils.showShort("未找到响应的推流方式");
        }
    }

    @OnTextChanged(R2.id.input_url)
    void inputUrlChanged(CharSequence inputUrl) {
        pushUrl = inputUrl == null ? pushUrl : inputUrl.toString();
    }

    @OnClick(R2.id.close)
    void closeWindow() {
        dismiss();
    }

    @OnClick(R2.id.start)
    void startClick() {
        LogUtils.click("开始推流");
        if (pushUrl.isEmpty()) {
            ToastUtils.showLong("请稍后再试，正在加载直播地址。");
            return;
        }
        callBack.callback(pushUrl);
    }

    /**
     * 显示弹窗确认推流
     *
     * @param callBack url经过编辑的地址回调 点击开始直播时回调
     * @param url      推流地址
     */
    public void showWindow(String url, ICallBack<String> callBack) {
        this.callBack = callBack;
        pushUrl = StringUtils.isTrimEmpty(url) ? "" : url;
        showAtAnchorView(ActivityUtils.getTopActivity().getWindow().getDecorView(), YGravity.CENTER, XGravity.CENTER);
        inputUrl.setFocusable(true);
        inputUrl.requestFocus();
        inputUrl.setFocusableInTouchMode(true);
        inputUrl.setText(url);
        inputUrl.setSelection(url.length());
        String rtmpType = SPEncrypted.getInstance().getString(SPConfig.RtmpStream.RTMP_STREAM_KEY, SPConfig.RtmpStream.RTMP_STREAM_SCREEN);
        switch (rtmpType) {
            case SPConfig.RtmpStream.RTMP_STREAM_SCREEN:
                screenStream.setChecked(true);
                break;
            case SPConfig.RtmpStream.RTMP_STREAM_FPV:
                fpvStream.setChecked(true);
                break;
        }
    }

    public void updateUrl(String url) {
        pushUrl = StringUtils.isTrimEmpty(url) ? "" : url;
        if (inputUrl != null)
            inputUrl.setText(url);
        inputUrl.setSelection(url.length());
    }

    @Override
    protected void initAttributes() {
        LayoutInflater inflater = Utils.getApp().getSystemService(LayoutInflater.class);
        setContentView(inflater.inflate(R.layout.module_window_window_rtmp, null))
                .setOutsideTouchable(false)
                //支持输入更改
                .setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED)
//                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .setWidth((int) (ScreenUtils.getScreenWidth() * 0.7))
                .setFocusable(true)
                .setFocusAndOutsideEnable(false);

    }

    @Override
    public void dismiss() {
        callBack = null;
        super.dismiss();
    }
}
