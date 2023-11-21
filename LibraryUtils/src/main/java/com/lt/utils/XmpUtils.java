package com.lt.utils;

import android.media.ExifInterface;
import android.nfc.Tag;
import android.util.ArrayMap;

import com.blankj.utilcode.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import kotlin.Metadata;

/**
 * 图片元数据操作工具类
 */
public class XmpUtils {
    /**
     * 从文件中 读取当前文件的 所有的xmp数据
     *
     * @param f 被操作的文件
     * @return 读取到的完整数据
     */
    public static Map<String, String> readXmp(File f) {
        Map<String, String> xmpData = new ArrayMap<>();
        //将 老文件的 xmp数据 读出来 写入新文件里
        try {
            ExifInterface exifInterface = new ExifInterface(f.getAbsolutePath());
            Field[] fields = exifInterface.getClass().getFields();
            ArrayList<String> strs = new ArrayList<>();
            for (Field field : fields) {
                field.setAccessible(true);
                if (!Modifier.isStatic(field.getModifiers()))
                    continue;
                if (!field.getName().startsWith("TAG"))
                    continue;
                Class<?> type = field.getType();
                if (type != String.class)
                    continue;
                String o = (String) field.get(null);
                strs.add(o);
            }
            for (String str : strs) {
                String attribute = exifInterface.getAttribute(str);
                if (StringUtils.isTrimEmpty(attribute))
                    continue;
                xmpData.put(str,attribute);
            }
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return xmpData;
    }

    /**
     * 将xmp数据 写入文件 与上方文件对应
     *
     * @param xmpData 数据源
     * @param f       需要写入的文件
     */
    public static void writeXmp(Map<String, String> xmpData, File f) {
        try {
            ExifInterface exifInterface = new ExifInterface(f.getAbsolutePath());
            Set<Map.Entry<String, String>> entries = xmpData.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                exifInterface.setAttribute(entry.getKey(),entry.getValue());
                exifInterface.saveAttributes();
            }
            exifInterface.saveAttributes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
