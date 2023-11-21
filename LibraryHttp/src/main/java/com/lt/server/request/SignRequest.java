package com.lt.server.request;

/**
 * 请求 app授权接口
 * path：    app/uav/sign/getSign
 * 获取当前api授权信息
 */
public class SignRequest {
    /**
     * 无人机唯一识别码
     */
    public String sn;
    /**
     * 地区：
     * 如，CHONG_QING HE_BEI_SHI_JIA_ZHUANG
     * 地区名 为： 省_市 格式，多个拼音以 _ 拼接，直辖市无省名
     */
    public String area;
}
