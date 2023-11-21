//package com.lt.utils;
//
//import android.os.Environment;
//
//import java.io.File;
//
///**
// * 软件SD 目录管理类
// */
//public class AppFileDirUtils {
//
//    private static AppFileDirUtils INSTANCE;
//
//    private static final String ROOT_DIR = Environment.getExternalStorageDirectory().getAbsoluteFile().toString();
//    private static final String ROOT_FILE_DIR = "SmartDroneFile";
//    private static final String ROOT_MAP_DIR = "SmartDroneMap";
//    private static final String ROOT_DJI_DIR = "DJI";
//    private static final String FILE_LOGS_DIR = "logs";
//    private static final String FILE_MEDIA_DIR = "media(媒体)";
//
//    private AppFileDirUtils() {
//    }
//
//    /**
//     * 创建SmartDroneMap文件夹并返回绝对路径
//     *
//     * @return 绝对路径
//     */
//    public static final String getPhoneMapDir() {
//        File file = new File(ROOT_DIR + File.separator + ROOT_MAP_DIR);
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        return file.getAbsolutePath();
//    }
//
//    /**
//     * 创建SmartDroneFile文件夹并返回绝对路径
//     *
//     * @return 绝对路径
//     */
//    public static String getPhoneFileDir() {
//        File file = new File(ROOT_DIR + File.separator + ROOT_FILE_DIR);
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        return file.getAbsolutePath();
//    }
//
//    /**
//     * 创建DJI文件夹并返回绝对路径
//     *
//     * @return 绝对路径
//     */
//    public static String getDJIDirectory() {
//        File file = new File(ROOT_DIR + File.separator + ROOT_DJI_DIR);
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        return file.getAbsolutePath();
//    }
//
//    /**
//     * 创建logs日志文件夹并返回绝对路径
//     *
//     * @return 绝对路径
//     */
//    public static String getLogFileDir() {
//        File file = new File(getPhoneFileDir() + File.separator + FILE_LOGS_DIR);
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        return file.getAbsolutePath();
//    }
//
//    /**
//     * 创建media(媒体)文件夹并返回绝对路径
//     *
//     * @return 绝对路径
//     */
//    public static String getDownloadMediaDir() {
//        File file = new File(getPhoneFileDir() + File.separator + FILE_MEDIA_DIR);
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        return file.getAbsolutePath();
//    }
//
//    public static AppFileDirUtils getInstance() {
//        if (null == INSTANCE) {
//            synchronized (AppFileDirUtils.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new AppFileDirUtils();
//                }
//            }
//        }
//        return INSTANCE;
//    }
//}
