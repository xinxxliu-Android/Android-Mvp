package com.lt.config

/**
 * socket 配置相关
 */

object SocketState {
    //已断开连接
    const val disconnected = -1

    //连接中
    const val connecting = 0

    //已连接
    const val connected = 1
}

/**
 * socket 状态
 */
object SocketMsgType {
    /**
     * 指令
     */
    const val EXECUTE = 1

    /**
     * 响应
     */
    const val REPLY = 0
}

object SocketError {
    /**
     * 正常状态 通用
     */
    const val normal = 0

    //异常    连接失败
    const val error_connect = 3

    //异常    读失败
    const val error_read = 4

    //异常    写失败
    const val error_write = 5

    //当前飞机未连接-通用异常状态
    const val device_disconnect = 6
}

/**
 * command = 1 的错误信息
 */
object TaskError1 {
    //异常-推流异常
    const val error_stream = 1001

    //异常-当前正在推流
    const val error_stream_task = 1002

}

/**
 * command = 3 的错误信息
 */
object TaskError3 {
    //异常-当前不在任务界面
    const val error_no_activity = 3001

    //异常-当前没有工单
    const val error_no_order = 3002

    //异常-当前没有开启rtk
    const val error_no_rtk = 3003

}

/**
 * command = 4 的错误信息
 */
object TaskError4 {
    //异常-当前不在任务界面
    const val error_no_activity = 4001

    //异常-当前没有起飞
    const val error_no_order = 4002
}

interface SocketConfig {
    enum class ALARM_TYPE(var type: Int, var content: String) {
        //正常飞行
        TYPE_0(0, "正常飞行"),  //闯入禁飞区告警
        TYPE_1(1, "闯入禁飞区告警"),  //无空域告警
        TYPE_2(2, "无空域告警"),  //无工单告警
        TYPE_3(3, "无工单告警"),  //偏航告警
        TYPE_4(4, "偏航告警"),  //非工单无人机告警
        TYPE_5(5, "非工单无人机告警");

    }

    /**
     * 推流相关异常
     */
    interface ErrorStream {
        companion object {
            /**
             * 当前未获取到推流url
             */
            const val stream_no_url = 7

            /**
             * 当前用户拒绝了推流权限
             * 仅在屏幕推流使用
             */
            const val no_permission = 8
        }
    }

    /**
     * 消息构建类型
     * 通用字典 请求、响应同步
     */
    interface Type {
        companion object {
            /**
             * 注册飞机至平台
             */
            const val register = 0

            /**
             * 推流-屏幕流
             */
            const val stream_screen = 1

            /**
             * 推流-fpv
             */
            const val stream_fpv = 2

            /**
             * 起飞
             */
            const val operate_up = 3

            /**
             * 降落
             */
            const val operate_down = 4

            /**
             * 警告
             */
            const val alarm = 5

            /**
             * 精细巡检
             */
            const val mission = 16

            /**
             * 跳转页面
             */
            const val start_intent = 20
        }
    }

    companion object {
        /**
         * socket 工作线程数量
         * 为保证 websocket 正常工作，收、发消息 线程独立于项目线程之外
         */
        const val work_pool_count = 3

        /**
         * socket 响应数据 回调线程池线程数
         * 用作：回调数据、回调状态
         */
        const val callback_pool_count = 3

        /**
         * socket 连接超时
         */
        const val connect_timeout = 10 * 1000

        /**
         * 是否自动重连
         */
        const val reconnect = true

        /**
         * 是否自动重连
         */
        const val reconnect_time = 1000
    }
}