package com.lt.server.request;

import java.util.List;

/**
 * app主动构建工单 请求实体
 * path：xxx
 */
public final class CreateInspectRequest {
    /**
     * 工单创建时间
     * 目前 使用选择的设备的 最早开始执行的时间
     */
    public long inspectStartTime;
    /**
     * 当前飞手guid
     */
    public String workerGuid;

    /**
     * 执行该任务的 飞机guid 即 当前飞机的guid
     */
    public String planeGuidList;

    /**
     * 所有选中的任务的集合
     * 如果任务关联的设备属于同一个分组 则放入同一个{@link  CreateInspectGroup}对象中
     */
    public List<CreateInspectGroup> taskList;

    /**
     * 任务
     * 每个任务 由 多个分组构成
     * 每个分组，由多个设备构成
     * 由于字段较少，故不再使用 {@link com.lt.server.response.Device}对象及相关group
     */
    public static final class CreateInspectGroup {
        public String groupType;

        public String groupGuid;

        public List<CreateInspectDevice> childDeviceList;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CreateInspectGroup that = (CreateInspectGroup) o;

            return groupGuid != null ? groupGuid.equals(that.groupGuid) : that.groupGuid == null;
        }

        @Override
        public int hashCode() {
            return groupGuid != null ? groupGuid.hashCode() : 0;
        }

        /**
         * app主动工单 设备 即 任务对象
         * 每个对象 代表一个任务
         */
        public static final class CreateInspectDevice {
            /**
             * 设备类型
             */
            public String deviceType;
            /**
             * 设备唯一id
             */
            public String deviceGuid;
            /**
             * 巡检该设备时 执行操作的飞手guid
             */
            public String workerGuid;
            /**
             * 巡检该设备时，执行 的巡检模式
             */
            public int workType;
            /**
             * 架次guid
             */
            public String flyGuid;

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                CreateInspectDevice that = (CreateInspectDevice) o;

                if (workType != that.workType) return false;
                return deviceGuid != null ? deviceGuid.equals(that.deviceGuid) : that.deviceGuid == null;
            }

            @Override
            public int hashCode() {
                int result = deviceGuid != null ? deviceGuid.hashCode() : 0;
                result = 31 * result + workType;
                return result;
            }
        }
    }
}
