package com.lt.config;

/**
 * 页面跳转的intent.putExtra 时的 name
 */
public interface IntentConfig {

    /**
     * 常用intent传值的key
     */
    interface Key{
        /**
         * 单条任务下载
         */
        int FLY_HISTORY_DETAILS_CODE = 216;
        /**
         * 批量下载
         */
        int FLY_HISTORY_DETAILS_CODE_MORE = 217;

        String DATA = "extras_data";

        String GUID = "guid";

        String POSITION = "position";

        // 工单Id
        String ORDER_GUID = "orderGuid";
    }

    /**
     * 请求吗、响应码
     */
    interface Code{
        /**
         * 需要关闭当前activity的resultCode
         */
        int FINISH_ACTIVITY = 449;
        /**
         * 默认 启动activity的requestCode
         */
        int DEFAULT_LAUNCH = 940;
        /**
         * 默认 activity setResult的值
         */
        int DEFAULT_RESULT = 944;
    }

}
