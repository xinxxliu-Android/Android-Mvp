package com.lt.server.request;

/**
 * app保存巡检图片信息
 * path：app/uavData/receivePic
 * 上传巡检图片
 *
 * multipart/form-data
 * file 巡检的图片
 */
public class PicUploadRequest {
    /**
     * 工单guid
     */
    public String inspectGuid;
    /**
     * 设备类型
     */
    public int deviceType;
    /**
     * 巡检的 分组guid
     */
    public String groupGuid;
    /**
     * 巡检设备guid 即 杆塔guid
     */
    public String deviceGuid;
    /**
     * 照片总数
     */
    //sumPic(照片总数)、isOver(是否是最后一张 0否 1是)
    public int sumPic;
    /**
     * 当前照片是否是 最后一张
     */
    public int isOver;
    /**
     * 批次/组，红外测温使用,默认就是”0“
     * 指 红外照片/可见光照片 是同一张照片 则该字段值相同
     */
    public int picBatch;
    /**
     * 图片类型【0:可见光,1:红外】,默认就是”0“
     */
    public int picMold;
}
