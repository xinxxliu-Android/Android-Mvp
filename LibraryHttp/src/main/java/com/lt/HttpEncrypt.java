package com.lt;

import com.lt.config.BuildConfig;
import com.lt.encrypt.AESCrypt;
import com.lt.encrypt.SMUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;

/**
 * 网络请求 请求体 和 响应体 统一加解密
 */
public class HttpEncrypt {
    /**
     * 加密
     * @param input
     * @return
     */
    public static String encrypt(String input) {
        if (BuildConfig.ENCRYPT_TYPE == 0) {
            try {
                return AESCrypt.encryptByte(input.getBytes(Charset.defaultCharset()));
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }
        if (BuildConfig.ENCRYPT_TYPE == 1) {
            try {
                return AESCrypt.encrypt(input);
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }
        if (BuildConfig.ENCRYPT_TYPE == 2) {
            byte[] bytes = input.getBytes(Charset.defaultCharset());
            try {
                return SMUtils.SM2Encrypt(bytes) + "," + SMUtils.SM3Encrypt(new String(bytes));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * 解密
     * @param input
     * @return
     */
    public static String decrypt(String input) {
        if (BuildConfig.ENCRYPT_TYPE == 0) {
            return AESCrypt.decrypt(input);
        }
        if (BuildConfig.ENCRYPT_TYPE == 1) {
            return AESCrypt.decrypt(input);
        }
        if (BuildConfig.ENCRYPT_TYPE == 2) {
            try {
                return SMUtils.SM2DeCodeEncrypt(input);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
