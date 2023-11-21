package com.lt.utils;

import android.os.Build;
import android.text.TextUtils;

import com.lt.log.LogUtils;
import com.lt.encrypt.AESCrypt;


import java.io.File;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 获取设备唯一编码工具类
 */
public class DeviceSerialUtils {

    private static DeviceSerialUtils INSTANCE;
    public static String PAD_SIGN ="PAD_SIGN";

    private DeviceSerialUtils() {
    }

    public String getDeviceSerialNumber() {
        try {
            String str = SPEncrypted.getInstance().getString(DeviceSerialUtils.PAD_SIGN);
            if (TextUtils.isEmpty(str)) {
                LogUtils.i("读取旧数据为null，开始生成key");
                str = readNewKey();
                SPEncrypted instance = SPEncrypted.getInstance(PAD_SIGN);
                instance.put(PAD_SIGN,str);
                LogUtils.i("新生成key:" + str);
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("获取Key失败:" + e.getMessage());
            try {
                String newKey = readNewKey();
                LogUtils.i("新生成key:" + newKey);
            } catch (Exception ex) {
                ex.printStackTrace();
                LogUtils.e("获取Key失败:" + ex.getMessage());
            }
        }
        LogUtils.e("获取Key失败,返回null");
        return null;
    }

    private String readOldFile() throws IOException, GeneralSecurityException {
//        File logFile = new File(AppFileDirUtils.getLogFileDir() + File.separator + ".logindex");
//        File phoneMapFile = new File(AppFileDirUtils.getPhoneMapDir() + File.separator + ".mapcache");
//        File downloadMediaFile = new File(AppFileDirUtils.getDownloadMediaDir() + File.separator + ".media");
//        File djiDirectoryFile = new File(AppFileDirUtils.getDJIDirectory() + File.separator + ".djiupdate");
//        if (logFile.exists() && phoneMapFile.exists() && downloadMediaFile.exists() && djiDirectoryFile.exists()) {
            StringBuilder sb = new StringBuilder();
//            sb.append(FileUtils.readFileToString(djiDirectoryFile, Charset.defaultCharset()))
//                    .append(FileUtils.readFileToString(phoneMapFile, Charset.defaultCharset()))
//                    .append(FileUtils.readFileToString(downloadMediaFile, Charset.defaultCharset()))
//                    .append(FileUtils.readFileToString(logFile, Charset.defaultCharset()));
            String d = AESCrypt.decrypt(sb.toString());
            //生成厂商
            String manufacturer = Build.MANUFACTURER;
            //品牌（Brand）
            String brand = android.os.Build.BRAND;
            //手机型号
            String model = android.os.Build.MODEL;
            String[] split = d.split("s;,;p");
            if (split.length >= 4) {
                if (split[0].equals(manufacturer) && split[2].equals(brand) && split[3].equals(model)) {
                    return getMD5(split[1]);
                } else {
                    return null;
                }
            } else {
                return null;
            }

//        }
//        return null;
    }

    private String readNewKey() throws GeneralSecurityException, IOException {
//        String logFileDir = AppFileDirUtils.getLogFileDir() + File.separator + ".logindex";
//        String phoneMapDir = AppFileDirUtils.getPhoneMapDir() + File.separator + ".mapcache";
//        String downloadMediaDir = AppFileDirUtils.getDownloadMediaDir() + File.separator + ".media";
//        String djiDirectory = AppFileDirUtils.getDJIDirectory() + File.separator + ".djiupdate";
        StringBuilder sb = new StringBuilder();
        //生成厂商
        String manufacturer = Build.MANUFACTURER;
        //品牌（Brand）
        String brand = android.os.Build.BRAND;
        //手机型号
        String model = android.os.Build.MODEL;
        //设备编码
        String serial = getPhoneSerialNoMd5();
        if (serial == null) {
            serial = UUID.randomUUID().toString().replace("-", "");
            LogUtils.i("readNewKey->生成UUID->" + serial);
        }
        sb.append(manufacturer).append("s;,;p").append(serial).append("s;,;p").append(brand).append("s;,;p").append(model);
        LogUtils.i(sb.toString());
        String encrypt = AESCrypt.encrypt(sb.toString());
        int partSize = encrypt.length() / 4;
        List<String> list = new ArrayList<>();
        int preIndex = 0;
        for (int i = 0; i < 4; i++) {
            if (i == 3) {
                String substring = encrypt.substring(preIndex);
                list.add(substring);
            } else {
                String substring = encrypt.substring(preIndex, preIndex + partSize);
                list.add(substring);
            }
            preIndex = preIndex + partSize;
        }
//        FileUtils.writeStringToFile(new File(djiDirectory), list.get(0), Charset.defaultCharset());
//        FileUtils.writeStringToFile(new File(phoneMapDir), list.get(1), Charset.defaultCharset());
//        FileUtils.writeStringToFile(new File(downloadMediaDir), list.get(2), Charset.defaultCharset());
//        FileUtils.writeStringToFile(new File(logFileDir), list.get(3), Charset.defaultCharset());
        String md5 = getMD5(serial);
        LogUtils.i("返回的MD5值:" + md5);
        return md5;
    }

    private String getMD5(String s) {
        String md5 = getHash(s, "MD5").toUpperCase();
        String md5_16 = md5.substring(8, 24).trim();
        char[] chars = md5_16.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            sb.append(aChar);
            if ((i + 1) % 4 == 0 && i != 15) {
                sb.append("-");
            }
        }
        return sb.toString();
    }

