package com.lt.server.request;

/**
 * app登录
 * path:app/validate/login
 * 飞手登陆
 * {@link  WorkerLoginRequest}
 * 校核时间：2023-0213 11：31
 */
public class WorkerLoginRequest {
    public String loginName;
    public String passWord;
    /**
     * 集成i国网后，只需要该字段，即可实现登陆
     */
    public String ticket;
}
