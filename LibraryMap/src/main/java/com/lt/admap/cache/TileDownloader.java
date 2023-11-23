package com.lt.admap.cache;

import android.content.Context;

import com.lt.admap.ADMapView;
import com.lt.config.PathConfig;
import com.lt.window.AppLoading;
import com.lt.window.IAppLoading;

import org.osmdroid.tileprovider.cachemanager.CacheManager;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.tileprovider.tilesource.TileSourcePolicyException;
import org.osmdroid.util.BoundingBox;

import java.io.File;

public class TileDownloader {
    CacheManager mgr = null;
    ADMapView mapView = null;

    public CacheManager.CacheManagerTask osmTileDownload(ImgTileWriter tiles,ADMapView mapView, CacheManager.CacheManagerCallback callback) {
        try {
            this.mapView = mapView;
            mgr = new CacheManager(mapView, tiles);
            int zoomMin = 18;
            int zoomMax = 19;
            //下载区域
            BoundingBox bb = new BoundingBox(30.48136, 114.40511, 30.47600, 114.39421);//北东南西
            ITileSource tileSource = mapView.getTileProvider().getTileSource();
            if (tileSource instanceof OnlineTileSourceBase) {
                if (!((OnlineTileSourceBase) tileSource).getTileSourcePolicy().acceptsBulkDownload()) {
                    callback.onTaskFailed(-1);
                    return null;
                }
            }
            int i = mgr.possibleTilesInArea(bb, zoomMin, zoomMax);
            callback.setPossibleTilesInArea(i);
            return mgr.downloadAreaAsyncNoUI(mapView.getContext(), bb, zoomMin, zoomMax, callback);

        } catch (Exception ex) {
            //TODO something better?
            ex.printStackTrace();
        }
        return null;
    }
}


