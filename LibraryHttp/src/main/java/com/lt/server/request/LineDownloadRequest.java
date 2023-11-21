package com.lt.server.request;

import androidx.annotation.NonNull;

/**
 * 下载航线文件
 * path:app/lineLedger/deviceProtocol
 */
public class LineDownloadRequest implements Cloneable{
    /**
     * 线路/分组识别码
     */
    public String groupGuid;
    /**
     * 设备类型
     */
    public int deviceType;
    /**
     * 设备识别码
     * 杆塔识别码
     */
    public String deviceGuid;

    /**
     * 航线类型 红外 自主
     */
    public int type;
    /**
     * 工单guid
     */
    public String inspectGuid;
    /**
     * 作业性质编码 网格工单
     */
    public int inspectNnatureCode;

    @NonNull
    @Override
    public LineDownloadRequest clone() throws AssertionError{
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (LineDownloadRequest) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
