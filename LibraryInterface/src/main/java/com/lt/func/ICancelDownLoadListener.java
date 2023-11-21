package com.lt.func;

/**
 * Create by wangpeng in 2023/8/30.
 * Describe: 取消下载监听
 */
public interface ICancelDownLoadListener extends IBaseDownloadFileProListener{
    //取消成功
    void cancelSuccess();
}
