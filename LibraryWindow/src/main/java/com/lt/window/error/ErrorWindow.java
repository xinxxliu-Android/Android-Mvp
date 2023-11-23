package com.lt.window.error;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.lt.config.WindowDimConfig;
import com.lt.utils.RxSchedulers;
import com.lt.widget.v.TextView;
import com.lt.window.R;
import com.lt.window.R2;
import com.lt.window.base.BasePopupWindow;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.util.List;

import butterknife.OnClick;
import io.reactivex.rxjava3.core.Flowable;

/**
 * 全局错误信息提示弹窗
 *
 * @author Y
 */
public class ErrorWindow extends BasePopupWindow<ErrorWindow> {

    /**
     * 错误信息展示
     */
    TextView mErrorMsg;
    /**
     * 列表展示错误信息
     */
    RecyclerView mErrorMsgList;

    private StringAdapter adapter;
    private LinearLayoutManager manager;

    /**
     * 确认按钮
     */
    @OnClick(R2.id.confirm)
    void OnClickBtnListener(View v) {
        dismiss();
    }

    public ErrorWindow(Context context) {
        super(context);
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        @SuppressLint("InflateParams") View contentView = inflater.inflate(R.layout.module_window_window_error_window, null);
        setContentView(contentView)
                .setWidth((int) (ScreenUtils.getScreenWidth() * 0.7))
                .setHeight((int) (ScreenUtils.getScreenHeight() * 0.7))
                .setOutsideTouchable(false)
                .setFocusAndOutsideEnable(false)
                .setBackgroundDimEnable(true)
                .setDimValue(WindowDimConfig.DIM_3);
        mErrorMsg = contentView.findViewById(R.id.error_msg);
        mErrorMsgList = contentView.findViewById(R.id.error_msg_list);
    }

    public void show(String errorMsg) {
        Flowable.just(1)
                .observeOn(RxSchedulers.main())
                .subscribe(it -> {
                    showAtAnchorView(ActivityUtils.getTopActivity().getWindow().getDecorView(), YGravity.CENTER, XGravity.CENTER);
                    mErrorMsg.setText(errorMsg);
                    mErrorMsgList.setVisibility(View.GONE);
                });
    }

    public void show(List<String> errorMsg) {
        Flowable.fromCallable(() -> {
                    adapter = new StringAdapter(mContext, errorMsg);
                    manager = new LinearLayoutManager(mContext);
                    return true;
                })
                .observeOn(RxSchedulers.main())
                .subscribe(it -> {
                    showAtAnchorView(ActivityUtils.getTopActivity().getWindow().getDecorView(), YGravity.CENTER, XGravity.CENTER);
                    mErrorMsgList.setLayoutManager(manager);
                    mErrorMsgList.setAdapter(adapter);
                    mErrorMsg.setVisibility(View.GONE);
                });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        adapter = null;
        manager = null;
    }
}
