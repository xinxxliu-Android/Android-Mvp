package com.lt.window.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.lt.func.IReleasable;
import com.zyyoona7.popup.BasePopup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 抽象 window
 * 使用第三方    easyPopup 实现
 *
 * @param <T>
 */
@SuppressWarnings("unused")
public abstract class BasePopupWindow<T extends BasePopupWindow<?>> extends BasePopup<T>
        implements IReleasable {
    protected Context mContext;
    protected Unbinder unbinder;
    protected LayoutInflater inflater;

    protected BasePopupWindow(Context context) {
        setContext(context);
        this.mContext = context;
        inflater = mContext.getSystemService(LayoutInflater.class);
    }

    /**
     * 初始化 View
     *
     * @param view  v
     * @param popup p
     */
    @Override
    protected void initViews(View view, T popup) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initAttributes() {
        PopupWindow popupWindow = getPopupWindow();
        hideBottomUIMenuForPopupWindow(popupWindow);
    }

    @Override
    public boolean isShowing() {
        return super.isShowing();
    }

    /**
     * 影藏PopupWindow页面弹出时的虚拟按键
     */
    @SuppressWarnings("deprecation")
    private void hideBottomUIMenuForPopupWindow(final PopupWindow popupWindow) {
        if (popupWindow != null && popupWindow.getContentView() != null) {
            popupWindow.getContentView().setOnSystemUiVisibilityChangeListener(visibility -> {
                //        //保持布局状态
                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        //布局位于状态栏下方
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        //全屏
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        //隐藏导航栏
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

                uiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
                popupWindow.getContentView().setSystemUiVisibility(uiOptions);
            });
        }
    }

    @Override
    public void release() {
        if (unbinder != null)
            unbinder.unbind();
        mContext = null;
    }
}
