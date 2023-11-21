package com.example.base;

import android.os.Build;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.rxjava3.core.Flowable;

/**
 * 下载并升级APP
 */
public class DownloadHelper implements IBaseHelper<IBaseView> {

    private IBaseView mView;

    @Override
    public void attach(IBaseView iBaseView) {
        this.mView = iBaseView;
    }

    public strictfp void download(AppUpdateResponse response) {
        Flowable.just(1).map(it -> {
                    mView.startLoading();
                    String apkPath = PathConfig.UPDATE_DIRECTORY + File.separator + "Upgrade" + response.versionCode + ".apk";
                    File apkFile = new File(apkPath);
                    if (apkFile.exists()){
                        apkFile.delete();
                    }
                    URL url = new URL(BaseUrlConfig.BASE_URL + (response.filePath.startsWith("/") ? response.filePath.substring(1) : response.filePath));
                    mView.updateLoading("安装包正在下载");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    //处理添加请求头
                    if (!StringUtils.isTrimEmpty(UserConfig.SignToken))
                        connection.setRequestProperty("sign", UserConfig.SignToken);
                    //如果登录平台账号，则所有接口添加 token到请求头
                    if (!StringUtils.isTrimEmpty(UserConfig.token)) {
                        connection.setRequestProperty("token", UserConfig.token);
                        connection.setRequestProperty("Allcore-Auth", UserConfig.token);
                    }

                    //如果已登陆飞手，则所有接口添加 飞手guid到请求头
                    if (!StringUtils.isTrimEmpty(UserConfig.workerUuid))
                        connection.setRequestProperty("workerGuid", UserConfig.workerUuid);

                    //如果已连接飞机，则所有接口添加 飞机serialNumber
                    if (!StringUtils.isTrimEmpty(UserConfig.deviceUuid))
                        connection.setRequestProperty("deviceGuid", UserConfig.deviceUuid);

                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode != 200) {
                        connection.disconnect();
                        throw new HttpException(responseCode, connection.getResponseMessage());
                    }
                    //总长
                    long contentLength = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ? connection.getContentLengthLong() : connection.getContentLength();
                    //说明文件已下载完毕了
                    long length = apkFile.length();
                    if (apkFile.exists() && length >= contentLength && contentLength > 1) {
                        return apkPath;
                    }
                    InputStream is = new BufferedInputStream(connection.getInputStream());
                    //断点下载 跳过这么多字节再读
                    if (apkFile.exists() && length > 0)
                        is.skip(length);
                    OutputStream fos = new BufferedOutputStream(new FileOutputStream(apkFile, true));
                    //1m缓存
                    byte[] bytes = new byte[1024 * 1024];
                    int len = -1;
                    long count = length;
                    while ((len = is.read(bytes)) != -1) {
                        fos.write(bytes, 0, len);
                        fos.flush();
                        count += len;
                        int v = contentLength <= 0 ? 0 : (int) (count * 1.0 / contentLength * 100);
                        mView.updateLoading("当前下载进度：" + v + "%");
                    }
                    fos.close();
                    is.close();
                    connection.disconnect();
                    return apkPath;
                }).subscribeOn(RxSchedulers.request()).observeOn(RxSchedulers.main())
                .subscribe(it -> {
                    mView.updateLoading("下载完成");
                    mView.postDelay(() -> installApp(it), 2000);
                }, it -> {
                    mView.postDelay(() -> mView.updateLoading("下载失败:" + it.getMessage()), 2000);
                    LogUtils.e(it.getMessage());
                });
    }

    /**
     * 安装apk
     */
    private void installApp(String installPath) {
        mView.endLoading();
        AppUtils.installApp(installPath);
    }

    @Override
    public void detach() {
        this.mView = null;
    }
}
