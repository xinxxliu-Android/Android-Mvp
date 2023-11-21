package com.lt;

import com.blankj.utilcode.util.TimeUtils;
import com.lt.config.CustomUrlConfig;
import com.lt.config.UrlConfig;
import com.lt.config.UserConfig;
import com.lt.db.entity.RequestEntity;
import com.lt.entity.HttpEntity;
import com.lt.http.DecodeBodyFunction;
import com.lt.http.LibraryHttp;
import com.lt.server.request.AircraftStateRequest;
import com.lt.server.request.AppUpdateRequest;
import com.lt.server.request.AuditorListRequest;
import com.lt.server.request.DeviceModelRequest;
import com.lt.server.request.FinishTaskUpdateStatusRequest;
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
import com.lt.server.request.UpdateWorkOrderRequest;
import com.lt.server.request.WorkerInfoRequest;
import com.lt.server.request.WorkerLoginRequest;
import com.lt.server.request.WorkerLogoutRequest;
import com.lt.server.response.WorkerLoginResponse;
import com.lt.utils.MGson;
import com.lt.utils.RxSchedulers;

import androidx.annotation.Keep;
import io.reactivex.rxjava3.core.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 网络请求统一管理
 * <p>
 * 开放类
 * 可使用反射 进行调用该类的所有函数
 */
@Keep
public class ServiceApiManager {
    IServiceApi serviceApi;

    {
        serviceApi = LibraryHttp.retrofitRequest().build(IServiceApi.class);
    }

    private static ServiceApiManager manager;

    public static ServiceApiManager getInstance() {
        if (manager == null)
            synchronized (ServiceApiManager.class) {
                if (manager == null)
                    manager = new ServiceApiManager();
            }
        return manager;
    }