    /**
     * @param source   需要加密的字符串
     * @param hashType 加密类型 （MD5 和 SHA）
     * @return
     */
    private String getHash(String source, String hashType) {

        // 用来将字节转换成 16 进制表示的字符
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest md = MessageDigest.getInstance(hashType);
            md.update(source.getBytes()); // 通过使用 update 方法处理数据,使指定的 byte数组更新摘要
            byte[] encryptStr = md.digest(); // 获得密文完成哈希计算,产生128 位的长整数
            char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对每一个字节,转换成 16 进制字符的转换
                byte byte0 = encryptStr[i]; // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
            return new String(str); // 换后的结果转换为字符串
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getPhoneSerialNoMd5() {
        String result = null;
        try {
            StringBuilder deviceId = new StringBuilder();
            /**
             * 生成厂商
             */

            String manufacturer = Build.MANUFACTURER;
            deviceId.append(manufacturer);
            //WIFI  MAC 地址
            String localMac = getLocalMac();
            if (localMac != null) {
                deviceId.append(localMac);
            }
            String cpuAbi = Build.SUPPORTED_64_BIT_ABIS[0];
            deviceId.append(cpuAbi);
            String radioVersion = Build.getRadioVersion();
            deviceId.append(radioVersion);
            String board = Build.BOARD;
            deviceId.append(board);
            String device = Build.DEVICE;
            deviceId.append(device);
            String display = Build.DISPLAY;
            deviceId.append(display);
            String hardware = Build.HARDWARE;
            deviceId.append(hardware);
            result = deviceId.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    /**
     * 获取设备MAC 地址 由于 6.0 以后 WifiManager 得到的 MacAddress得到都是 相同的没有意义的内容
     * 所以采用以下方法获取Mac地址
     * 需要
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     * <uses-permission android:name="android.permission.INTERNET"></uses-permission>
     *
     * @return
     */
    private static String getLocalMac() {
        String macAddress = null;
        StringBuffer buf = new StringBuffer();
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface == null) {
                return "";
            }
            byte[] addr = networkInterface.getHardwareAddress();


            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
        } catch (SocketException e) {
            e.printStackTrace();
            return "";
        }
        return macAddress;
    }

    public static DeviceSerialUtils getInstance() {
        if (INSTANCE == null) {
            synchronized (DeviceSerialUtils.class) {
                if (null == INSTANCE) {
                    INSTANCE = new DeviceSerialUtils();
                }
            }
        }
        return INSTANCE;
    }
}
