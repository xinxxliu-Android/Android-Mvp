package com.lt.server.response;

/**
 * app授权登录接口-账号密码+pad识别码
 * 注意 该接口只坐临时使用，后续刘哥后台拆出服务后，全部使用自动授权登录
 * path:app/uav/sign/getPadSign
 * 接口说明：使用账号、密码、pad识别码进行登录授权
 */
public final class PadSignResponse {
    /**
     * 授权验证token
     * 有效期 48小时
     */
    public String token;
    /**
     * pad 别名 可以为空
     */
    public String name;
    /**
     * 授权到期时间   yyyy-MM-dd HH:mm:ss
     */
    public String signTimeOut;
}
