package com.lt.server.request;

import java.util.List;

/**
 * 无人机状态信息集合数据类。
 * path：app/uavData/appReceiveAircraft
 * 该实体类为 上传当前app信息的类
 * 需求：添加新版本接口，用以上传当前无人机状态信息，及当前飞行状态信息、任务执行信息等状态，以新数据接口上传，方便前、后端模块化解耦，方便后期扩展
 * 详情：
 * 1、添加新版本接口。
 * 2、接口内容：
 * 目前包含：
 * 1、无人机当前信息
 * 2、云台当前信息
 * 3、电池当前信息
 * 4、遥控器当前信息
 * 5、当前巡检任务及状态信息
 * 6、其他可扩展信息。
 * 设计架构：
 * 1、每个信息单独对应字段
 * 2、其中：1，4，5，6 为 JsonObject对象，2，3为JsonArray数据结构
 * 3、其中：6为JsonObject对象，解析时，后台可暂用String解析，方便后期添加自定义的其他类型状态及信息
 * 具体实施：
 * 1、flight-字段 无人机当前信息
 * 2、gimbal-字段 云台当前信息
 * 3、battery-电池    电池当前信息
 * 4、remote-遥控器    遥控器当前信息
 * 5、task-当前巡检任务及状态信息
 * 6、other-其他备用扩展信息
 */
public final class AircraftStateRequest {
    /**
     * 无人机飞机信息
     */
    public FlightState flight;
    /**
     * 云台信息
     * 可能有多个云台
     */
    public List<GimbalState> gimbal;
    /**
     * 电池信息
     * 可能有多个电池
     */
    public List<BatteryState> battery;
    /**
     * 遥控器信息
     */
    public RemoteState remote;
    /**
     * 用户注册授权、登陆、大疆账号相关
     */
    public WorkerState worker;
    /**
     * 飞行任务相关
     */
    public TaskState task;


    /**
     * 无人机信息
     */
    public final class FlightState {
        /**
         * 无人机唯一识别码
         */
        public String serialNumber;
        /**
         * 无人机型号  如：Phantom 4 RTK
         */
        public String modelName;
        /**
         * 当前飞行信息：
         * 如 可以起飞(RTK)  正在飞行(RTK)
         * 注意：该字段长度会有很大差异 如果web端进行显示，注意控制显示宽度
         * 比如：恶劣环境时，会显示为： 风速很高,谨慎飞行并确保飞机保持在视线范围内 或 大风,谨慎飞行并确保飞机保持在视线范围内
         */
        public String flyStr;
        /**
         * 飞行状态
         * 0 没飞、1 在飞、 2 飞完 3.飞航线  4.下载图 5.上传图
         */
        public int flyState;
        /**
         * longitude; latitude; altitude;
         * 经度，纬度，海拔高度
         */
        public double lo, la, al;
        /**
         * 返航点坐标 可能为0 即 无效值
         */
        public double hlo, hla, hal;
        /**
         * gps卫星数量
         */
        public int gpsCount;
        /**
         * hSpeed:当前飞机水平速度
         * vSpeed:当前飞机垂直速度
         */
        public double hSpeed, vSpeed;
        /**
         * 当前rtk是否已连接
         * -1 不支持 0 未打开开关 1 未连接 2 已连接使用
         */
        public int rtkState;
        /**
         * 航向角 基于地磁北方向的角度
         * 180 - -180
         */
        public double yaw;
        /**
         * 最近的架次 飞行的总距离
         */
        public double distance;
        /**
         * 从最近一次起飞后，飞行的时长 （一个架次的总飞行时长）
         * 该字段不需要进行求和计算，始终为 最近起飞到当前的飞行时长
         * 单位：秒
         */
        public long flySeconds;
    }
    /**
     * 云台信息
     */
     public final class GimbalState {
        /**
         * 云台唯一识别码
         */
        public String serialNumber;
        /**
         * 云台中文描述名 如果 H20T
         */
        public String displayName;
        /**
         * 俯仰角,横滚角，航向角
         */
        public int pitch, roll, yaw;
    }

    /**
     * 电池信息
     * 飞机电池，非遥控器电池
     */
    public final class BatteryState {
        /**
         * 电池唯一识别码
         */
        public String serialNumber;
        //电压 mv
        public int voltage;
        //电流 负数放电 ma
        public int current;
        //剩余电量百分比
        public int chargeRemainingInPercent;
        //最大电量  ma
        public int fullChargeCapacity;
        //充电次数
        public int numberOfDischarges;
        //完美最大电量  设计电量   ma
        public int designCapacity;
        //寿命    百分比
        //在不受支持的产品中，该值始终为 0
        public int lifetimeRemaining;
        //电池温度
        public double temperature;
    }

