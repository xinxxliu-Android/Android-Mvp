package com.lt.config;

import com.lt.utils.SPEncrypted;

import org.json.JSONException;
import org.json.JSONObject;

import static com.lt.config.UrlConfig.*;

public final class OfflineConfig {
    /**
     * 当前 是否正在进入离线模式
     * 通过该标记 标记是否进入 离线模式专用拦截器
     */
    public static boolean intoOffline;

    public static boolean tempOffline = true;
    /**
     * 每次请求 都需要将数据缓存入库
     * 在退出离线化时 需要将数据统一上传平台的接口
     */
    public static String[] needCache = {
            //无人机状态信息上报
            UPLOAD_AIRCRAFT_INFO,
            //工单执行状态
            ORDER_UPDATE_STATE,
    };

    /**
     * 进入离线化后，允许请求的接口，
     * 但是 只会固定返回最后一次接口请求的响应的结果
     * <p>
     * 该字段的值 加上 {@link  #needCache}中的值
     * 在离线化状态时 能够请求，且响应的是本地缓存的最后一次响应的值。
     * 除此之外，所有其他接口全部响应失败，提示信息：离线化状态不允许该操作
     */
    static String[] cacheSingle = {

    };

    public static boolean isOfflineMode() {
        return tempOffline && sp().getBoolean(SPConfig.Settings.User.OFFLINE_MODE) && System.currentTimeMillis() < offlineTime();
    }

    public static void updateOfflineState(boolean state) {
        tempOffline = true;
        sp().put(SPConfig.Settings.User.OFFLINE_MODE, state, true);
    }

    public static void updateTempOfflineState(boolean state) {
        tempOffline = state;
    }

    /**
     * 允许离线化持续到的 时间
     *
     * @return x
     */
    public static long offlineTime() {
        return sp().getLong(SPConfig.Settings.User.OFFLINE_MODE_TIME,0);
//        return System.currentTimeMillis() + (long) (1 * 60 * 60 * 1000);
    }

    /**
     * 允许离线化的时长
     *
     * @param offlineResponse x
     */
    public static void updateOfflineInfo(String offlineResponse) {
        try {
            JSONObject jsonObject = new JSONObject(offlineResponse);
            long maxTime = jsonObject.getLong("maxTime") * 24 * 60 * 60 * 1000;
            sp().put(SPConfig.Settings.User.OFFLINE_MODE_TIME, maxTime + System.currentTimeMillis(), true);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static SPEncrypted sp() {
        return SPEncrypted.getInstance(SPConfig.Settings.NAME);
    }
}
