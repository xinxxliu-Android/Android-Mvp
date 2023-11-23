package com.lt.config;

import com.blankj.utilcode.util.ThreadUtils;
import com.lt.utils.SPEncrypted;
import com.lt.utils.thread.ThreadPoolUtils;

/**
 * 图片配置
 * 包含：
 * 1 是否开启图片压缩-默认开启
 * 2 压缩倍率 默认 20
 */
public class PhotoConfig {
    /**
     * 压缩开关
     */
    public static boolean compressEnable = true;
    /**
     * 压缩倍率
     */
    public static int compressLevel = 80;

    static void init() {
        ThreadPoolUtils.commitSingle(() -> {
            SPEncrypted instance = SPEncrypted.getInstance(SPConfig.Photo.KEY);
            compressEnable = instance.getBoolean(SPConfig.Photo.COMPRESS_ENABLE,true);
            compressLevel = instance.getInt(SPConfig.Photo.COMPRESS_LEVEL,80);
        });
    }
}
