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



注意：
1、所有接口，固定请求头接受 当前的授权token 该token为 授权接口响应的字段数据，该token有效期为48小时
2、所有接口，除app安装包下载，图片上传外，全部为post请求，以form表单形式提交  param=xxxxxx
3、所有接口中数据，高度为海拔高度  经纬度坐标系为 WGS-84坐标系
4、所有加密，统一加密(http aes)，添加开关 即拦截器
5、所有接口，抽象的操作 必须放入拦截器做统一处理 如：当前设备授权token验证，当前用户guid验证等
6、所有接口，固定请求头 接受 管控平台登陆用户的guid
注意：
1、所有接口 如果有变动。需实时更新到：郭浩 刘红锁 崔蓝天 戈老师
2、当前接口当，需按照标准，贴入在线api
3、同样的属性使用固定的字段（例如userName，LineGuid）

注意：
无人机机身编码 与软件获取的serialNumber值是不一定一样的