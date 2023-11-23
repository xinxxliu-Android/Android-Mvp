package com.lt.config;

/**
 * sp key 默认value name等
 */
public interface SPConfig {
    // 用户名
    String USER_NAME = "USER_NAME";
    // 用户密码
    String USER_PWD = "USER_PWD";
    //飞手单位id
    String DEPT_CODE = "DEPT_CODE";
    //飞手名称
    String WORK_NAME = "WORK_NAME";
    //i国网登录ticket
    String TICKET = "TICKET";

    interface Photo {
        String KEY = "project_photo";
        String COMPRESS_ENABLE = "compressEnable";
        String COMPRESS_LEVEL = "compressLevel";
    }

    /**
     * 设置界面
     */
    interface Settings {
        /**
         * settings模块 key
         */
        String NAME = "settings_module";
        /**
         * 设置 是否自动上传图片开关 true false
         * 该字段影响 上传下载图片详情界面ui及响应事件
         */
        String AUTO_UPDATE_PIC = "AUTO_UPDATE_PIC";

        /**
         * 设置 是否边下载边上传 true false
         * 该字段影响 上传下载图片详情界面ui及响应事件
         */
        String DOWNLOAD_UPDATE_PIC = "DOWNLOAD_UPDATE_PIC";

        /**
         * 设置 是否开启图片压缩 true false
         * 该字段影响 上传下载图片详情界面ui及响应事件
         */
        String IMAGE_COMPRESSION = "IMAGE_COMPRESSION";

        /**
         * 开启压缩后，使用压缩方式
         * 0 质量压缩, 1 中科压缩
         */
        String COMPRESS_TYPE = "COMPRESS_TYPE";

        /**
         * 用户设置界面
         */
        interface User {
            // app更新时间
            String UPLOAD_APP_TIME = "upload_app_time";
            /**
             * 当前是否处于离线化模式
             */
            String OFFLINE_MODE = "offline_Mode";

            /**
             * 允许离线化的时长
             */
            String OFFLINE_MODE_TIME = "offline_mode_time";

            /**
             * 服务器接口地址
             */
            String SERVER_URL = "server_url";

            /**
             * 语音播放状态
             */
            String VOICE_PLAY_STATUS = "voice_play_status";

            /**
             * 语音播放默认关闭
             */
            boolean VOICE_PLAY_STATUS_DEFAULT = false;
        }

        /**
         * rtk界面
         */
        interface RTK {
            String NAME = "rtk_settings";
            String IP = "ip";
            String PORT = "port";
            String MOUNT_POINT = "mountPoint";
        }

        /**
         * 飞机信息
         */
        interface Flight {

            //默认塔间速度
            int FLIGHT_BETWEEN_SPEED = 10;
            //默认绕塔速度
            int FLIGHT_AROUND_SPEED = 3;


            String NAME = "flight_info";
            // 飞机塔间速度
            String FLIGHT_BETWEEN = "flight_between";
            // 飞机绕塔速度
            String FLIGHT_AROUND = "flight_around";
        }

        /**
         * 避障设置
         */
        interface Avoiding {
            String NAME = "avoiding_name";
            // 是否显示雷达图
            String AVOIDING_RADAR = "avoiding_radar";
        }

        /**
         * 飞行设置
         */
        interface Fly {

            String NAME = "fly_info";
            // 航点间航向角设置
            String FLY_HEADING_MODE = "fly_heading_mode";
        }

        /**
         * 巡检配置设置
         */
        interface mission{
            // 飞行时采用高度模式设置
            String MISSION_FLY_HEIGHT_MODEL = "mission_fly_height_model";
        }
    }

    ;

    /**
     * 飞手信息
     */
    interface Worker {

        String NAME = "worker_user_info";

        // 用户guid
        String USER_GUID = "USER_GUID";
    }

    /**
     * 网格巡检模式
     */
    interface GridMission {
        String MISSION = "mission_type";
        //自主巡检
        String MISSION1 = "mission_type_1";
        //手动巡检
        String MISSION3 = "mission_type_3";
        //自主巡检
        String MISSION21_1 = "mission_type_21_1";
        //手动巡检
        String MISSION21_3 = "mission_type_21_3";
    }

    /**
     * 推流方式
     */
    interface RtmpStream {
        /**
         * 存储推流方式key
         */
        String RTMP_STREAM_KEY = "rtmp_stream_key";

        /**
         * 屏幕推流方式
         */
        String RTMP_STREAM_SCREEN = "rtmp_stream_screen";

        /**
         * FPV推流方式
         */
        String RTMP_STREAM_FPV = "rtmp_stream_fpv";

        /**
         * 推流相关 sp存储 文件名 即 {@link android.content.Context#getSharedPreferences(String, int)}中的 name
         */
        String KEY = "rtmp_sp_key";

        /**
         * 推流相关 rtmp推流 推流地址
         * 值类型为 string
         */
        String RTMP_URL = "rtmp_url_value";
    }

    /**
     * sdk相关
     */
    interface SDK {
        /**
         * 大疆sdk相关 sp的name
         */
        String KEY_NAME = "sdk_name_key";

        /**
         * 无人机sn码缓存key
         */
        String VALUE_FLIGHT_SN = "VALUE_FLIGHT_SN";
    }

    /**
     * 设置固件信息修改别名
     */
    interface Firmware {
        String FirmwareRename = "firmware_rename";
    }


}
