package com.lt;

import static com.lt.config.UrlConfig.*;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.StringUtils;
import com.lt.config.BaseUrlConfig;
import com.lt.config.OfflineConfig;
import com.lt.db.entity.OfflineHttpEntity;
import com.lt.db.greendao.DaoManagerAircraft;
import com.lt.entity.HttpEntity;
import com.lt.utils.MGson;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.RealResponseBody;
import okio.Buffer;

/**
 * 处于离线化状态时，所有接口全部使用该拦截器组装数据返回
 */
final class OfflineConverter {

    @NonNull
    public Response intercept(@NonNull Request chain) throws IOException {
        Request request = chain;
        //如果当前正在执行 退出离线模式 则跳过该拦截器 直接请求网络
        String s = request.url().encodedPath();
        //第一个字符是 /
        s = s.substring(1);
        s = convertPath(s);
        return dispatchHttpData(s, request);
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
     * @return 进入离线化时的响应对象
     */
    private Response dispatchHttpData(String s, Request request) throws IOException {
        Response response = null;
        if (VERSION_INFO.equals(s)){
            return new Response.Builder().protocol(Protocol.HTTP_1_1).request(request).code(200).message("请求成功").body(ResponseBody.create(normalString(), MediaType.parse("application/json;charset=UTF-8"))).build();
        }
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
                //获取当前app版本升级信息
            case VERSION_INFO:
                //获取工单设备精细学习模板
            case ORDER_DEVICE_MODEL:
                //获取飞手信息（重庆专用）
            case WORKER_INFO:
                //下载航线
            case DOWNLOAD_PROTOCOL:
            case DOWNLOAD_PROTOCOL_NEW:
                RequestBody body = request.body();
                Buffer buffer = new Buffer();
                assert body != null;
                body.writeTo(buffer);
                String requestBody = buffer.readUtf8();
                List<OfflineHttpEntity> offlineHttpEntities = DaoManagerAircraft.getInstance().offlineHttpWeapon().loadAll();
                for (OfflineHttpEntity offlineHttpEntity : offlineHttpEntities) {
                    if (!StringUtils.equals(offlineHttpEntity.path, s))
                        continue;
                    //如果当前接口为 登陆接口 请求体可能不一致 也进行构建
                    if (!StringUtils.equals(offlineHttpEntity.requestBody, requestBody) && !StringUtils.equals(WORKER_LOGIN, s))
                        continue;
                    buffer.clear();
                    buffer.write(offlineHttpEntity.responseBody.getBytes(StandardCharsets.UTF_8));
                    response = new Response.Builder().code(200).protocol(Protocol.HTTP_1_1).message("请求成功").request(request).body(
                                    new RealResponseBody("application/json;charset=UTF-8", offlineHttpEntity.responseBody.getBytes(Charset.defaultCharset()).length, buffer))
                            .build();
                    if (StringUtils.equals(offlineHttpEntity.path, s))
                        break;
                }
                break;
            //工单执行状态上报
            case ORDER_UPDATE_STATE:
                //无人机数据实时上传
            case UPLOAD_AIRCRAFT_INFO:
                HttpEntity httpEntity = new HttpEntity();
                httpEntity.data = HttpEncrypt.encrypt("{}");
                httpEntity.code = 200;
                httpEntity.msg = "";
                String ss = MGson.toJson(httpEntity);
                buffer = new Buffer();
                buffer.write(ss.getBytes(StandardCharsets.UTF_8));
                response = new Response.Builder().code(200).protocol(Protocol.HTTP_1_1).message("请求成功").request(request).body(
                                new RealResponseBody("application/json;charset=UTF-8", ss.getBytes(Charset.defaultCharset()).length, buffer))
                        .build();
                saveNeeded(s, request);
                break;
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
                response = new Response.Builder().code(400).protocol(Protocol.HTTP_1_1).request(request).message("离线化状态不允许当前操作->" + s).body(ResponseBody.create(errorString(s), MediaType.parse("application/json;charset=UTF-8"))).build();
        }
        //Date key 的请求头 为 接口请求时间。故统一添加
        return response == null ? new Response.Builder().protocol(Protocol.HTTP_1_1).request(request).code(200).message("离线化状态不允许当前操作->" + s).body(ResponseBody.create(errorString(s), MediaType.parse("application/json;charset=UTF-8"))).build() : response;
    }

    private String errorString(String path) {
        HttpEntity httpEntity = new HttpEntity();
        httpEntity.code = 400;
        httpEntity.msg = "离线化状态不允许当前操作"+path;
        return MGson.toJson(httpEntity);
    }

    private String normalString(){
        HttpEntity httpEntity = new HttpEntity();
        httpEntity.code = 200;
        httpEntity.msg = "";
        httpEntity.data = "";
        return MGson.toJson(httpEntity);
    }
    /**
     * 需要在每次请求都保存数据时的接口，需要将每次请求数据全部缓存入库
     *
     * @param s       path
     * @param request 请求内容
     */
    private void saveNeeded(String s, Request request) throws IOException {
        int i = Arrays.binarySearch(OfflineConfig.needCache, s);
        if (i < 0)
            return;
        Buffer bufferedSink = new Buffer();
        request.body().writeTo(bufferedSink);
        String requestBody = bufferedSink.readUtf8();
        bufferedSink.clear();
        bufferedSink.close();
        OfflineHttpEntity offlineHttpEntity = new OfflineHttpEntity();
        offlineHttpEntity.setPath(s);
        offlineHttpEntity.setRequestBody(requestBody);
        offlineHttpEntity.setRequestTime(System.currentTimeMillis());
        DaoManagerAircraft.getInstance().offlineHttpWeapon().insert(offlineHttpEntity);
    }
}
