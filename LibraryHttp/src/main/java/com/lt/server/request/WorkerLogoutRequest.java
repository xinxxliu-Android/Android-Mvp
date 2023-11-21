package com.lt.server.request;

/**
 * 飞手登出
 * path:app/validate/logout
 * 登出管控平台
 * {@link com.lt.server.response.WorkerLogoutResponse}
 * 校核时间：2023-0213 11：31
 */
public class WorkerLogoutRequest {
    /**
     * 当前用户guid
     */
    public String userGuid;
}
