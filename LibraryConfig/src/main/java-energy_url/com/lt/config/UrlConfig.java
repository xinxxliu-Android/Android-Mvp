package com.lt.config;

/**
 * 当前项目 所有 网络 url相关常量信息
 */
public interface UrlConfig {
    /**
     * 查询pad授权信息
     */
    String PORT_AUTHORIZATION = "app/auth/appCheck";
    /**
     * 根据sn获取sign
     */
    String SIGN_REGISTER = "app/system/getSign";

    /**
     * 重命名无人机接口
     */
    String RENAME_AIRCRAFT = "app/system/updateData";

    /**
     * 管控平台登陆 即 飞手登陆
     */
    String WORKER_LOGIN = "app/system/appLogIn";
    /**
     * 管控平台 登出  即 飞手登出
     */
    String WORKER_LOGOUT = "app/system/appLogOut";
    /**
     * 飞手信息接口
     */
    String FLYER_INFO = "/android/operator/info";
    /**
     * 根据飞机id 获取推流地址
     */
    String DEVICE_RTMP = "app/business/streamMediaUrl";

    /**
     * 查询工单列表
     */
    String ORDER_LIST = "app/main/orderList";
    /**
     * 工单巡检设备查询
     */
    String ORDER_DETAILS = "app/main/orderDetails";
    /**
     * 工单执行状态上报
     */
    String ORDER_UPDATE_STATE = "app/main/updateInspectStatus";
    /**
     * 结束工单
     */
    String ORDER_FINISH = "aapp/main/appFinishOrder";
    /**
     * 上传图片接口
     */
    String UPLOAD_PIC = "app/main/appReceivePic";
    /**
     * 获取当前空域信息
     */
    String FLY_ZONE = "app/main/flyAirSpace";
    /**
     * 下载航线
     */
    String DOWNLOAD_PROTOCOL = "app/main/deviceProtocol";
    /**
     * 新下载航线接口
     * 根据设备guid获取当前设备下的所有航线
     */
    String DOWNLOAD_PROTOCOL_NEW = "android/main/deviceProtocolNew";
    /**
     * 无人机数据实时上传
     */
    String UPLOAD_AIRCRAFT_INFO = "app/main/appReceiveAircraft";
    /**
     * 获取当前app版本升级信息
     */
    String VERSION_INFO = "app/system/versionInfo";
    /**
     * 台账 获取分组信息 可用作 获取线路信息
     */
    String GROUP_LIST = "app/main/groupInfoList";
    /**
     * 获取 分组中的设备信息 可用作 获取线路中的杆塔信息
     */
    String DEVICE_LIST = "app/main/deviceInfoList";
    /**
     * 获取工单设备精细学习模板
     */
    String ORDER_DEVICE_MODEL = "app-server/android/main/orderDeviceModel";
    /**
     * 获取台账，即当前通用设备精细学习模板
     */
    String LEDGER_DEVICE_MODEL = "app-server/android/main/ledgerDeviceModel";
    /**
     * 上传 精细学习数据 注意 必须工单下发的任务彩壳上传 否则不允许
     */
    String ORDER_UPGRADE_PROTOCOL = "app-server/android/main/upgradeProtocol";
    /**
     * 修正设备，修正设备信息
     */
    String UPDATE_DEVICE = "app-server/android/main/updateDevice";
    /**
     * 获取飞手信息（重庆专用）
     */
    String WORKER_INFO = "app-server/android/operator/info";
    /**
     * 获取网格台账数据
     */
    String GRID_LIST = "app-server/android/main/getAllFixedGrid";
    /**
     * 申请进入/退出离线化模式
     */
    String OFFLINE_MODE = "app/system/offlineModel";
    /**
     * 审核人列表接口
     * GET请求
     */
    String AUDITOR_LIST = "android/main/userListByRoleCode";

    /**
     * 开始执行-更新工单持票人
     */
    String UPDATE_WORK_ORDER = "android/main/updateInspectUserInfo";

    /**
     * 完成任务-更新状态
     */
    String FINISH_TASK_UPDATE_STATUS = "android/main/finishTask";

    /**
     * 获取用户查看实时视频权限（蒙东使用）
     */
    String VIDEO_POWER = "app/system/videoPower";

    /**
     * 飞行日志上传地市平台（重庆使用）
     */
    String UPLOAD_FLY_LOG = "app-server/android/main/uploadFLyPartLog";

    /**
     * 本地日志上传到平台
     */
    String UPLOAD_LOG_FILE = "android/main/uploadLogFile";

    /**
     * 交跨信息（重庆使用）
     */
    String FLIGHT_CROSS_INFO = "android/main/flightCrossInfo";

    /**
     * 查询工单已完成未完成数（重庆使用）
     */
    String QUEERY_INSPECT_NUM = "android/main/queryInspectNum";
}
