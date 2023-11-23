package com.lt.config;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.lt.log.LogUtils;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * 目录配置常量类
 * 由 welcome模块初始化目录
 */
public final class PathConfig {
    public static final String TAG = "PathConfig";

    private static Application mContext;

    public synchronized static void install(Application mAppContext) {
        mContext = mAppContext;

        // app名称文件夹
        File appFolderPath = new File(findSDCardRoot(), AppUtils.getAppName());
        if (!appFolderPath.exists()) {
            appFolderPath.mkdirs();
        }
        LogUtils.d("file", "当前设备根目录为：" + appFolderPath.getAbsolutePath());

        Resources resources = mAppContext.getResources();
        if (resources == null) {
            return;
        }
        // 二级文件夹
        String[] secondFilePath = {resources.getString(R.string.update_version), resources.getString(R.string.flying_log),
                resources.getString(R.string.line_model), resources.getString(R.string.line_file), resources.getString(R.string.offline_map),
                resources.getString(R.string.hand_line), resources.getString(R.string.download_pic), resources.getString(R.string.lic)};
        for (String s : secondFilePath) {
            File secondFile = new File(appFolderPath.getAbsolutePath(), s);
            if (!secondFile.exists()) {
                secondFile.mkdirs();
                LogUtils.d("file", "创建文件夹：" + s);
            }
            if (s.equals(resources.getString(R.string.line_model))) {
                createLineModelFile(resources, secondFile.getAbsolutePath());
            } else if (s.equals(resources.getString(R.string.line_file))) {
                createLineFile(resources, secondFile.getAbsolutePath());
            }
        }

        Log.i(TAG, "install: 版本更新路径：" + getUpdateVersionPath());
        Log.i(TAG, "install: 飞行日志路径：" + getFlyingLogPath());

        Log.i(TAG, "install: 未加密航迹模版路径：" + getUnEncryptedModelPath());
        Log.i(TAG, "install: 已加密航迹模版路径：" + getEncryptedModelPath());

        Log.i(TAG, "install: 未加密航线文件json：" + getUnencryptedLineRouteJsonPath());
        Log.i(TAG, "install: 未加密航线文件kml：" + getUnencryptedLineRouteKmlPath());
        Log.i(TAG, "install: 未加密航线文件pwline：" + getUnencryptedLineRoutePwPath());
        Log.i(TAG, "install: 未加密航线文件 点云规划：" + getUnencryptedLineRoutePlanPath());

        Log.i(TAG, "install: 已加密航线文件json：" + getEncryptedLineRouteJsonPath());
        Log.i(TAG, "install: 已加密航线文件kml：" + getEncryptedLineRouteKmlPath());
        Log.i(TAG, "install: 已加密航线文件pwline：" + getEncryptedLineRoutePwPath());
        Log.i(TAG, "install: 已加密航线文件 点云规划：" + getEncryptedLineRoutePlanPath());

        Log.i(TAG, "install: 离线地图路径：" + getOfflineMapPath());
        Log.i(TAG, "install: 手采航线路径：" + getLineHandPath());
        Log.i(TAG, "install: 图片下载路径：" + getDownloadPicPath());
        Log.i(TAG, "install: lic文件路径：" + getLicFilePath());
    }

    /**
     * 创建航线文件子文件夹
     *
     * @param secondAbsolutePath 二级目录路径
     */
    private static void createLineFile(Resources resources, String secondAbsolutePath) {
        String[] threeFolder = {resources.getString(R.string.encrypted_line), resources.getString(R.string.un_encrypted_line), resources.getString(R.string.pvroute_line)};
        for (String s : threeFolder) {
            File lineFile = new File(secondAbsolutePath, s);
            if (!lineFile.exists()) {
                lineFile.mkdirs();
                LogUtils.d("file", "创建文件夹：" + s);
            }
            // 创建子文件
            createLineSecondFile(resources, lineFile.getAbsolutePath());
        }
    }

    /**
     * 创建航线文件三级文件夹
     *
     * @param threeAbsolutePath 三级目录路径
     */
    private static void createLineSecondFile(Resources resources, String threeAbsolutePath) {
        String[] fourFolder = {resources.getString(R.string.json_path), resources.getString(R.string.kml_path), resources.getString(R.string.excel_path), resources.getString(R.string.pw_path)};
        for (String s : fourFolder) {
            File fourLineFie = new File(threeAbsolutePath, s);
            if (!fourLineFie.exists()) {
                fourLineFie.mkdirs();
                LogUtils.d("file", "创建文件夹：" + s);
            }
            // 如果上级目录是光伏航线的话，则只需要创建json子目录文件夹即可。
            if (threeAbsolutePath.contains(resources.getString(R.string.pvroute_line)) && s.equals(resources.getString(R.string.json_path))) {
                break;
            }
        }
    }

