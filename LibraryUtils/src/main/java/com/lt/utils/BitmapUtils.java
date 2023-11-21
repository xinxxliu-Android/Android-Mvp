package com.lt.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.lt.log.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class BitmapUtils {
    public static File compress(File src, int compressLevel) {
        //图片压缩
        String name = src.getName();
        Bitmap bitmap = BitmapFactory.decodeFile(src.getAbsolutePath());
        //读xmp数据
        Map<String, String> metadata = XmpUtils.readXmp(src);
        File cacheDir = Utils.getApp().getCacheDir();
        File f = new File(cacheDir, "Bitmap");
        f.mkdirs();
        f = new File(f, name);
        try {
            f.delete();
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressLevel, fo);
            fo.close();
            Log.e("压缩后的大小",f.length()+"");
        } catch (IOException e) {
            //压缩失败
            LogUtils.e(e);
            ToastUtils.showShort("图片压缩失败");
            return src;
        }
        if (!bitmap.isRecycled())
            bitmap.recycle();
        //写入源文件的xmp数据
        XmpUtils.writeXmp(metadata, f);
        return f;
    }

    public static byte[] toByteArray(Bitmap bitmap) {
        if (bitmap == null)
            return null;
        ByteArrayOutputStream br = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,br);
        byte[] bytes = br.toByteArray();
        try {
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }

    public static Bitmap decode(File file) {
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }
}
