package com.lt.utils;

import android.content.Context;
import android.util.Log;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class AssertZipUtils {

    /**
     * 解压缩 assert中的 zip文件
     *
     * @param context       c
     * @param zipFileString 文件名
     * @param outPathString 输出文件夹
     * @param override      是否覆盖
     * @throws Exception 异常
     */
    public static void UnZipAssetsFolder(Context context, String zipFileString, String outPathString, boolean overide) throws Exception {
        ZipInputStream inZip = new ZipInputStream(context.getAssets().open(zipFileString));
        String szName = "";

        ZipEntry zipEntry;
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            File file;
            if (zipEntry.isDirectory()) {
                szName = szName.substring(0, szName.length() - 1);
                file = new File(outPathString + File.separator + szName);
                file.mkdirs();
            } else {
                file = new File(outPathString + File.separator + szName);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                    outPutStream(file, inZip);
                } else if (overide) {
                    outPutStream(file, inZip);
                }
            }
        }

        inZip.close();
    }

    public static void UnZipFolder(String zipPath, String outPathString, boolean overide) throws Exception {
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipPath));
        String szName = "";

        ZipEntry zipEntry;
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            File file;
            if (zipEntry.isDirectory()) {
                szName = szName.substring(0, szName.length() - 1);
                file = new File(outPathString + File.separator + szName);
                file.mkdirs();
            } else {
                Log.e("ZipUtils", outPathString + File.separator + szName);
                file = new File(outPathString + File.separator + szName);
                if (!file.exists()) {
                    Log.e("ZipUtils", "Create the file:" + outPathString + File.separator + szName);
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                    outPutStream(file, inZip);
                } else if (overide) {
                    outPutStream(file, inZip);
                }
            }
        }

        inZip.close();
    }

    private static void outPutStream(File file, ZipInputStream inZip) throws Exception {
        FileOutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[1024];

        int len;
        while ((len = inZip.read(buffer)) != -1) {
            out.write(buffer, 0, len);
            out.flush();
        }

        out.close();
    }
}
