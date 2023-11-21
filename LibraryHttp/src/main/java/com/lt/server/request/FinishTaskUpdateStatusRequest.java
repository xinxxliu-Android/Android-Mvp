package com.lt.server.request;
/**
 * 完成任务-更新状态
 * path：android/main/finishTask
 *
 */
public class FinishTaskUpdateStatusRequest {
    /**
     *  工单guid
     */
    public String inspectGuid;

    /**
     *  审核人guid
     */
    public String reviewerGuid;

}
