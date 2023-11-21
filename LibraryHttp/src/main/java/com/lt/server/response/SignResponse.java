package com.lt.server.response;

import java.util.List;

/**
 * 请求 app授权接口
 * path：    app/uav/sign/getSign
 * 获取当前api授权信息
 */
public class SignResponse {
    /**
     * 授权验证token
     * 有效期 48小时
     */
    public String token;
    /**
     * 无人机 别名 可以为空
     */
    public String name;
    /**
     * 授权到期时间   yyyy-MM-dd HH:mm:ss
     */
    public String signTimeOut;
    /**
     * 是否弹出告警窗
     */
    public boolean isMaintenanceRequire;
    /**
     * 保养周期使用次数
     * 距离上次保养使用过了多少次
     */
    public int warnText;
    /**
     * 保养周期天数
     * 距离上次保养过了多少天
     */
    public int totalPeriod;
    /**
     * 保养周期巡检杆塔数量
     * 距离上次保养巡检杆塔数量又巡检了多少
     */
    public int inspecCount;
}
