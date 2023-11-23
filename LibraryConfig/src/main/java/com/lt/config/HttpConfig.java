package com.lt.config;

/**
 * 该类 定义了 所有网络请求 异常状态码 及 描述
 * 其他类型 待定
 */
public interface HttpConfig {
    interface ErrorCode {

        /**
         * 请求获取设备航迹接口
         */
        interface DeviceProtocol{
            /**
             * 没有数据内容 当前设备 无绑定的航迹
             */
            int NO_CONTENT = 446;
            /**
             * 没有当前设备 数据库中没有当前设备
             */
            int NO_DEVICE = 757;
        }

        /**
         * 请求获取设备航迹模版接口
         */
        interface DeviceModel{
            /**
             * 没有数据内容 当前设备无绑定的模版
             */
            int NO_CONTENT = 446;
            /**
             * 没有当前设备 数据库中没有当前涉笔
             */
            int NO_DEVICE = 757;
        }

        /**
         * 获取sign接口
         */
        interface Sign{
            /**
             * 授权失败,sign不存在
             */
            int NO_SIGN_CONTENT = 448;
        }
    }
}
