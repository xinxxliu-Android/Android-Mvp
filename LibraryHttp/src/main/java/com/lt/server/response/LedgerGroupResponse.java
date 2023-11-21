package com.lt.server.response;

import java.util.List;

/**
 * 台账相关
 * 查询所有线路
 * path：app/lineLedger/lineInfoList
 */
public class LedgerGroupResponse {
    /**
     * 所有的分组
     */
    public List<Group> groups;
}
