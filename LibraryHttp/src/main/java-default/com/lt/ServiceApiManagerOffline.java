package com.lt;

import static com.lt.config.UrlConfig.DEVICE_LIST;
import static com.lt.config.UrlConfig.DEVICE_RTMP;
import static com.lt.config.UrlConfig.DOWNLOAD_PROTOCOL;
import static com.lt.config.UrlConfig.DOWNLOAD_PROTOCOL_NEW;
import static com.lt.config.UrlConfig.FLY_ZONE;
import static com.lt.config.UrlConfig.GROUP_LIST;
import static com.lt.config.UrlConfig.LEDGER_DEVICE_MODEL;
import static com.lt.config.UrlConfig.OFFLINE_MODE;
import static com.lt.config.UrlConfig.ORDER_DETAILS;
import static com.lt.config.UrlConfig.ORDER_DEVICE_MODEL;
import static com.lt.config.UrlConfig.ORDER_FINISH;
import static com.lt.config.UrlConfig.ORDER_LIST;
import static com.lt.config.UrlConfig.ORDER_UPDATE_STATE;
import static com.lt.config.UrlConfig.ORDER_UPGRADE_PROTOCOL;
import static com.lt.config.UrlConfig.UPDATE_DEVICE;
import static com.lt.config.UrlConfig.UPLOAD_AIRCRAFT_INFO;
import static com.lt.config.UrlConfig.VERSION_INFO;
import static com.lt.config.UrlConfig.WORKER_INFO;
import static com.lt.config.UrlConfig.WORKER_LOGIN;
import static com.lt.config.UrlConfig.WORKER_LOGOUT;

import com.blankj.utilcode.util.TimeUtils;
import com.lt.config.BaseUrlConfig;
import com.lt.config.CustomUrlConfig;
import com.lt.config.UrlConfig;
import com.lt.config.UserConfig;
import com.lt.entity.HttpEntity;
import com.lt.http.DecodeBodyFunction;
import com.lt.server.request.AircraftStateRequest;
import com.lt.server.request.AppUpdateRequest;
import com.lt.server.request.DeviceModelRequest;
import com.lt.server.request.FlyZoneRequest;
import com.lt.server.request.GridRequest;
import com.lt.server.request.LedgerDeviceRequest;
import com.lt.server.request.LedgerLineRequest;
import com.lt.server.request.LineDownloadRequest;
import com.lt.server.request.MediaPathRequest;
import com.lt.server.request.OfflineRequest;
import com.lt.server.request.OrderDeviceModelRequest;
import com.lt.server.request.OrderDeviceRequest;
import com.lt.server.request.OrderDeviceStateRequest;
import com.lt.server.request.OrderInterceptRequest;
import com.lt.server.request.OrderListRequest;
import com.lt.server.request.OrderUpgradeProtocolRequest;
import com.lt.server.request.SignRenameRequest;
import com.lt.server.request.SignRequest;
import com.lt.server.request.UpdateDeviceRequest;
import com.lt.server.request.WorkerInfoRequest;
import com.lt.server.request.WorkerLoginRequest;
import com.lt.server.request.WorkerLogoutRequest;
import com.lt.server.response.WorkerLoginResponse;
import com.lt.utils.MGson;
import com.lt.utils.RxSchedulers;

import java.io.IOException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.Part;

/**
 * 项目中 所有接口请求归属该类
 */
final class ServiceApiManagerOffline extends ServiceApiManager{
    OfflineConverter interceptor;
    private static ServiceApiManagerOffline manager;

