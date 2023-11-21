package com.lt.server.request;

/**
 * app授权登录接口-账号密码+pad识别码
 * 注意 该接口只坐临时使用，后续刘哥后台拆出服务后，全部使用自动授权登录
 * path:app/uav/sign/getPadSign
 * 接口说明：使用账号、密码、pad识别码进行登录授权
 */
public final class PadSignRequest {
    /**
     * 授权账号
     */
    public String signName;
    /**
     * 授权密码
     */
    public String signPsw;
    /**
     * pad唯一识别码
     */
    public String padGuid;
}
