package com.lt.server.request;

import com.google.gson.annotations.SerializedName;

public class AuditorListBean {

    @SerializedName("realName")
    private String realName;
    @SerializedName("id")
    private String id;

    public AuditorListBean(String realName, String id) {
        this.realName = realName;
        this.id = id;
    }

    @Override
    public String toString() {
        return "AuditorListBean{" +
                "realName='" + realName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
