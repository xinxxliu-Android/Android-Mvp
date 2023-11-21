package com.lt.server.request;

/**
 * 无人机重命名接口
 * 设置别名
 * path：    app/uav/sign/updateData
 */
public class SignRenameRequest {
    /**
     * 无人机识别码
     */
    public String sn;
    /**
     * 需要修改成的别名
     */
    public String name;
}
