package com.lt.admap.tilesources;

import android.content.Context;
import android.util.Log;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.MapTileProviderBasic;
import org.osmdroid.util.MapTileIndex;
import org.osmdroid.views.overlay.TilesOverlay;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * OpenStreet 地形图OK
 */
public final class SourceOnlineStreetTerrain extends XYTileSourceCoordinateSystem {
    private final List<String> apiKeys = new ArrayList<>();
    private final Random mRandom = new Random(System.currentTimeMillis());

    /**
     * 获取OSM地形图层
     */
    public static TilesOverlay getTilesOverlays(Context context) {
        //设置OSM矢量图层
        MapTileProviderBasic noteTileProvider = new MapTileProviderBasic(context, new SourceOnlineStreetTerrain());
        TilesOverlay tilesOverlay = new TilesOverlay(noteTileProvider, context);
        tilesOverlay.setEnabled(false);
        return tilesOverlay;
    }


    public SourceOnlineStreetTerrain() {
        super("Open-Street-Terrain", 1, 22, 256, "png",
                new String[]{"https://tile.thunderforest.com/cycle"}, CoordinateSystem.WGS84);
        apiKeys.add("77f4910c439a48a594502d82ac9f8e51");
        apiKeys.add("6170aad10dfd42a38d4d8c709a536f38");
        Configuration.getInstance().setUserAgentValue("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/72.0.3626.121 Safari/537.36");
    }

    @Override
    public String getTileURLString(final long pMapTileIndex) {
        int randomKey = mRandom.nextInt(2);
        String urlString = getBaseUrl() +
                "/" +
                MapTileIndex.getZoom(pMapTileIndex) +
                "/" +
                MapTileIndex.getX(pMapTileIndex) +
                "/" +
                MapTileIndex.getY(pMapTileIndex) +
                ".png?apikey=" +
                apiKeys.get(randomKey);
//        Log.d("OsmDroid", urlString);
        return urlString;
    }

    @Override
    public TitleSourceType getTitleSourceType() {
        return TitleSourceType.OPEN_STREET_MAP_TERRAIN;
    }
}
