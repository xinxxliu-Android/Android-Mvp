package com.lt.admap;

import androidx.annotation.NonNull;

import com.lt.IMapCommon;
import com.lt.entity.LocationEntity;

import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;

final class MapCommon implements IMapCommon {
    ADMapView mapView;
    CustomZoomButtonsController zoomController;
    IMapController mapController;

    MapCommon(ADMapView mapView) {
        this.mapView = mapView;
    }

    @Override
    public void release() {
        mapView = null;
        mapController = null;
        zoomController = null;
    }

    private boolean isTouch = false;

    private long lastTime = 0L;

    /**
     * 将视角 移动到指定位置 并将该位置设置为 定位中心点
     *
     * @param le 需要移动到的点。
     */
    @Override
    public void moveCamera(@NonNull LocationEntity le) {
        moveCamera(le, false);
    }

    @Override
    public void moveCamera(@NonNull LocationEntity le, boolean isClick) {
        //每3秒刷新下位置
        long currentTime = System.currentTimeMillis();
        double zoomLevelDouble = 0.0;
        /**
         * 当点击当前位置或进入巡检页面后
         * 定位到无人机位置并缩放至地图瓦片
         * 缩放地图时地图不缩放
         *
         */
        if (isClick) {
            // 设置缩放
            zoomLevelDouble = mapView.getMaxZoomLevel();
        } else {
            //默认移动飞机位置不缩放
            zoomLevelDouble = mapView.getZoomLevelDouble();
            //如果三秒内刷新过。那么跳出。
            if (currentTime - lastTime < 3000L) {
                return;
            }
        }
        mapController = mapView.getController();
        if (mapController == null) return;

        GeoPoint startPoint = new GeoPoint(le.getLatitude(), le.getLongitude());
        startPoint.setAltitude(le.getAltitude());
        lastTime = currentTime;
        if (!isTouch) {
            mapController.animateTo(startPoint, zoomLevelDouble, 500L);
        }
    }

    @Override
    public void setCamera(boolean touch) {
        if (mapController != null) {
            mapController.stopAnimation(false);
        }

        lastTime = System.currentTimeMillis();
        this.isTouch = touch;
    }
}