    public static ServiceApiManagerOffline getInstance() {
        if (manager == null)
            synchronized (ServiceApiManagerOffline.class) {
                if (manager == null)
                    manager = new ServiceApiManagerOffline();
            }
        return manager;
    }
    /**
     * 请求授权
     *
     * @param request 请求授权
     * @return 响应授权
     */
    public Flowable<String> requestSign(SignRequest request) {
        return readFromDb(UrlConfig.SIGN_REGISTER, request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .filter((it) -> {
                    return true;
                })
                .observeOn(RxSchedulers.main());
    }

    private <@NonNull T> Flowable<Response<ResponseBody>> readFromDb(String url, Object signRequest) {
        RequestBody requestBody = RequestBody.create(MGson.toJson(signRequest), MediaType.parse("application/json"));
        Request.Builder post = new Request.Builder().url(BaseUrlConfig.BASE_URL + url).post(requestBody);
        if (interceptor == null)
            interceptor = new OfflineConverter();
        try {
            okhttp3.Response intercept = interceptor.intercept(post.build());
            return Flowable.just(Response.success(intercept.body()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 进入、退出离线化模式
     *
     * @param request 请求
     * @return 响应
     */
    public Flowable<String> offlineMode(@Body OfflineRequest request){
        return readFromDb(OFFLINE_MODE,request)
                .flatMap(new DecodeBodyFunction<String>() {
                }).subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }
    /**
     * 请求 重命名飞机
     *
     * @param request 请求重命名
     * @return 响应重命名结果
     */
    public Flowable<String> requestRenameAircraft(SignRenameRequest request) {
        return readFromDb(UrlConfig.RENAME_AIRCRAFT, request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * 飞手登陆
     *
     * @param request 请求
     * @return 响应
     */
    public Flowable<String> requestWorkerLogin(WorkerLoginRequest request) {
        return readFromDb(WORKER_LOGIN, request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .map((it) -> {
                    WorkerLoginResponse res = MGson.fromJson(it, WorkerLoginResponse.class);
                    UserConfig.workerUuid = res.workerGuid;
                    UserConfig.workerName = res.workerName;
                    UserConfig.usePeriod = TimeUtils.millis2String(res.workerCreateTime);
                    return it;
                })
                .observeOn(RxSchedulers.main());
    }

    /**
     * 飞手登出
     *
     * @param request 请求
     * @return 响应
     */
    public Flowable<String> requestWorkerLogout(WorkerLogoutRequest request) {
        return readFromDb(WORKER_LOGOUT, request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .filter((it) -> {
                    if (it == null)
                        return true;
                    UserConfig.workerUuid = null;
                    UserConfig.usePeriod = null;
                    return true;
                })
                .observeOn(RxSchedulers.main());
    }

    /**
     * 飞手信息
     *
     * @param request 请求
     * @return 响应
     */
    public Flowable<String> requestWorkerInfo(WorkerInfoRequest request) {
        return readFromDb(WORKER_INFO, request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .map((it) -> {
                    return it;
                })
                .observeOn(RxSchedulers.main());
    }

    /**
     * 上传当前无人机信息
     *
     * @param request 请求
     * @return 响应
     */
    public Flowable<String> requestUploadAircraftState(AircraftStateRequest request) {
        return readFromDb(UPLOAD_AIRCRAFT_INFO, request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * 获取当前无人机的推流地址
     *
     * @param request 请求
     * @return 响应
     */
    public Flowable<String> requestDeviceRtmpPath(MediaPathRequest request) {
        return readFromDb(DEVICE_RTMP, request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * 获取当前app版本升级信息
     *
     * @param request 请求
     * @return 响应
     */
    public Flowable<String> requestAppVersionInfo(AppUpdateRequest request) {
        return readFromDb(VERSION_INFO, request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * 获取工单列表
     *
     * @param request 请求
     * @return 响应
     */
    public Flowable<String> requestOrderList(OrderListRequest request) {
        return readFromDb(ORDER_LIST, request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * 获取工单详情
     *
     * @param request 请求
     * @return 响应
     */
    public Flowable<String> requestOrderDetails(OrderDeviceRequest request) {
        return readFromDb(ORDER_DETAILS, request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * 工单执行状态同步
     *
     * @param request 请求
     * @return 响应
     */
    public Flowable<String> requestUpdateOrderState(OrderDeviceStateRequest request) {
        return readFromDb(ORDER_UPDATE_STATE, request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * app结束工单
     *
     * @param request 请求
     * @return 响应
     */
    public Flowable<String> requestOrderFinish(OrderInterceptRequest request) {
        return readFromDb(ORDER_FINISH, request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * 上传图片
     *
     * @param param      参数
     * @param uploadFile 文件
     * @return 结果
     */
    @Multipart
    Call<HttpEntity> requestUploadPic(@Header("token") String userToken, @Part("param") RequestBody param, @Part MultipartBody.Part uploadFile) {
        return null;
    }

    /**
     * 获取空域信息
     *
     * @param request 请求
     * @return 响应
     */
    public Flowable<String> requestFlyZone(FlyZoneRequest request) {
        return readFromDb(FLY_ZONE, request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * 航线文件下载
     *
     * @param request 请求
     * @return 响应
     */
    public Flowable<String> requestFlyProtocol(LineDownloadRequest request) {
        return readFromDb(DOWNLOAD_PROTOCOL, request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * 新航线文件下载
     *
     * @param request 请求
     * @return 响应
     */
    public Flowable<String> requestFlyProtocolNew(LineDownloadRequest request) {
        return readFromDb(DOWNLOAD_PROTOCOL_NEW, request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * 台账：查询线路信息
     *
     * @param request 请求
     * @return 响应
     */
    public Flowable<String> requestLedgerGroup(LedgerLineRequest request) {
        return readFromDb(GROUP_LIST, request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * 台账：网格
     *
     * @param request 请求
     * @return 响应
     */
    public Flowable<String> requestLedgerGrid(GridRequest request) {
        return null;
    }

    /**
     * 台账：查询设备信息
     *
     * @param request 请求
     * @return 响应
     */
    public Flowable<String> requestLedgerDevice(LedgerDeviceRequest request) {
        return readFromDb(DEVICE_LIST, request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * 上传 精细学习 生成的航迹数据
     *
     * @param request
     * @return
     */
    public Flowable<String> upgradeProtocol(OrderUpgradeProtocolRequest request) {
        return readFromDb(ORDER_UPGRADE_PROTOCOL, request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * 获取工单设备精细学习模板
     *
     * @param request 请求
     * @return 相应
     */
    public Flowable<String> orderDeviceModel(OrderDeviceModelRequest request) {
        return readFromDb(ORDER_DEVICE_MODEL, request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * 获取台账，即当前通用设备精细学习模板
     *
     * @param request 请求
     * @return 相应
     */
    public Flowable<String> ledgerDeviceModel(DeviceModelRequest request) {
        return readFromDb(LEDGER_DEVICE_MODEL, request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    public Flowable<String> updateDeviceModel(UpdateDeviceRequest request) {
        return readFromDb(UPDATE_DEVICE, request)
                .flatMap(new DecodeBodyFunction<String>() {
                }).subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * 自定义 网络请求
     * 通过{@link com.lt.config.CustomUrlConfig}中的各个字段，通过本地拦截器进行path转换
     *
     * @param request 请求对象
     * @param type    接口类型，对应的是{@link com.lt.config.CustomUrlConfig}中的各个字段
     * @return x
     */
    public Flowable<String> customRequest(@Body Object request, @Header("custom_type") int type) {
        return readFromDb(new LocalInterceptor().filterPath("/" + CustomUrlConfig.PATH_BASE, type + ""), request)
                .flatMap(new DecodeBodyFunction<String>() {
                }).subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

}
