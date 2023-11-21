package com.lt.server.response;

import static com.lt.config.MissionModeConfig.*;

import com.blankj.utilcode.util.StringUtils;
import com.lt.config.ActionConfig;

import java.io.Serializable;
import java.util.List;

/**
 * 查询工单信息
 * path：app/workOrder/orderList
 * 获取工单列表
 */
public class OrderListResponse {
    /**
     * 工单列表
     */
    public List<Order> data;

    public static class Order implements Serializable {
        /**
         * 工单类型
         * 0:自由接取任务类型(现有类型)
         * 1：主动接取任务类型，接受后，其他人不可再执行该工单，只可以查看
         */
        public int orderType;
        /**
         * 工单guid
         */
        public String inspectGuid;
        /**
         * 工单名
         */
        public String inspectName;

        //工作班名称
        public String workerGroupName;
        /**
         * 签发人
         */
        public String signer;
        /**
         * 审核人
         */
        public String appRover;
        /**
         *  持票人
         */
        public String bearerGuid;
        /**
         * 计划工作许可时间
         * 一定是 最后一个流程确定的时间
         * 即 最后一个人签完字后 即可开始执行工单 这个签字时间
         */
        public long planPermitTime;
        /**
         * 工单计划工作开始时间
         */
        public long planStartTime;
        /**
         * 工单计划工作结束时间 可以为0 则 不限制超时
         */
        public long planEndTime;

        /**
         * 无人机设备编号 即 当前工单授权的无人机设备编号
         * 可能允许多个设备接受工单
         * 如果为空，则为 可任意使用无人机
         */
        public List<String> uavEquipmentNo;

        /**
         * 工作许可人 名字
         */
        public String workPermitPeople;
        /**
         * 工作负责人 名字
         */
        public String workerPeople;
        /**
         * 限制使用的无人机类型名 如 Phantom 4 RTK
         */
        public String uavCategory;

        /**
         * 当前工单默认巡检方式,对应{@link com.lt.config.MissionModeConfig}
         */
        public int inspectMode;

        /**
         * 当前工单巡检方式可选列表 纯数字 对应 {@link com.lt.config.MissionModeConfig}
         */
        public List<String> inspectModeList;

        /**
         * 作业性质 仅供工单详情内展示使用
         */
        public String workTName;

        /**
         * 作业状态
         */
        public String inspecStatus;

        /**
         * 当前工单，配置的所有飞手的飞手名(一般为5个内)
         */
        public List<String> workers;
        /**
         * 对应模式参考后台文档 返回action 参考 {@link com.lt.config.MissionModeConfig}
         *
         * @return  x
         */
        @SuppressWarnings("deprecation")
        public String getLaunchAction() {
            String action = "";
            switch (inspectMode){
                case mission0:
                    action =  ActionConfig.Mission.ACTION_MISSION_0;
                    break;
                case mission1:
                    action =  ActionConfig.Mission.ACTION_MISSION_1;
                    break;
                case mission2:
                    action =  ActionConfig.Mission.ACTION_MISSION_2;
                    break;
                case mission3:
                    action =  ActionConfig.Mission.ACTION_MISSION_3;
                    break;
                case mission4:
                    action =  ActionConfig.Mission.ACTION_MISSION_4;
                    break;
                case mission5:
                    action =  ActionConfig.Mission.ACTION_MISSION_5;
                    break;
                case mission6:
                    action =  ActionConfig.Mission.ACTION_MISSION_6;
                    break;
                case mission7:
                    action =  ActionConfig.Mission.ACTION_MISSION_7;
                    break;
                case mission8:
                    action =  ActionConfig.Mission.ACTION_MISSION_8;
                    break;
                case mission9:
                    action =  ActionConfig.Mission.ACTION_MISSION_9;
                    break;
                case mission10:
                    action =  ActionConfig.Mission.ACTION_MISSION_10;
                    break;
                case mission11:
                    action =  ActionConfig.Mission.ACTION_MISSION_11;
                    break;
                case mission12:
                    action =  ActionConfig.Mission.ACTION_MISSION_12;
                    break;
                case mission13:
                    action =  ActionConfig.Mission.ACTION_MISSION_13;
                    break;
                case mission14:
                    action =  ActionConfig.Mission.ACTION_MISSION_14;
                    break;
                case mission17:
                    action =  ActionConfig.Mission.ACTION_MISSION_17;
                    break;
                case mission18:
                    action =  ActionConfig.Mission.ACTION_MISSION_18;
                    break;
                case mission19:
                    action =  ActionConfig.Mission.ACTION_MISSION_19;
                    break;
                case mission20:
                    action =  ActionConfig.Mission.ACTION_MISSION_20;
                    break;
                case mission21:
                    action =  ActionConfig.Mission.ACTION_MISSION_21;
                    break;
                default:
                    // 未匹配到相应的巡检模式
                    return action;
            }
            return action + "_"+inspectMode;
        }
    }
}

