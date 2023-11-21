package com.lt;

import static com.lt.config.UrlConfig.DEVICE_LIST;
import static com.lt.config.UrlConfig.DEVICE_RTMP;
import static com.lt.config.UrlConfig.DOWNLOAD_PROTOCOL;
import static com.lt.config.UrlConfig.DOWNLOAD_PROTOCOL_NEW;
import static com.lt.config.UrlConfig.FLY_ZONE;
import static com.lt.config.UrlConfig.GRID_LIST;
import static com.lt.config.UrlConfig.GROUP_LIST;
import static com.lt.config.UrlConfig.LEDGER_DEVICE_MODEL;
import static com.lt.config.UrlConfig.ORDER_DETAILS;
import static com.lt.config.UrlConfig.ORDER_DEVICE_MODEL;
import static com.lt.config.UrlConfig.ORDER_FINISH;
import static com.lt.config.UrlConfig.ORDER_LIST;
import static com.lt.config.UrlConfig.ORDER_UPDATE_STATE;
import static com.lt.config.UrlConfig.ORDER_UPGRADE_PROTOCOL;
import static com.lt.config.UrlConfig.PORT_AUTHORIZATION;
import static com.lt.config.UrlConfig.RENAME_AIRCRAFT;
import static com.lt.config.UrlConfig.SIGN_REGISTER;
import static com.lt.config.UrlConfig.UPDATE_DEVICE;
import static com.lt.config.UrlConfig.UPLOAD_AIRCRAFT_INFO;
import static com.lt.config.UrlConfig.UPLOAD_PIC;
import static com.lt.config.UrlConfig.VERSION_INFO;
import static com.lt.config.UrlConfig.WORKER_INFO;
import static com.lt.config.UrlConfig.WORKER_LOGIN;
import static com.lt.config.UrlConfig.WORKER_LOGOUT;

import androidx.annotation.NonNull;

import com.lt.config.BaseUrlConfig;
import com.lt.config.OfflineConfig;
import com.lt.db.entity.OfflineHttpEntity;
import com.lt.db.greendao.DaoManagerAircraft;
import com.lt.log.LogUtils;

import java.io.IOException;
import java.lang.reflect.Field;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * 进入离线化时，使用该处理器 处理接口缓存数据
 * 缓存入本地数据库/文件等
 * 当前：
 * 飞机当前状态信息接口
 * 为 缓存入本地数据 file文件夹，其他全部为数据库 防止数据量过大造成卡顿
 */
public final class OfflineCacheInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (!OfflineConfig.intoOffline)
            return chain.proceed(chain.request());
        Request request = chain.request();
        String s = request.url().encodedPath();
        //第一个字符是 /
        s = s.substring(1);
        s = convertPath(s);
        return dispatchHttpData(s, chain);
    }
    private String convertPath(String s) {
        Field[] url_server = BaseUrlConfig.class.getFields();
        for (Field field : url_server) {
            field.setAccessible(true);
            String name = field.getName();
            if (!"URL_SERVER".equals(name))
                continue;
            try {
                String o = (String) field.get(null);
                s = s.replace(o,"");
                if (s.startsWith("/"))
                    s = s.substring(1);
                return s;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return s;
    }
    /**
     * 分发离线数据 从不同路径/缓存位置 获取数据响应
     *
     * @param s       path地址
     * @param request 请求对象
     * @return 进入离线化时的响应对象 该对象还是从服务端返回的
     */
    private synchronized Response dispatchHttpData(String s, Chain request) throws IOException {
        switch (s) {
            //查询pad授权信息
            case PORT_AUTHORIZATION:
                //无人机授权
            case SIGN_REGISTER:
                //管控平台登陆 即 飞手登陆
            case WORKER_LOGIN:
                //根据飞机id 获取推流地址
            case DEVICE_RTMP:
                //查询工单列表
            case ORDER_LIST:
                //工单巡检设备查询
            case ORDER_DETAILS:
                //结束工单
            case ORDER_FINISH:
                //获取当前空域信息
            case FLY_ZONE:
                //工单执行状态上报
            case ORDER_UPDATE_STATE:
                //无人机数据实时上传
            case UPLOAD_AIRCRAFT_INFO:
                //获取当前app版本升级信息
            case VERSION_INFO:
                //获取工单设备精细学习模板
            case ORDER_DEVICE_MODEL:
                //获取飞手信息（重庆专用）
            case WORKER_INFO:
                //下载航线
            case DOWNLOAD_PROTOCOL:
            case DOWNLOAD_PROTOCOL_NEW:
                //修正设备，修正设备信息
            case UPDATE_DEVICE:
                //获取网格台账数据
            case GRID_LIST:
                // TODO 上传 精细学习数据 注意 必须工单下发的任务彩壳上传 否则不允许
            case ORDER_UPGRADE_PROTOCOL:
                //重命名无人机接口
            case RENAME_AIRCRAFT:
                //上传图片接口
            case UPLOAD_PIC:
                //台账 获取分组信息 可用作 获取线路信息
            case GROUP_LIST:
                //获取 分组中的设备信息 可用作 获取线路中的杆塔信息
            case DEVICE_LIST:
                //获取台账，即当前通用设备精细学习模板
            case LEDGER_DEVICE_MODEL:
                //管控平台 登出  即 飞手登出
            case WORKER_LOGOUT:
                //其他接口，不允许离线状态获取数据
            default:
                LogUtils.d("开始组装缓存数据：->" + BaseUrlConfig.BASE_URL + s);
                RequestBody body = request.request().body();
                Response response = request.proceed(request.request());
                if (!response.isSuccessful() || response.code() != 200 || response.body() == null)
                    return response;
                long contentLength = 0;
                if (response.body().contentLength() <= 0)
                    contentLength = 1024 * 1024 * 2;
                else
                    contentLength = response.body().contentLength();
                ResponseBody responseBody = response.peekBody(contentLength);
                String string = responseBody.string();
                //数据入库
                Buffer bufferedSink = new Buffer();
                assert body != null;
                body.writeTo(bufferedSink);
                String requestBody = bufferedSink.readUtf8();
                bufferedSink.clear();
                bufferedSink.close();
                OfflineHttpEntity e = new OfflineHttpEntity();
                e.path = s;
                e.requestBody = requestBody;
                e.responseBody = string;
                e.requestTime = System.currentTimeMillis();
                DaoManagerAircraft.getInstance().offlineHttpWeapon().insert(e);
                return response;
        }
    }
}