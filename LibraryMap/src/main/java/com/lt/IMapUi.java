package com.lt;

import android.location.Location;

import androidx.annotation.NonNull;

import com.lt.entity.LocationEntity;
import com.lt.entity.map.LineEntity;
import com.lt.entity.map.MarkerEntity;
import com.lt.entity.map.PointEntity;
import com.lt.entity.map.PolygonEntity;
import com.lt.func.IReleasable;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;

import java.util.List;

/**
 * 地图控件
 * ui配置
 * 如：
 * 添加点
 * 添加线
 * 添加自定义图形
 * 移除点
 * 移除线
 * 移除图形
 * 释放所有图形等
 */
public interface IMapUi extends IReleasable {
    /**
     * 添加地图内标记
     *
     * @param le 需要添加的标记
     * @return 自身
     */
    IMapUi addMarker(@NonNull MarkerEntity... le);

    /**
     * 移除地图内标记
     *
     * @param los 需要移除的标记
     * @return 当前对象
     */
    IMapUi removeMarker(@NonNull MarkerEntity... los);

    /**
     * 清除所有自定义添加的marker
     *
     * @return 自身
     */
    IMapUi clearMarker();

    /**
     * 显示飞机在地图的位置
     *
     * @return 自身
     */
    IMapUi showRemote();

    /**
     * 显示飞机在地图的位置
     *
     * @return 自身
     */
    IMapUi showRemote(Location location);

    /**
     * 显示飞机在地图的位置
     *
     * @param aircraftLocation 飞机位置
     * @return 自身
     */
    IMapUi showAircraft(LocationEntity aircraftLocation);

    /**
     * 显示飞机与遥控器的线
     *
     * @param remoteLocation   遥控器位置
     * @param aircraftLocation 飞机位置
     * @return 自身
     */
    IMapUi showAircraftLine(LocationEntity remoteLocation, LocationEntity aircraftLocation);

    /**
     * 显示飞机与遥控器的线距离文字
     *
     * @param remoteLocation   遥控器位置
     * @param aircraftLocation 飞机位置
     * @return 自身
     */
    IMapUi showAircraftLineText(LocationEntity remoteLocation, LocationEntity aircraftLocation);

    /**
     * 隐藏飞机在地图的位置
     *
     * @return 自身
     */
    IMapUi hideAircraft();

    /**
     * 添加线
     * 可以单独添加，也可以批量添加。
     *
     * @param lines 需要添加的线
     * @return 自身
     */
    IMapUi addLine(@NonNull LineEntity lines);

    /**
     * 添加线
     * 可以单独添加，也可以批量添加。
     *
     * @param lines 需要添加的集合
     * @return 自身
     */
    IMapUi addPolygon(@NonNull PolygonEntity lines);

    //region 网格相关

    /**
     * 添加网格
     */
    void addPolygonGrid(@NonNull PolygonEntity lines);

    /**
     * 清空网格
     */
    void clearPolygonGrid();

    /**
     * 展示移除网格化
     */
    void showPolygonGrid(Boolean show);

    //endregion

    /**
     * 网格 名称
     *
     * @param name
     * @return
     */
    IMapUi addPolygonGridName(@NonNull String name, GeoPoint location);

    /**
     * 移除线
     * <p>
     * 此处，只需实例化对象的经纬度和高度即可
     *
     * @param lines 需要移除的线
     * @return 自身
     */
    IMapUi removeLine(@NonNull LineEntity lines);


    /**
     * 展示移除网格化名称
     */
    IMapUi showPolygonGridText(Boolean show);

    /**
     * 添加点
     *
     * @param points 点
     * @return 当前
     */
    IMapUi addPoint(@NonNull PointEntity... points);


    /**
     * 移除点
     *
     * @param points 的
     * @return 的
     */
    IMapUi removePoint(@NonNull PointEntity... points);

    /**
     * 清除当前地图上所有的线
     *
     * @return 自身
     */
    IMapUi clearLine();

    /**
     * 删除所有点信息
     *
     * @return x
     */
    IMapUi clearPoint();

    IMapUi changTileSource(int sourceType);

    /**
     * 当前添加的杆塔marker
     *
     * @return List
     */
    List<Marker> towerMarkerList();

    /**
     * 画杆塔或建筑物之间的距离
     *
     * @param points
     * @return
     */
    void drawMissionTask(List<LocationEntity> points);

    /**
     * 清除杆塔建筑物之间距离
     *
     * @return
     */
    IMapUi clearTowerDistanceText();

    /**
     * 根据坐标点画文字标注
     *
     * @param gPoints      对应位置经纬度
     * @param distanceList 对应具体距离
     * @param angleList    对应线之间的角度
     * @return
     */
    IMapUi markTower(List<GeoPoint> gPoints, List<String> distanceList, List<Float> angleList);

    /**
     * 显示返航点图标
     */
    IMapUi showGoHomeLocation(LocationEntity goHomeLocation);


    /**
     * 重新绘制
     */
    void postInvalidate();

    void addMarker(List<List<MarkerEntity>> deviceMarkList);
}
