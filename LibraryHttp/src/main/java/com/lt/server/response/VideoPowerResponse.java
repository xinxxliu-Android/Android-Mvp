package com.lt.server.response;

/**
 * 蒙东获取用户查看实时视频响应类
 */
public class VideoPowerResponse {

    /**
     * 视频权限：
     * 1、不可查看
     * 0、可以查看
     */
    private int videoPower;

    /**
     * 用户名称
     */
    private String userName;

    public int getVideoPower() {
        return videoPower;
    }

    public void setVideoPower(int videoPower) {
        this.videoPower = videoPower;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