    /**
     * 遥控器信息
     */
    public final class RemoteState {
        /**
         * 遥控器唯一识别码
         */
        public String serialNumber;
        /**
         * 当前剩余电量 ma
         */
        public int remainingChargeInmAh;
        /**
         * 当前剩余电量百分比
         */
        public int remainingChargeInPercent;
        /**
         * 是否正在充电
         */
        public boolean charging;
        /*
         * 飞控模式(如果当前未连接无人机，则字段为null)
         */
        public int flight_mode;
        /**
         * 当前遥控器位置
         * 高度为 海拔高度
         */
        public double la, lo, al;
        /**
         * 当前遥控器信号强度(百分比)
         */
        public int signalLevel;
    }

    /**
     * 当前飞行任务信息数据
     */
    public final class TaskState {

        /**
         * 当前任务类型
         * 0-实时任务
         * 1-补传任务
         */
        public int taskType;

        /**
         * 架次guid
         * 每次起飞时，更新该字段
         */
        public String flyGuid;
        /**
         * 起飞时间 毫秒
         * 最近的一次起飞时间
         */
        public long launchTime;
        /**
         * 降落时间 毫秒
         * 如果当前飞机正在飞行，则该字段为0
         * 如果当前飞机着陆后，该字段为飞机最后着陆时的时间
         * 如果当前飞机刚打开，未起飞过，该字段为0
         */
        public long landTime;
        /**
         * 工单guid 如果当前正在执行工单，则为工单id  否则为空
         */
        public String inspectGuid;
        /**
         * 设备识别码  设备信息  如果有的话。否则为空
         * 可作为 杆塔识别码  如果是新能源  可以作为 风机识别码
         */
        public String deviceGuid;
        /**
         * 当前巡检的设备类型
         *  杆塔 光伏 风机等
         * 0 配电杆塔
         * 1 输电杆塔
         * 2 太阳能板
         * 3 风机
         */
        public int deviceType;
        /**
         * 分组/线路识别码
         *
         * 如果有的话，否则为空
         */
        public String groupGuid;

        /**
         * 当前航点已执行数 如果有的话
         * 支持模式参考{@link #missionType}
         * 注意当前支持模式为：
         * 精细巡检，精细巡检红外，通道巡检，通道巡检红外
         * 否则，该字段值无效
         */
        public int missionCount;

        /**
         * 当前执行的航线任务 航点总数
         * 支持模式参考{@link #missionType}
         * 注意当前支持模式为：
         * 精细巡检，精细巡检红外，通道巡检，通道巡检红外
         * 否则，该字段值无效
         */
        public int totalMissionCount;

        /**
         * 当前任务执行类型：
         *  -1未开始巡检
         * 0：精细巡检
         * 1、精细巡检红外
         * 2、采集航线
         * 3、通道巡检
         * 4、通道巡检红外
         * 5、手动巡检
         * 6、手动巡检红外
         */
        public int missionType;
        /**
         * 画面实时最高温度
         */
        public double highTemperature;
        /**
         * 画面实时最高温度 坐标(像素坐标)
         */
        public double hPositionX, hPositionY;
        /**
         * 画面实时最低温度
         */
        public double lowTemperature;
        /**
         * 画面实时最低温度 坐标(像素坐标)
         */
        public double lPositionX, lPositionY;
        /**
         * 当前任务已执行了的拍照点数量
         */
        public int takePhotoIndex;
        /**
         * 当前任务的拍照点数量总数
         */
        public int takePhotoCount;
        /**
         * 当前飞机正在飞往的航点的类型
         *     1：拍照点，2：安全点，0：无(未起飞或任务执行完毕)
         */
        public int nowPointType;
    }

    /**
     * app登陆的相关信息
     */
    public final class WorkerState {
        /**
         * appCode  暂用 飞机sn码
         */
        public String appCode;
        /**
         * 飞手识别码 如果有的话
         */
        public String workerGuid;
        /**
         * 当前飞机登陆的大疆账号
         * 如果未登陆大疆账号，则该字段为空值
         */
        public String djUser;
    }
}










