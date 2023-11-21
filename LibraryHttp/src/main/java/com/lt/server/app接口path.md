1、请求app授权接口 {@link com.lt.entity.server.request.SignRequest}
    path:app/system/getSign
 2、无人机重命名接口 {@link com.lt.entity.server.request.SignRenameRequest}
    path:app/system/updateData
 3、用户登录 {@link com.lt.entity.server.request.WorkerLoginRequest}
    path:app/system/login
 4、用户登出 {@link com.lt.entity.server.request.WorkerLogoutRequest}
    path:app/system/logout
 5、上传当前无人机信息 {@link com.lt.entity.server.request.AircraftStateRequest}
    path:app/main/appReceiveAircraft
 6、获取当前无人机的推流地址 {@link com.lt.entity.server.request.MediaPathRequest}
    path:app/main/streamMediaUrl
 7、获取当前app版本升级信息{@link com.lt.entity.server.request.AppUpdateRequest}
    path:app/system/versionInfo
 8、获取工单列表 {@link com.lt.entity.server.request.OrderListRequest}
    path:app/main/orderList
9、获取工单详情 {@link com.lt.entity.server.request.OrderDeviceRequest}
    path:app/main/orderDetails
 10、工单执行状态同步 {@link com.lt.entity.server.request.OrderDeviceStateRequest}
    path:app/main/updateInspectStatus
 11、app结束工单 {@link com.lt.entity.server.request.OrderInterceptRequest}
    path:app/main/appFinishOrder
 12、app上传图片 {@link com.lt.entity.server.request.PicUploadRequest}
    path:app/main/appReceivePic
 13、获取空域信息 {@link com.lt.entity.server.request.FlyZoneRequest}
    path:app/main/flyAirSpace
 14、航线文件下载 {@link com.lt.entity.server.request.LineDownloadRequest}
    path:app/main/deviceProtocol
 15、台账：查询线路信息 {@link com.lt.entity.server.request.LedgerLineRequest}
    path:app/main/groupInfoList
 16、台账：查询线路中设备信息 {@link com.lt.entity.server.request.LedgerDeviceRequest}
    path:app/main/deviceInfoList
 17、上传精细学习数据(只支持有工单上传){@link com.lt.entity.server.request.OrderUpgradeProtocolRequest}
    path:app/main/upgradeProtocol
 18、获取工单设备精细学习模板{@link com.lt.entity.server.request.OrderDeviceModelRequest}
    path:app/workOrder/orderDeviceModel
 19、获取台账，即当前通用设备精细学习模板{@link com.lt.entity.server.request.DeviceModelRequest}
    path:app/lineLedger/ledgerDeviceModel
 20、设备坐标修正{@link com.lt.entity.server.request.UpdateDeviceRequest}
    path:app/main/updateDevice

#注意：所有请求 请求头中已包含：
1、当前用户guid   workerGuid
2、当前用户token  token
3、当前飞机sn码   deviceGuid
如有需要，从接口拦截器中拦截请求头进行处理