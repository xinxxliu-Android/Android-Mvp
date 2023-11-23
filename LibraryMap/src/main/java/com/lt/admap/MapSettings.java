package com.lt.admap;

import android.view.View;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.lt.IMapSettings;

import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.overlay.MapEventsOverlay;

final class MapSettings implements IMapSettings {
    ADMapView mapView;

    private MapEventsReceiver clickListener;
    private MapEventsOverlay clickOverlay;

    /**
     * 最小缩放
     */
    private static final Double minZoomLevel = 4.0D;
    /**
     * 最大缩放
     */
    private static final Double maxZoomLevel = 24.0D;

    public MapSettings(ADMapView mapView) {
        this.mapView = mapView;
    }

    @Override
    public void release() {
        if (clickOverlay != null)
            mapView.getOverlays().remove(clickOverlay);
        clickOverlay = null;
        clickListener = null;
        mapView = null;
    }

    @Override
    public void setMapClickListener(View.OnClickListener listener) {
        if (clickListener == null)
            clickListener = new MapEventsReceiver() {
                @Override
                public boolean singleTapConfirmedHelper(GeoPoint p) {
                    if (listener != null)
                        listener.onClick(mapView);
                    return false;
                }

                @Override
                public boolean longPressHelper(final GeoPoint p) {
                    return false;
                }
            };
        if (clickOverlay == null)
            clickOverlay = new MapEventsOverlay(clickListener);
        if (listener != null) {
            mapView.getOverlays().add(clickOverlay);
        } else
            mapView.getOverlays().remove(clickOverlay);
    }

    @Override
    public void initSettings() {
        mapView.setMultiTouchControls(true);
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        mapView.setMinZoomLevel(minZoomLevel);
        mapView.setMaxZoomLevel(maxZoomLevel);
        mapView.getController().setZoom(18.0);
        ITileSource tileSource = mapView.getTileProvider().getTileSource();
        int tile_size = tileSource.getTileSizePixels();
        float size = mapView.getContext().getResources().getDisplayMetrics().density * 256.0F / (float) tile_size;
        //如果是大疆设备
        String manufacturer = DeviceUtils.getManufacturer();
        if ("DJI".toUpperCase().equals(manufacturer)) {
            mapView.setTilesScaledToDpi(true);
        } else if (size >= 2.0F) {
            mapView.setTilesScaleFactor(size / 2.0F);
        } else {
            mapView.setTilesScaledToDpi(true);
        }
    }

    /**
     * 设置图层缩放到最小(地图放大)
     */
    @Override
    public void setMinZoomLevel() {
        mapView.getController().setZoom(mapView.getMinZoomLevel());
    }

    /**
     * 设置图层缩放到最大(地图缩小)
     */
    @Override
    public void setMaxZoomLevel() {
        mapView.getController().setZoom(mapView.getMaxZoomLevel());
    }

    private void post(Runnable run) {
        ThreadUtils.getMainHandler().post(run);
    }
}
