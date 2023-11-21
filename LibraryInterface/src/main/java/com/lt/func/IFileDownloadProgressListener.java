package com.lt.func;

/**
 * 下载相机文件进度监听
 */
public interface IFileDownloadProgressListener extends IBaseDownloadFileProListener{
    /**
     * 开始下载
     */
    void onStartFile();
    /**
     * 新的文件下载开始
     * 可以用该函数进行计数
     * @param index 当前下载的图片的索引
     */
    void onNewFile(int index);

    /**
     * 当前索引文件下载进度
     * @param index     下标
     * @param percent   进度值
     */
    void progress(int index, int percent);
    /**
     * 当前索引的文件下载完毕
     * @param index 当前文件 所对应的 航点的索引
     */
    void downloadSuccess(int index);

    /**
     * 正常结束下载
     */
    void onComplete();
}
