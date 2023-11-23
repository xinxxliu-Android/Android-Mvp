package com.lt.admap.tilesources;

import android.content.Context;
import android.util.Log;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.util.MapTileIndex;
import org.osmdroid.views.overlay.TilesOverlay;

/**
 * 天地图矢量图层 街道
 */
public class TianDituVectorNoteTileSource extends XYTileSourceCoordinateSystem {

    /**
     * 获取天地图矢量图层 - 街道
     */
    public static TilesOverlay getTilesOverlays(Context context) {
        MapTileProviderBasic noteTileProviders = new MapTileProviderBasic(context, new TianDituVectorNoteTileSource());
        TilesOverlay tilesOverlay = new TilesOverlay(noteTileProviders, context);
        tilesOverlay.setEnabled(false);
        return tilesOverlay;
    }


    public TianDituVectorNoteTileSource() {
        super("TianDitu-Vector-Note", 1, 22, 256, "png",
                new String[]{
                        "https://t0.tianditu.gov.cn/cva_w/wmts?",
                        "https://t1.tianditu.gov.cn/cva_w/wmts?",
                        "https://t2.tianditu.gov.cn/cva_w/wmts?",
                        "https://t3.tianditu.gov.cn/cva_w/wmts?",
                        "https://t4.tianditu.gov.cn/cva_w/wmts?",
                        "https://t5.tianditu.gov.cn/cva_w/wmts?",
                        "https://t6.tianditu.gov.cn/cva_w/wmts?",
                }, CoordinateSystem.CGCS2000);
        Configuration.getInstance().setUserAgentValue("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/72.0.3626.121 Safari/537.36");
    }


    @Override
    public String getTileURLString(final long pMapTileIndex) {
    /*    String urlString = getBaseUrl() +
                "SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=cva&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles" +
                "&tk=b749aea9b97365bef48816ad9253ff49" +
                "&TILEMATRIX=" + MapTileIndex.getZoom(pMapTileIndex) +
                "&TILEROW=" + MapTileIndex.getY(pMapTileIndex) +
                "&TILECOL=" + MapTileIndex.getX(pMapTileIndex);*/

        String urlString = new StringBuilder()
                .append(getBaseUrl())
                .append("SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=cva&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles")
                .append("&tk=")
                .append(GobalConfiguration.tianDiTuTk)
                .append("&TILEMATRIX=")
                .append(MapTileIndex.getZoom(pMapTileIndex))
                .append("&TILEROW=")
                .append(MapTileIndex.getY(pMapTileIndex))
                .append("&TILECOL=")
                .append(MapTileIndex.getX(pMapTileIndex)).toString();

//        Log.d("OsmDroid", urlString);
        return urlString;
    }

    @Override
    public TitleSourceType getTitleSourceType() {
        return TitleSourceType.TIAN_DI_TU_VECTOR;
    }
}
