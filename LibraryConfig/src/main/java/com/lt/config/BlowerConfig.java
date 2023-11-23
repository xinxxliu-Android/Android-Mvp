package com.lt.config;

/**
 * 风机巡检配置
 */
public interface BlowerConfig {
    int INNNO_BLOWER_REQUEST_CODE = 120;
    int INNNO_BLOWER_RESULT_CODE = 100;
    /**
     * 风机跳转入参名称
     */
    String INNNO_BLOWER_NAME = "launchData";
    /**
     * 获取风机历史任务入参
     */
    String INNNO_BLOWER_ORDER = "orderGuid";
    /**
     * 风机巡检历史数据返回KEY
     */
    String KEY_RESULT_DATA = "resultData";
    /**
     * 因诺风机巡检包名
     */
    String INNNO_BLOWER_PACKAGENAME = "com.innno.wtgbladeinspection";

    /**
     * 因诺风机巡检主界面
     */
    String INNNO_BLOWER_HOME_ACTIVITY = "com.innno.wtgbladeinspection.ui.HomeActivity";

    /**
     * 因诺风机巡检历史任务界面
     */
    String INNNO_BLOWER_THIRD_TASK_ACTIVITY = "com.innno.wtgbladeinspection.ui.ThirdTaskActivity";
}
