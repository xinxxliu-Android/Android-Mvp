package com.lt.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.lt.encrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 加密的 SP管理工具类。 借鉴 {@link com.blankj.utilcode.util.SPUtils}实现
 * TODO 未完成加密操作
 * 注意，同一个对象的同一个key 会被覆盖
 */
@SuppressWarnings("unused")
public class SPEncrypted {

    private static final Map<String, SPEncrypted> SP_UTILS_MAP = new HashMap<>();
    /**
     * 所有数据 全部使用加密key 及 value存储
     */
    private static final String int_key = "int_key";
    private static final String long_key = "long_key";
    private static final String boolean_key = "boolean_key";
    private static final String byte_key = "byte_key";
    private static final String short_key = "short_key";
    private static final String float_key = "float_key";
    private static final String double_key = "double_key";
    private final SharedPreferences sp;

    private static String encryptKey;

    /**
     * Return the single {@link SPEncrypted} instance
     *
     * @return the single {@link SPEncrypted} instance
     */
    public static SPEncrypted getInstance() {
        return getInstance("", Context.MODE_PRIVATE);
    }

    public static void initInstall(String eKey) {
        encryptKey = eKey;
    }

    /**
     * Return the single {@link SPEncrypted} instance
     *
     * @param mode Operating mode.
     * @return the single {@link SPEncrypted} instance
     */
    public static SPEncrypted getInstance(final int mode) {
        return getInstance("", mode);
    }

    /**
     * Return the single {@link SPEncrypted} instance
     *
     * @param spName The name of sp.
     * @return the single {@link SPEncrypted} instance
     */
    public static SPEncrypted getInstance(String spName) {
        return getInstance(spName, Context.MODE_PRIVATE);
    }

    /**
     * Return the single {@link SPEncrypted} instance
     *
     * @param spName The name of sp.
     * @param mode   Operating mode.
     * @return the single {@link SPEncrypted} instance
     */
    public static SPEncrypted getInstance(String spName, final int mode) {
        if (isSpace(spName)) spName = "spUtils";
        SPEncrypted spUtils = SP_UTILS_MAP.get(spName);
        if (spUtils == null) {
            synchronized (SPEncrypted.class) {
                spUtils = SP_UTILS_MAP.get(spName);
                if (spUtils == null) {
                    spUtils = new SPEncrypted(spName, mode);
                    SP_UTILS_MAP.put(spName, spUtils);
                }
            }
        }
        return spUtils;
    }

