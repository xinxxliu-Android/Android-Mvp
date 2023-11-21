package com.lt.utils;

import android.graphics.Bitmap;
import android.text.Layout;
import android.util.Base64;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;


/**
 * RFID 通信协议封包和解包的工具类
 * 将数据封装成协议格式发送给RFID设备
 * 将RFID设备发送回来的数据解析成正常数据
 */
public class RFIDUtils {
//    只校验正数 0-90.000000 0-180.000000 范围内

    public static int getTextViewHeight(TextView view) {
        Layout layout = view.getLayout();
        if (layout==null){
            return 0;
        }
        int desired = layout.getLineTop(view.getLineCount());
        int padding = view.getCompoundPaddingTop() + view.getCompoundPaddingBottom();
        return desired + padding;
    }

    /**
     * 经纬度校验
     * 经度longitude: (?:[0-9]|[1-9][0-9]|1[0-7][0-9]|180)\\.([0-9]{6})
     * 纬度latitude：  (?:[0-9]|[1-8][0-9]|90)\\.([0-9]{6})
     *
     * @return
     */
    public static boolean checkItude(double longitude, double latitude) {
        String reglo = "((?:[0-9]|[1-9][0-9]|1[0-7][0-9])\\.([0-9]{0,6}))|((?:180)\\.([0]{0,6}))";
        String regla = "((?:[0-9]|[1-8][0-9])\\.([0-9]{0,6}))|((?:90)\\.([0]{0,6}))";
        String one = String.format("%.6f", longitude).trim();
        String two = String.format("%.6f", latitude).trim();
        return one.matches(reglo) == true ? two.matches(regla) : false;
    }
    /*
     * bitmap转base64
     * */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getStringUTF8(byte[] bytes, int start, int length) {
        if (null == bytes || bytes.length == 0) {
            return "";
        }
        // 去除NULL字符
        byte zero = 0x00;
        for (int i = start; i < length && i < bytes.length; i++) {
            if (bytes[i] == zero) {
                length = i - start;
                break;
            }
        }
        return getString(bytes, start, length, "UTF-8");
    }

    private static String getString(byte[] bytes, int start, int length, String charsetName) {
        return new String(bytes, start, length, Charset.forName(charsetName));
    }

   /* public static boolean isMultiStreamPlatform() {
        if (DJISDKManager.getInstance() == null) {
            return false;
        }
        Model model = DJISDKManager.getInstance().getProduct().getModel();
        return model != null && (model == Model.INSPIRE_2
                || model == Model.MATRICE_200
                || model == Model.MATRICE_210
                || model == Model.MATRICE_210_RTK
                || model == Model.MATRICE_200_V2
                || model == Model.MATRICE_210_V2
                || model == Model.MATRICE_210_RTK_V2
                || model == Model.MATRICE_300_RTK
                || model == Model.MATRICE_600
                || model == Model.MATRICE_600_PRO
                || model == Model.A3
                || model == Model.N3);
    }

    public static boolean isM300Product() {
        if (DJISDKManager.getInstance().getProduct() == null) {
            return false;
        }
        Model model = DJISDKManager.getInstance().getProduct().getModel();
        return model == Model.MATRICE_300_RTK;
    }*/
}
