package com.lt.window;

public interface ILoadingDialog {
     int LOADING_ERROR = R.string.library_window_loading_error;
    int LOADING_SUCCESS = R.string.library_window_loading_success;
    /**
     * 显示loading
     * @return
     */
    ILoadingDialog showLoading();

    /**
     * 消失loading
     * @return
     */
    ILoadingDialog dismissLoading();

    /**
     * 更新loading文字 可选
     * @param charSequence  更新到的文字
     * @return
     */
    ILoadingDialog updateLoading(CharSequence charSequence);
}
