package com.lt.func;

import android.graphics.Bitmap;

/**
 * 下载相机文件进度监听
 */
public interface IFileDownloadPreListener extends IBaseDownloadFileProListener{
    /**
     * 当前索引的文件下载完毕
     * @param index 当前文件 所对应的 航点的索引
     */
    void downloadSuccess(int index, Bitmap bitmap);

}
