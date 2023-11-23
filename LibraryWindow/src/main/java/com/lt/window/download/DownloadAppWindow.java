package com.lt.window.download;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.blankj.utilcode.util.ScreenUtils;
import com.lt.func.ICallBack;
import com.lt.widget.v.Button;
import com.lt.widget.v.TextView;
import com.lt.window.BuildConfig;
import com.lt.window.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * app检测更新弹窗
 */
public class DownloadAppWindow extends DialogFragment {
    /**
     * 更新信息
     */
    TextView info;
    TextView title;
    Button confirm,cancel;

    private Dialog dialog;

    private String versionName,warnInfo;

    private ICallBack<Boolean> callback;

    public DownloadAppWindow(String versionName, String warnInfo, ICallBack<Boolean> callBack) {
        this.versionName = versionName;
        this.warnInfo = warnInfo;
        this.callback = callBack;
    }


    /**
     * 对于dialog的style设置和属性设置，在此完成
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置返回键无效
        //设置点击外部不消失
        setCancelable(false);
        setStyle(STYLE_NORMAL,R.style.AppUpDateDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.module_window_window_download, null);
        info = contentView.findViewById(R.id.info);
        title = contentView.findViewById(R.id.title);
        confirm = contentView.findViewById(R.id.confirm);
        cancel = contentView.findViewById(R.id.cancel);
        cancel.setVisibility(!BuildConfig.CHANNEL_KEY.equals("重庆") ? View.GONE : View.VISIBLE);
        info.setText(warnInfo);
        title.setText(versionName);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.callback(true);
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.callback(false);
                dismiss();
            }
        });
        return contentView;
    }


    @Override
    public void onStart() {
        super.onStart();
        //设置dialog的大小
        WindowManager.LayoutParams attributes = getDialog().getWindow().getAttributes();
        attributes.width = (int)( ScreenUtils.getScreenWidth()*0.7);
        getDialog().getWindow().setAttributes(attributes);
    }

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, "DownloadAppWindow");

    }

}
