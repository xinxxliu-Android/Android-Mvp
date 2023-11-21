package com.lt;

import com.lt.config.CustomUrlConfig;
import com.lt.config.UrlConfig;
import com.lt.entity.HttpEntity;
import com.lt.server.request.AircraftStateRequest;
import com.lt.server.request.AppUpdateRequest;
import com.lt.server.request.AuditorListRequest;
import com.lt.server.request.DeviceModelRequest;
import com.lt.server.request.FinishTaskUpdateStatusRequest;
import com.lt.server.request.FlightCrossInfoRequest;
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
import com.lt.server.request.QueryInspectNumRequest;
import com.lt.server.request.SignRenameRequest;
import com.lt.server.request.SignRequest;
import com.lt.server.request.UpdateDeviceRequest;
import com.lt.server.request.UpdateWorkOrderRequest;
import com.lt.server.request.WorkerInfoRequest;
import com.lt.server.request.WorkerLoginRequest;
import com.lt.server.request.WorkerLogoutRequest;

import io.reactivex.rxjava3.core.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 项目中 所有接口请求归属该类
 */
public interface IServiceApi {

    /**
     * 请求授权
     *
     * @param request 请求授权
     * @return 响应授权
     */
    @POST(UrlConfig.SIGN_REGISTER)
    Flowable<Response<ResponseBody>> requestSign(@Body SignRequest request);

    /**
     * 请求 重命名飞机
     *
     * @param request 请求重命名
     * @return 响应重命名结果
     */
    @POST(UrlConfig.RENAME_AIRCRAFT)
    Flowable<Response<ResponseBody>> requestRenameAircraft(@Body SignRenameRequest request);

    /**
     * 飞手登陆
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.WORKER_LOGIN)
    Flowable<Response<ResponseBody>> requestWorkerLogin(@Body WorkerLoginRequest request);

    /**
     * 飞手登出
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.WORKER_LOGOUT)
    Flowable<Response<ResponseBody>> requestWorkerLogout(@Body WorkerLogoutRequest request);

    /**
     * 飞手信息
     */
    @POST(UrlConfig.FLYER_INFO)
    Flowable<Response<ResponseBody>> requestFlyerInfo(@Body WorkerInfoRequest request);

    /**
     * 上传当前无人机信息
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.UPLOAD_AIRCRAFT_INFO)
    Flowable<Response<ResponseBody>> requestUploadAircraftState(@Body AircraftStateRequest request);

    /**
     * 获取当前无人机的推流地址
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.DEVICE_RTMP)
    Flowable<Response<ResponseBody>> requestDeviceRtmpPath(@Body MediaPathRequest request);

    /**
     * 获取当前app版本升级信息
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.VERSION_INFO)
    Flowable<Response<ResponseBody>> requestAppVersionInfo(@Body AppUpdateRequest request);

    /**
     * 获取工单列表
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.ORDER_LIST)
    Flowable<Response<ResponseBody>> requestOrderList(@Body OrderListRequest request);

    /**
     * 获取工单详情
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.ORDER_DETAILS)
    Flowable<Response<ResponseBody>> requestOrderDetails(@Body OrderDeviceRequest request);

    /**
     * 工单执行状态同步
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.ORDER_UPDATE_STATE)
    Flowable<Response<ResponseBody>> requestUpdateOrderState(@Body OrderDeviceStateRequest request);

    /**
     * app结束工单
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.ORDER_FINISH)
    Flowable<Response<ResponseBody>> requestOrderFinish(@Body OrderInterceptRequest request);

    /**
     * 上传图片
     *
     * @param param      参数
     * @param uploadFile 文件
     * @return 结果
     */
    @Multipart
    @POST(UrlConfig.UPLOAD_PIC)
    Call<HttpEntity> requestUploadPic(@Part("param") RequestBody param, @Part MultipartBody.Part uploadFile);

