package com.lt.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

//生成二维码
public class CreateQRCode {


    /**
     * 生成二维码
     * @param amapUri 转换后的坐标
     * @return
     */
    public static Bitmap createQRCodeUri(String amapUri) {
        Bitmap h = createQRCodeBitmap(amapUri,
                800,
                800,
                "UTF-8",
                "H",
                "1",
                Color.BLACK,
                Color.WHITE);
        return h;
    }

    /**
     * 生成简单二维码
     *
     * @param content                字符串内容
     * @param width                  二维码宽度
     * @param height                 二维码高度
     * @param character_set          编码方式（一般使用UTF-8）
     * @param error_correction_level 容错率 L：7% M：15% Q：25% H：35%
     * @param margin                 空白边距（二维码与边框的空白区域）
     * @param color_black            黑色色块
     * @param color_white            白色色块
     * @return BitMap
     */
    private static Bitmap createQRCodeBitmap(String content, int width, int height,
                                            String character_set, String error_correction_level,
                                            String margin, int color_black, int color_white) {
        // 字符串内容判空
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        // 宽和高>=0
        if (width < 0 || height < 0) {
            return null;
        }
        try {
            /** 1.设置二维码相关配置 */
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            // 字符转码格式设置
            if (!TextUtils.isEmpty(character_set)) {
                hints.put(EncodeHintType.CHARACTER_SET, character_set);
            }
            // 容错率设置
            if (!TextUtils.isEmpty(error_correction_level)) {
                hints.put(EncodeHintType.ERROR_CORRECTION, error_correction_level);
            }
            // 空白边距设置
            if (!TextUtils.isEmpty(margin)) {
                hints.put(EncodeHintType.MARGIN, margin);
            }
            /** 2.将配置参数传入到QRCodeWriter的encode方法生成BitMatrix(位矩阵)对象 */
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            /** 3.创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    //bitMatrix.get(x,y)方法返回true是黑色色块，false是白色色块
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = color_black;//黑色色块像素设置
                    } else {
                        pixels[y * width + x] = color_white;// 白色色块像素设置
                    }
                }
            }
            /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,并返回Bitmap对象 */
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    //转换腾讯地图坐标
    public static String getTenXunUri(double lng, double lat) {
        double[] doubles = CoordinateTransformUtil.wgs84togcj02(lat, lng);
        Uri parse = Uri.parse("qqmap://map/routeplan?"
                + "type=drive"
                + "&to=" + "" //终点的显示名称 必要参数
                + "&tocoord=" + doubles[1] + "," + doubles[0] //终点的经纬度
                + "&referer=test");
        return parse.toString();
    }

    //转百度地图坐标
    public static String getBaiduUri(double lng, double lat) {
        double[] doubles = CoordinateTransformUtil.wgs84tobd09(lng, lat);
        Uri parse = Uri.parse("baidumap://map/direction?destination=latlng:" + doubles[1] + "," + doubles[0] + "|name:" + "" + "&mode=driving");
        return parse.toString();
    }

    //转高德坐标
    public static String getAmapUri(double lng, double lat) {
        double[] doubles = CoordinateTransformUtil.wgs84togcj02(lng, lat);
        Uri parse = Uri.parse("amapuri://route/plan/?dlat=" + doubles[1] + "&dlon=" + doubles[0] + "&dname=" + "" + "&dev=0&t=0");
        return parse.toString();
    }


}
