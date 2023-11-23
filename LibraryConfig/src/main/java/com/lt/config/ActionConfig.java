package com.lt.config;

/**
 * 所有页面 路径常量配置
 * 调用start页面时，全部使用隐士intent 禁止使用class调用
 */
@SuppressWarnings("unused")
public interface ActionConfig {
    /**
     * 主模块相关界面 action
     */
    interface Main {
        /**
         * 主页面 mainActivity
         */
        String ACTION_MAIN = BuildConfig.PROJECT_PACKAGE_NAME + ".AC_MAIN";
        /**
         * 无人机自检界面
         */
        String ACTION_FLIGHT_CHECK = BuildConfig.PROJECT_PACKAGE_NAME + ".FLIGHT_CHECK";
        /**
         * 云台参数界面
         */
        String ACTION_GIMBAL_PARAMS = BuildConfig.PROJECT_PACKAGE_NAME + ".GIMBAL_PARAMS";

        /**
         * 同步资源界面
         */
        String ACTION_SYNC_SOURCE = BuildConfig.PROJECT_PACKAGE_NAME + ".SYNC_SOURCE";
        /**
         * 禁飞区界面
         */
        String ACTION_NO_FLY = BuildConfig.PROJECT_PACKAGE_NAME + ".NO_FLY";

        /**
         * 飞行记录界面
         */
        String ACTION_FLY_HISTORY = BuildConfig.PROJECT_PACKAGE_NAME + ".FLY_HISTORY";
        /**
         * 飞手界面
         */
        String ACTION_FLY_WORKER = BuildConfig.PROJECT_PACKAGE_NAME + ".FLY_WORKER";
    }

    interface Settings {
        String ACTION_FLY_SETTINGS = BuildConfig.PROJECT_PACKAGE_NAME + ".FLY_SETTINGS";
    }

    /**
     * 应用核心模块
     */
    interface System {
        /**
         * 工单任务界面
         */
        String ACTION_ORDER = BuildConfig.PROJECT_PACKAGE_NAME + ".ORDER";
        /**
         * 工单任务详情界面
         */
        String ACTION_ORDER_DETAIL = BuildConfig.PROJECT_PACKAGE_NAME + ".ORDER_DETAIL";
        /**
         * 已完成工单
         */
        String ACTION_ORDER_HISTORY = BuildConfig.PROJECT_PACKAGE_NAME + ".ORDER_HISTORY";
        /**
         * 巡视功能界面
         */
        String ACTION_FUNCTION = BuildConfig.PROJECT_PACKAGE_NAME + ".FUNCTION";
        /**
         * 数据传输界面
         */
        String ACTION_DATA_SOURCE = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_DATA_SOURCE";
        /**
         * 飞行记录详情
         */
        String ACTION_FLIGHT_RECORD = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_FLIGHT_RECORD";
    }

    /**
     * 数据传输模块
     */
    interface Transmission {
        /**
         * 历史记录界面
         */
        String ACTION_TRANSMISSION = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_TRANSMISSION";
        /**
         * 数据传输详情界面  即 上传下载图片界面
         */
        String ACTION_TRANSMISSION_DETAILS = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_TRANSMISSION_DETAILS";
        /**
         * 批量下载图片页面
         */
        String ACTION_TRANSMISSION_DETAILS_V2 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_TRANSMISSION_DETAILS_V2";
        /**
         * 查看大图界面
         */
        String ACTION_TRANSMISSION_PHOTO_ALBUM = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_TRANSMISSION_PHOTO_ALBUM";
    }

    /**
     * 欢迎模块相关界面
     */
    interface Welcome {
        /**
         * 蒙东人巡机巡选择界面
         */
        String CHOOSE = BuildConfig.PROJECT_PACKAGE_NAME + ".CHOOSE";
        /**
         * 欢迎界面 首屏界面
         */
        String WELCOME = BuildConfig.PROJECT_PACKAGE_NAME + ".WELCOME";
        /**
         * 欢迎页面 登陆页面
         */
        String LOGIN = BuildConfig.PROJECT_PACKAGE_NAME + ".LOGIN";
    }

    /**
     * 浏览器界面
     */
    interface Browser {
        String ACTION_BROWSER = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_BROWSER_ACTION";
    }

    /**
     * 巡视任务模块
     */
    interface Mission {

        /**
         * 精细学习 界面
         */
        String ACTION_MISSION_0 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_0";
        /**
         * 精细巡视 界面
         */
        String ACTION_MISSION_1 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_1";

        /**
         * 精细巡视-红外 界面
         */
        String ACTION_MISSION_2 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_2";

        /**
         * 手动巡视 界面
         */
        String ACTION_MISSION_3 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_3";

        /**
         * 手动巡视-红外 界面
         */
        String ACTION_MISSION_4 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_4";

        /**
         * 通道巡视 界面
         */
        String ACTION_MISSION_5 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_5";

        /**
         * 通道巡视-红外 界面
         */
        String ACTION_MISSION_6 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_6";

        /**
         * 快速通道巡视 界面
         */
        String ACTION_MISSION_7 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_7";

        /**
         * 快速通道巡视-红外 界面
         */
        String ACTION_MISSION_8 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_8";
        /**
         * 树障巡视 界面
         */
        String ACTION_MISSION_9 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_9";

        /**
         * 树障巡视-红外 界面
         */
        String ACTION_MISSION_10 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_10";
        /**
         * 杆塔坐标修正 界面
         */
        String ACTION_MISSION_11 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_11";
        /**
         * 新建线路 界面
         * 已过时 该巡检模式 不适用于APP端
         */
        String ACTION_MISSION_12 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_12";
        /**
         * 点云采集 界面
         */
        String ACTION_MISSION_13 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_13";
        /**
         * 弓子航带扫描 界面
         */
        String ACTION_MISSION_14 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_14";

        /**
         * 点云采集-激光 界面
         */
        String ACTION_MISSION_17 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_17";
        /**
         * 手动通道巡检
         * 暂未开发
         */
        String ACTION_MISSION_18 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_18";
        /**
         * 光伏巡检
         */
        String ACTION_MISSION_19 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_19";
        /**
         * 风机巡检
         * 新能源使用因诺app巡检
         */
        String ACTION_MISSION_20 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_20";
        /**
         * 网格化巡检
         *
         */
        String ACTION_MISSION_21 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_21";

        /**
         * 航带巡检 区域巡检
         * 巡检两个点之间的 设置的区域范围
         */
        String ACTION_MISSION_23 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_23";
        /**
         * 风机坐标修正
         * TODO 仅供测试使用
         */
        String ACTION_MISSION_24 = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_24";

        /**
         * 风机数据手动填充
         * TODO 仅供测试风机使用
         */
        String ACTION_MISSION_EDIT = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_MISSION_EDIT";

    }

    /**
     * 服务action
     */
    interface Service {
        /**
         * 飞手服务
         * 飞手状态、登陆、登出
         */
        String ACTION_WORKER = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_WORKER";
        /**
         * 安全交互平台服务
         * 安全交互平台连接成功及连接互联网大区
         */
        String ACTION_SAFE = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_SAFE";
        /**
         * 更新服务
         * 强制更新app本版
         */
        String ACTION_FORCE_UPDATE = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_FORCE_UPDATE";
        /**
         * Socket服务
         * 开始Socket长连接
         */
        String ACTION_SOCKET = BuildConfig.PROJECT_PACKAGE_NAME + ".ACTION_SOCKET";
    }
}