    /**
     * 创建航线模板子文件夹
     *
     * @param secondAbsolutePath 二级目录路径
     */
    private static void createLineModelFile(Resources resources, String secondAbsolutePath) {
        String[] threeFolder = {resources.getString(R.string.encrypted_model), resources.getString(R.string.un_encrypted_model)};
        for (String s : threeFolder) {
            File lineModelFile = new File(secondAbsolutePath, s);
            if (!lineModelFile.exists()) {
                lineModelFile.mkdirs();
                LogUtils.d("file", "创建文件夹：" + s);
            }
        }
    }

    /**
     * 递归自己 找到根目录 避免 超时提示错误
     * <p>
     * 20220921 修改获取方式  部分设备 根目录名字 不是 0 所以 低版本 使用Android开放api获取路径
     *
     * @return t
     */
    @SuppressWarnings("deprecation")
    private static File findSDCardRoot() {
        /**
         * 获取所有外置存储的路径，包括内置SD卡路径和外置SD卡路径
         * 内置 SD卡路径根目录为  /storage/emulated/0/
         * 外置 SD卡路径根目录为 /storage/CE56-5647/
         * 注意:
         *  1. 外置 SDk卡根目录中 /storage/xxxx/ 中的 xxxx部分，由于不同的设备或者厂商的修改，
         *     可能会显示出不同的名称，不一定是 CE56-5647.
         *     CE56-5647 是大疆御3 带屏遥控器上测试出的名称，其他设备需自行测试
         *  2. 如果设备上有多个SD卡或其他存储设备，则都会读取出来，如果没有，只会有内置SD卡的路径
         *     此处暂时处理外置一张SD卡的情况
         *  3. 如果是重庆地区，因为使用 内存卡 进行 VPN加密认证进行推流，卡空间很小，所以不在往SD上写数据
         *     存储到机身上。
         */
        /**
         * 注意:带屏遥控器可以在系统重设置默认存储路径，如果将默认存储路径修改为SD卡，则SD卡为第一外置存储，即：
         * 重庆地区插入SD卡并设置为默认存储后，代码判断不执行 if 部分，执行 else 部分，但是路径依然为SD路径
         */
        File[] directory = PluginConfig.mAppContext.getExternalFilesDirs(null);
        //判断是否有外置SD卡,当有两个及以上外置存储路径时，认为有外置SD卡,重庆不使用SD卡
        if (directory.length >= 2 && !BuildConfig.CHANNEL_KEY.equals("重庆")) {
            for (File file : directory) {
                if (file.getAbsolutePath().contains("/storage/emulated/0/")) {
                    continue;
                } else {
                    /**
                     * 如果遇到多个外置sd卡路径，默认返回第一个
                     * 此处路径为 /storage/CE56-5647/Android/data/com.hanchuang.dronesmart/files/mounted
                     * 此路径为大疆御3带屏遥控器插上sd卡后的路径
                     */
                    String absolutePath = file.getAbsolutePath();
                    //String replace = absolutePath.replace("/Android/data/com.hanchuang.dronesmart/files/mounted", "/");
                    //file = new File(replace);
                    /**
                     * 此处路径为 /storage/xxxx (外置SD卡根目录)
                     */
                    return file;
                }
            }
        } else {
            /**
             * 如果没有外置SD卡，则返回内置SD卡的路 需要做版本兼容，android 10 以下和 android 10及以上
             * 此处路径为：/storage/emulated/0/Android/data/com.hanchuang.dronesmart/files/
             */
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                //包名/file 路径
                File filesDir = PluginConfig.mAppContext.getExternalFilesDir(null);
                String filePath = filesDir.getAbsolutePath();
                //String replace = filePath.replace("/Android/data/com.hanchuang.dronesmart/files", "/");
                File file = new File(filePath);
                return file;
            }
            /**
             * 此处路径为 /storage/emulated/0 (外置存储根目录)
             */
            return Environment.getExternalStorageDirectory();
        }
        return null;
    }

    /**
     * 根目录路径
     */
    public static String getAppRootFolderPath() {
        return new File(findSDCardRoot(), AppUtils.getAppName()).getAbsolutePath();
    }

    /**
     * 版本更新路径
     */
    public static String getUpdateVersionPath() {
        if (mContext == null) {
            return "";
        }
        Resources resources = mContext.getResources();
        if (resources == null) {
            return "";
        }
        return new File(getAppRootFolderPath(), resources.getString(R.string.update_version)).getAbsolutePath();
    }

    /**
     * 飞行日志路径
     *
     * @return
     */
    public static String getFlyingLogPath() {
        if (mContext == null) {
            return "";
        }
        Resources resources = mContext.getResources();
        if (resources == null) {
            return "";
        }
        return new File(getAppRootFolderPath(), resources.getString(R.string.flying_log)).getAbsolutePath();
    }

    /**
     * 未加密模版路径
     */
    public static String getUnEncryptedModelPath() {
        if (mContext == null) {
            return "";
        }
        Resources resources = mContext.getResources();
        if (resources == null) {
            return "";
        }
        return new File(new File(getAppRootFolderPath(), resources.getString(R.string.line_model)).getAbsolutePath(), resources.getString(R.string.un_encrypted_model)).getAbsolutePath();
    }

    /**
     * 已加密模版路径
     */
    public static String getEncryptedModelPath() {
        if (mContext == null) {
            return "";
        }
        Resources resources = mContext.getResources();
        if (resources == null) {
            return "";
        }
        return new File(new File(getAppRootFolderPath(), resources.getString(R.string.line_model)).getAbsolutePath(), resources.getString(R.string.encrypted_model)).getAbsolutePath();
    }

    /**
     * 光伏Json航线文件路径
     */
    public static String getPvRouteJsonLinePath() {
        if (mContext == null) {
            return "";
        }
        Resources resources = mContext.getResources();
        if (resources == null) {
            return "";
        }
        return new File(new File(new File(getAppRootFolderPath(), resources.getString(R.string.line_file)).getAbsolutePath(), resources.getString(R.string.pvroute_line)).getAbsolutePath(), resources.getString(R.string.json_path)).getAbsolutePath();
    }

    /**
     * 未加密航线Json航线文件路径
     */
    public static String getUnencryptedLineRouteJsonPath() {
        if (mContext == null) {
            return "";
        }
        Resources resources = mContext.getResources();
        if (resources == null) {
            return "";
        }
        return new File(new File(new File(getAppRootFolderPath(), resources.getString(R.string.line_file)).getAbsolutePath(), resources.getString(R.string.un_encrypted_line)).getAbsolutePath(), resources.getString(R.string.json_path)).getAbsolutePath();
    }

    /**
     * 未加密航线Kml航线文件路径
     */
    public static String getUnencryptedLineRouteKmlPath() {
        if (mContext == null) {
            return "";
        }
        Resources resources = mContext.getResources();
        if (resources == null) {
            return "";
        }
        return new File(new File(new File(getAppRootFolderPath(), resources.getString(R.string.line_file)).getAbsolutePath(), resources.getString(R.string.un_encrypted_line)).getAbsolutePath(), resources.getString(R.string.kml_path)).getAbsolutePath();
    }

    /**
     * 未加密航线pwline航线文件路径
     */
    public static String getUnencryptedLineRoutePwPath() {
        if (mContext == null) {
            return "";
        }
        Resources resources = mContext.getResources();
        if (resources == null) {
            return "";
        }
        return new File(new File(new File(getAppRootFolderPath(), resources.getString(R.string.line_file)).getAbsolutePath(), resources.getString(R.string.un_encrypted_line)).getAbsolutePath(), resources.getString(R.string.pw_path)).getAbsolutePath();
    }

    /**
     * 未加密航线excel航线文件路径
     */
    public static String getUnencryptedLineRouteExcelPath() {
        if (mContext == null) {
            return "";
        }
        Resources resources = mContext.getResources();
        if (resources == null) {
            return "";
        }
        return new File(new File(new File(getAppRootFolderPath(), resources.getString(R.string.line_file)).getAbsolutePath(), resources.getString(R.string.un_encrypted_line)).getAbsolutePath(), resources.getString(R.string.excel_path)).getAbsolutePath();
    }

    /**
     * 未加密航线点云规划航线文件路径
     */
    public static String getUnencryptedLineRoutePlanPath() {
        if (mContext == null) {
            return "";
        }
        Resources resources = mContext.getResources();
        if (resources == null) {
            return "";
        }
        return new File(new File(new File(getAppRootFolderPath(), resources.getString(R.string.line_file)).getAbsolutePath(), resources.getString(R.string.un_encrypted_line)).getAbsolutePath(), resources.getString(R.string.routerplan_path)).getAbsolutePath();
    }

    /**
     * 已加密航线Json航线文件路径
     */
    public static String getEncryptedLineRouteJsonPath() {
        if (mContext == null) {
            return "";
        }
        Resources resources = mContext.getResources();
        if (resources == null) {
            return "";
        }
        return new File(new File(new File(getAppRootFolderPath(), resources.getString(R.string.line_file)).getAbsolutePath(), resources.getString(R.string.encrypted_line)).getAbsolutePath(), resources.getString(R.string.json_path)).getAbsolutePath();
    }

    /**
     * 已加密航线Kml航线文件路径
     */
    public static String getEncryptedLineRouteKmlPath() {
        if (mContext == null) {
            return "";
        }
        Resources resources = mContext.getResources();
        if (resources == null) {
            return "";
        }
        return new File(new File(new File(getAppRootFolderPath(), resources.getString(R.string.line_file)).getAbsolutePath(), resources.getString(R.string.encrypted_line)).getAbsolutePath(), resources.getString(R.string.kml_path)).getAbsolutePath();
    }

    /**
     * 已加密航线pwline航线文件路径
     */
    public static String getEncryptedLineRoutePwPath() {
        if (mContext == null) {
            return "";
        }
        Resources resources = mContext.getResources();
        if (resources == null) {
            return "";
        }
        return new File(new File(new File(getAppRootFolderPath(), resources.getString(R.string.line_file)).getAbsolutePath(), resources.getString(R.string.encrypted_line)).getAbsolutePath(), resources.getString(R.string.pw_path)).getAbsolutePath();
    }

    /**
     * 已加密航线excel航线文件路径
     */
    public static String getEncryptedLineRouteExcelPath() {
        if (mContext == null) {
            return "";
        }
        Resources resources = mContext.getResources();
        if (resources == null) {
            return "";
        }
        return new File(new File(new File(getAppRootFolderPath(), resources.getString(R.string.line_file)).getAbsolutePath(), resources.getString(R.string.encrypted_line)).getAbsolutePath(), resources.getString(R.string.excel_path)).getAbsolutePath();
    }

    /**
     * 已加密航线点云规划航线文件路径
     */
    public static String getEncryptedLineRoutePlanPath() {
        if (mContext == null) {
            return "";
        }
        Resources resources = mContext.getResources();
        if (resources == null) {
            return "";
        }
        return new File(new File(new File(getAppRootFolderPath(), resources.getString(R.string.line_file)).getAbsolutePath(), resources.getString(R.string.encrypted_line)).getAbsolutePath(), resources.getString(R.string.routerplan_path)).getAbsolutePath();
    }

    /**
     * 离线地图路径
     */
    public static String getOfflineMapPath() {
        if (mContext == null) {
            return "";
        }
        Resources resources = mContext.getResources();
        if (resources == null) {
            return "";
        }
        return new File(getAppRootFolderPath(), resources.getString(R.string.offline_map)).getAbsolutePath();
    }

    /**
     * 手采航线路径
     */
    public static String getLineHandPath() {
        if (mContext == null) {
            return "";
        }
        Resources resources = mContext.getResources();
        if (resources == null) {
            return "";
        }
        return new File(getAppRootFolderPath(), resources.getString(R.string.hand_line)).getAbsolutePath();
    }

    /**
     * 图片下载路径
     */
    public static String getDownloadPicPath() {
        if (mContext == null) {
            return "";
        }
        Resources resources = mContext.getResources();
        if (resources == null) {
            return "";
        }
        return new File(getAppRootFolderPath(), resources.getString(R.string.download_pic)).getAbsolutePath();
    }

    /**
     * lic文件路径
     */
    public static String getLicFilePath() {
        if (mContext == null) {
            return "";
        }
        Resources resources = mContext.getResources();
        if (resources == null) {
            return "";
        }
        return new File(getAppRootFolderPath(), resources.getString(R.string.lic)).getAbsolutePath();
    }

    /**
     * 图片下载路径
     * 目录结构调整为 :
     * 有线路名称、杆塔号时：图片下载/线路名称/巡检模式/时间/杆塔号/图片
     * 无线路名称、无杆塔号时：图片下载/本地巡检或者无工单巡检/巡检模式/时间/图片
     *
     * @param createTime 任务时间
     * @param imageName  图片名称
     * @param imagePath  图片路径
     *                   格式: 线路名称_巡检模式_杆塔号  或
     *                   本地巡检/无工单巡检_巡检模式（本地飞行时）
     */
    @SuppressLint("SimpleDateFormat")
    public static String getMissionPicPath(String createTime, String imageName, String imagePath) {
        File parent;
        if (imagePath.contains("_")) {
            String time = TimeUtils.millis2String(Long.parseLong(createTime), new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒"));
            //截取imagePath，取得层级目录
            String[] s = imagePath.split("_");
            if (s.length >= 3) {
                parent = new File(getDownloadPicPath(), s[1] + File.separator + s[0] + File.separator + time + File.separator + s[2]);
            } else {
                parent = new File(getDownloadPicPath(), s[1] + File.separator + s[0] + File.separator + time);
            }
            parent.mkdirs();
            String path = new File(parent, imageName).getAbsolutePath();
            return path;
        } else {
            LogUtils.e("图片路径格式错误:imagePath = " + imagePath);
            return "图片路径格式错误:imagePath = " + imagePath;
        }
    }
}