    /**
     * 请求授权
     *
     * @param request 请求授权
     * @return 响应授权
     */
    @POST(UrlConfig.SIGN_REGISTER)
    public Flowable<String> requestSign(SignRequest request) {
        return serviceApi.requestSign(request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .filter((it) -> {
                    return true;
                })
                .observeOn(RxSchedulers.main());
    }

    /**
     * 请求 重命名飞机
     *
     * @param request 请求重命名
     * @return 响应重命名结果
     */
    @POST(UrlConfig.RENAME_AIRCRAFT)
    public Flowable<String> requestRenameAircraft(SignRenameRequest request) {
        return serviceApi.requestRenameAircraft(request)
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
    @POST(UrlConfig.WORKER_LOGIN)
    public Flowable<String> requestWorkerLogin(WorkerLoginRequest request) {
        return serviceApi.requestWorkerLogin(request)
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
    @POST(UrlConfig.WORKER_LOGOUT)
    public Flowable<String> requestWorkerLogout(WorkerLogoutRequest request) {
        return serviceApi.requestWorkerLogout(request)
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
    @POST(UrlConfig.WORKER_INFO)
    public Flowable<String> requestWorkerInfo(WorkerInfoRequest request) {
        return serviceApi.workerInfo(request)
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
    @POST(UrlConfig.UPLOAD_AIRCRAFT_INFO)
    public Flowable<String> requestUploadAircraftState(AircraftStateRequest request) {
        return ServiceCacheApiManager.getInstance().request(request, UrlConfig.UPLOAD_AIRCRAFT_INFO)
                .subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
//        return serviceApi.requestUploadAircraftState(request)
//                .flatMap(new DecodeBodyFunction<String>() {
//                })
//                .subscribeOn(RxSchedulers.request())
//                .observeOn(RxSchedulers.main());
    }

    /**
     * 获取当前无人机的推流地址
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.DEVICE_RTMP)
    public Flowable<String> requestDeviceRtmpPath(MediaPathRequest request) {
        return serviceApi.requestDeviceRtmpPath(request)
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
    @POST(UrlConfig.VERSION_INFO)
    public Flowable<String> requestAppVersionInfo(AppUpdateRequest request) {
        return serviceApi.requestAppVersionInfo(request)
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
    @POST(UrlConfig.ORDER_LIST)
    public Flowable<String> requestOrderList(OrderListRequest request) {
        return serviceApi.requestOrderList(request)
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
    @POST(UrlConfig.ORDER_DETAILS)
    public Flowable<String> requestOrderDetails(OrderDeviceRequest request) {
        return serviceApi.requestOrderDetails( request)
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
    @POST(UrlConfig.ORDER_UPDATE_STATE)
    public Flowable<String> requestUpdateOrderState(OrderDeviceStateRequest request) {
        return ServiceCacheApiManager.getInstance().request(request, UrlConfig.ORDER_UPDATE_STATE)
                .subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
//        return serviceApi.requestUpdateOrderState(request)
//                .flatMap(new DecodeBodyFunction<String>() {
//                })
//                .subscribeOn(RxSchedulers.request())
//                .observeOn(RxSchedulers.main());
    }

    /**
     * app结束工单
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.ORDER_FINISH)
    public Flowable<String> requestOrderFinish(OrderInterceptRequest request) {
        return serviceApi.requestOrderFinish(request)
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
    @POST(UrlConfig.UPLOAD_PIC)
    Call<HttpEntity> requestUploadPic(@Part("param") RequestBody param, @Part MultipartBody.Part uploadFile) {
        return serviceApi.requestUploadPic( param, uploadFile);
    }

    /**
     * 获取空域信息
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.FLY_ZONE)
    public Flowable<String> requestFlyZone(FlyZoneRequest request) {
        return serviceApi.requestFlyZone(request)
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
    @POST(UrlConfig.DOWNLOAD_PROTOCOL)
    public Flowable<String> requestFlyProtocol(LineDownloadRequest request) {
        return serviceApi.requestFlyProtocol(request)
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
    @POST(UrlConfig.DOWNLOAD_PROTOCOL_NEW)
    public Flowable<String> requestFlyProtocolNew(LineDownloadRequest request) {
        return serviceApi.requestFlyProtocolNew(request)
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
    @POST(UrlConfig.GROUP_LIST)
    public Flowable<String> requestLedgerGroup(LedgerLineRequest request) {
        return serviceApi.requestLedgerGroup(request)
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
    @POST(UrlConfig.GRID_LIST)
    public Flowable<String> requestLedgerGrid(GridRequest request) {
        return null;
    }
    /**
     * 台账：查询设备信息
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.DEVICE_LIST)
    public Flowable<String> requestLedgerDevice(LedgerDeviceRequest request) {
        return serviceApi.requestLedgerDevice(request)
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
    @POST(UrlConfig.ORDER_UPGRADE_PROTOCOL)
    public Flowable<String> upgradeProtocol(OrderUpgradeProtocolRequest request) {
        return serviceApi.upgradeProtocol(request)
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
    @POST(UrlConfig.ORDER_DEVICE_MODEL)
    public Flowable<String> orderDeviceModel(OrderDeviceModelRequest request) {
        return serviceApi.orderDeviceModel(request)
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
    @POST(UrlConfig.LEDGER_DEVICE_MODEL)
    public Flowable<String> ledgerDeviceModel(DeviceModelRequest request) {
        return serviceApi.ledgerDeviceModel(request)
                .flatMap(new DecodeBodyFunction<String>() {
                })
                .subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    @POST(UrlConfig.UPDATE_DEVICE)
    public Flowable<String> updateDeviceModel(UpdateDeviceRequest request) {
        return serviceApi.updateDevice(request)
                .flatMap(new DecodeBodyFunction<String>() {
                }).subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * 用户查看实时视频权限
     *
     * @return 响应
     */
    @POST(UrlConfig.VIDEO_POWER)
    public Flowable<String> requestVideoPower() {
        return serviceApi.videoPower()
                .flatMap(new DecodeBodyFunction<String>() {
                }).subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * 自定义 网络请求
     * 通过{@link com.lt.config.CustomUrlConfig}中的各个字段，通过本地拦截器进行path转换
     * @param request   请求对象
     * @param type   接口类型，对应的是{@link com.lt.config.CustomUrlConfig}中的各个字段
     * @return          x
     */
    @POST(CustomUrlConfig.PATH_BASE)
    public Flowable<String> customRequest(@Body Object request, @Header("custom_type") int type) {
        return serviceApi.customRequest(request,type)
                .flatMap(new DecodeBodyFunction<String>() {
                }).subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }
    /**
     * 审核人列表接口
     *
     * @param request 请求对象
     * @return x
     */
    @POST(UrlConfig.AUDITOR_LIST)
    public Flowable<String> auditorListRequest(AuditorListRequest request) {
        return serviceApi.auditorList(request)
                .flatMap(new DecodeBodyFunction<String>() {
                }).subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * 开始执行-更新工单持票人
     *
     * @param request 请求对象
     * @return x
     */
    @POST(UrlConfig.UPDATE_WORK_ORDER)
    @FormUrlEncoded
    public Flowable<String> updateWorkOrderRequest(UpdateWorkOrderRequest request) {
        return serviceApi.updateWorkOrder(request)
                .flatMap(new DecodeBodyFunction<String>() {
                }).subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * 完成任务-更新状态
     *
     * @param request 请求对象
     * @return x
     */
    @POST(UrlConfig.FINISH_TASK_UPDATE_STATUS)
    public Flowable<String> finishTaskUpdateStatusRequest(FinishTaskUpdateStatusRequest request) {
        return serviceApi.finishTaskUpdateStatus(request)
                .flatMap(new DecodeBodyFunction<String>() {
                }).subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    /**
     * 进入、退出离线化模式
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.OFFLINE_MODE)
    public Flowable<String> offlineMode(@Body OfflineRequest request) {
        return serviceApi.offlineMode(request)
                .flatMap(new DecodeBodyFunction<String>() {
                }).subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }
    /**
     * 数据补传
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.ORDER_UPDATE_STATE)
    public Flowable<String> requestData(RequestEntity request) {
        return ServiceCacheApiManager.getInstance().requestData(request,request.requestPath)
                .subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }
}
