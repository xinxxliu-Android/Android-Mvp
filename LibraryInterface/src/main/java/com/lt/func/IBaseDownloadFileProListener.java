package com.lt.func;

/**
 * Create by wangpeng in 2023/8/29.
 * Describe:
 */
public interface IBaseDownloadFileProListener {
    /**
     * 下载文件出错
     * @param t 异常信息
     */
    void onError(Throwable t);
}