    /**
     * 获取空域信息
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.FLY_ZONE)
    Flowable<Response<ResponseBody>> requestFlyZone(@Body FlyZoneRequest request);

    /**
     * 航线文件下载
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.DOWNLOAD_PROTOCOL)
    Flowable<Response<ResponseBody>> requestFlyProtocol(@Body LineDownloadRequest request);

    /**
     * 新航线文件下载
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.DOWNLOAD_PROTOCOL_NEW)
    Flowable<Response<ResponseBody>> requestFlyProtocolNew(@Body LineDownloadRequest request);

    /**
     * 台账：查询分组信息
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.GROUP_LIST)
    Flowable<Response<ResponseBody>> requestLedgerGroup(@Body LedgerLineRequest request);

    /**
     * 台账：查询网格信息
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.GRID_LIST)
    Flowable<Response<ResponseBody>> requestLedgerGrid(@Body GridRequest request);

    /**
     * 台账：查询设备信息
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.DEVICE_LIST)
    Flowable<Response<ResponseBody>> requestLedgerDevice(@Body LedgerDeviceRequest request);

    /**
     * 上传精细学习的航迹
     *
     * @param request 请求
     * @return 相应
     */
    @POST(UrlConfig.ORDER_UPGRADE_PROTOCOL)
    Flowable<Response<ResponseBody>> upgradeProtocol(@Body OrderUpgradeProtocolRequest request);

    /**
     * 获取工单设备精细学习模板
     *
     * @param request 请求
     * @return 相应
     */
    @POST(UrlConfig.ORDER_DEVICE_MODEL)
    Flowable<Response<ResponseBody>> orderDeviceModel(@Body OrderDeviceModelRequest request);

    /**
     * 获取台账，即当前通用设备精细学习模板
     *
     * @param request 请求
     * @return 相应
     */
    @POST(UrlConfig.LEDGER_DEVICE_MODEL)
    Flowable<Response<ResponseBody>> ledgerDeviceModel(@Body DeviceModelRequest request);

    /**
     * 修正设备，修正设备信息
     *
     * @param request
     * @return
     */
    @POST(UrlConfig.UPDATE_DEVICE)
    Flowable<Response<ResponseBody>> updateDevice(@Body UpdateDeviceRequest request);

    /**
     * 飞手登陆
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.WORKER_INFO)
    Flowable<Response<ResponseBody>> workerInfo(@Body WorkerInfoRequest request);

    /**
     * 用户查看实时视频权限
     *
     * @return 响应
     */
    @POST(UrlConfig.VIDEO_POWER)
    Flowable<Response<ResponseBody>> videoPower();

    /**
     * 飞行日志上传
     *
     * @param
     * @param token
     * @param sign
     * @param uploadFile
     * @return
     */
    @POST(UrlConfig.UPLOAD_FLY_LOG)
    Call<HttpEntity> uploadFlyLog(@Header("Blade-Auth") String token, @Header("sign") String sign, @Body RequestBody uploadFile);

    /**
     * 本地日志上传
     *
     * @param
     * @param token
     * @param sign
     * @param uploadFile
     * @return
     */
    @POST(UrlConfig.UPLOAD_LOG_FILE)
    Call<HttpEntity> uploadLogFile(@Header("Blade-Auth") String token, @Header("sign") String sign, @Body RequestBody uploadFile);

    /**
     * 飞行交跨
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.FLIGHT_CROSS_INFO)
    Flowable<Response<ResponseBody>> flightCrossInfo(@Body FlightCrossInfoRequest request);

    /**
     * 自定义 网络请求
     * 通过{@link CustomUrlConfig}中的各个字段，通过本地拦截器进行path转换
     *
     * @param request 请求对象
     * @param type    接口类型，对应的是{@link CustomUrlConfig}中的各个字段
     * @return x
     */
    @POST(CustomUrlConfig.PATH_BASE)
    Flowable<Response<ResponseBody>> customRequest(@Body Object request, @Header("custom_type") int type);

    /**
     * 进入、退出离线化模式
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.OFFLINE_MODE)
    Flowable<Response<ResponseBody>> offlineMode(@Body OfflineRequest request);

    /**
     * 审核人列表接口
     *
     * @param userId 用户id
     * @return 响应
     */
    @POST(UrlConfig.AUDITOR_LIST)
    Flowable<Response<ResponseBody>> auditorList(@Body AuditorListRequest userId);


    /**
     * 开始执行-更新工单持票人
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.UPDATE_WORK_ORDER)
    Flowable<Response<ResponseBody>> updateWorkOrder(@Body UpdateWorkOrderRequest request);


    /**
     * 完成任务-更新状态
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.FINISH_TASK_UPDATE_STATUS)
    Flowable<Response<ResponseBody>> finishTaskUpdateStatus(@Body FinishTaskUpdateStatusRequest request);

    /**
     * 已完成未完成
     *
     * @param request 请求
     * @return 响应
     */
    @POST(UrlConfig.QUEERY_INSPECT_NUM)
    Flowable<Response<ResponseBody>> queryInspectNum(@Body QueryInspectNumRequest request);
}
