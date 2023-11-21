package com.lt.server.request;

import com.lt.server.response.Group;

import java.util.List;

public class AuditorListGroup {


    /**
     * 审核人列表接口的分组的集合
     */
    public List<AuditorListBean> data;
    @Override
    public String toString() {
        return "AuditorListGroup{" +
                "data=" + data +
                '}';
    }
}
