package com.lt.utils;

import android.content.res.AssetManager;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * assert文件夹中的文件处理
 * 1、从assert写入app指定目录
 * 2、读取assert中的文件的内容-string或byte[]
 */
public class AssertFileUtils {

    public static String read2String(String path) {
        AssetManager assets = PluginUtils.mAppContext.getAssets();
        try {
            InputStream inputStream = assets.open(path);
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] read2byte(String path) {
        AssetManager assets = PluginUtils.mAppContext.getAssets();
        try {
            InputStream inputStream = assets.open(path);
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File read2file(String path,String to) {
        AssetManager assets = PluginUtils.mAppContext.getAssets();
        try {
            InputStream inputStream = assets.open(path);
            FileUtils.delete(to);
            FileIOUtils.writeFileFromIS(to,inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new File(to);
    }
}