    private SPEncrypted(final String spName) {
        sp = Utils.getApp().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    private SPEncrypted(final String spName, final int mode) {
        sp = Utils.getApp().getSharedPreferences(spName, mode);
    }

    /**
     * Put the string value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public void put(@NonNull final String key, final String value) {
        put(key, value, true);
    }

    /**
     * Put the string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    @SuppressLint("ApplySharedPref")
    public void put(@NonNull final String key, final String value, final boolean isCommit) {
        if (isCommit) {
            try {
                sp.edit().putString(key, AESCrypt.encrypt(encryptKey, value)).commit();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        } else {
            try {
                sp.edit().putString(key, AESCrypt.encrypt(encryptKey, value)).apply();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Return the string value in sp.
     *
     * @param key The key of sp.
     * @return the string value if sp exists or {@code ""} otherwise
     */
    public String getString(@NonNull final String key) {
        return getString(key, "");
    }

    /**
     * Return the string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the string value if sp exists or {@code defaultValue} otherwise
     */
    public String getString(@NonNull final String key, final String defaultValue) {
        String string = sp.getString(key, defaultValue);
        if (Objects.equals(string, defaultValue))
            return defaultValue;
        if (!StringUtils.isTrimEmpty(string)) {
            return AESCrypt.decrypt(encryptKey, string);
        }
        return defaultValue;
    }

    /**
     * Put the int value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public void put(@NonNull final String key, final int value) {
        put(key, value, true);
    }

    /**
     * Put the int value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    @SuppressLint("ApplySharedPref")
    public void put(@NonNull final String key, final int value, final boolean isCommit) {
        put(key + int_key, value + int_key, isCommit);
    }

    /**
     * Return the int value in sp.
     *
     * @param key The key of sp.
     * @return the int value if sp exists or {@code -1} otherwise
     */
    public int getInt(@NonNull final String key) {
        return getInt(key, -1);
    }

    /**
     * Return the int value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the int value if sp exists or {@code defaultValue} otherwise
     */
    public int getInt(@NonNull final String key, final int defaultValue) {
        String string = getString(key + int_key);
        if (StringUtils.isTrimEmpty(string))
            return defaultValue;
        String substring = string.replace(int_key, "");
        if (!substring.matches("\\d+"))
            return defaultValue;
        return Integer.parseInt(substring);
    }

    /**
     * Put the long value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public void put(@NonNull final String key, final long value) {
        put(key, value, true);
    }

    /**
     * Put the long value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    @SuppressLint("ApplySharedPref")
    public void put(@NonNull final String key, final long value, final boolean isCommit) {
        put(key + long_key, value + long_key, isCommit);
    }

    /**
     * Return the long value in sp.
     *
     * @param key The key of sp.
     * @return the long value if sp exists or {@code -1} otherwise
     */
    public long getLong(@NonNull final String key) {
        return getLong(key, -1L);
    }

    /**
     * Return the long value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the long value if sp exists or {@code defaultValue} otherwise
     */
    public long getLong(@NonNull final String key, final long defaultValue) {
        String string = getString(key + long_key);
        if (StringUtils.isTrimEmpty(string))
            return defaultValue;
        String substring = string.replace(long_key, "");
        if (!substring.matches("\\d+"))
            return defaultValue;
        return Long.parseLong(substring);
    }

    /**
     * Put the float value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public void put(@NonNull final String key, final float value) {
        put(key, value, true);
    }

    /**
     * Put the float value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    @SuppressLint("ApplySharedPref")
    public void put(@NonNull final String key, final float value, final boolean isCommit) {
        put(key + float_key, value + float_key, isCommit);
    }

    /**
     * Return the float value in sp.
     *
     * @param key The key of sp.
     * @return the float value if sp exists or {@code -1f} otherwise
     */
    public float getFloat(@NonNull final String key) {
        return getFloat(key, -1f);
    }

    /**
     * Return the float value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the float value if sp exists or {@code defaultValue} otherwise
     */
    public float getFloat(@NonNull final String key, final float defaultValue) {
        String string = getString(key + float_key);
        if (StringUtils.isTrimEmpty(string))
            return defaultValue;
        String substring = string.replace(float_key, "");
        if (!substring.matches("\\d+\\.?\\d?"))
            return defaultValue;
        return sp.getFloat(key, defaultValue);
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public void put(@NonNull final String key, final boolean value) {
        put(key, value, true);
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    @SuppressLint("ApplySharedPref")
    public void put(@NonNull final String key, final boolean value, final boolean isCommit) {
        put(key + boolean_key, value + boolean_key, isCommit);
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key The key of sp.
     * @return the boolean value if sp exists or {@code false} otherwise
     */
    public boolean getBoolean(@NonNull final String key) {
        return getBoolean(key, false);
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the boolean value if sp exists or {@code defaultValue} otherwise
     */
    public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        String string = getString(key + boolean_key);
        if (StringUtils.isTrimEmpty(string))
            return defaultValue;
        String substring = string.replace(boolean_key, "").toLowerCase();
        if (substring.equals("true") || substring.equals("false"))
            return Boolean.parseBoolean(substring);
        return defaultValue;
    }

    /**
     * Remove all preferences in sp.
     */
    public void clear() {
        clear(true);
    }

    /**
     * Remove all preferences in sp.
     *
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    @SuppressLint("ApplySharedPref")
    public void clear(final boolean isCommit) {
        if (isCommit) {
            sp.edit().clear().commit();
        } else {
            sp.edit().clear().apply();
        }
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
