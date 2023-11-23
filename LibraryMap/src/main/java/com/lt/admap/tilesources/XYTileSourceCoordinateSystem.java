package com.lt.admap.tilesources;

import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.tileprovider.tilesource.TileSourcePolicy;

 public abstract class XYTileSourceCoordinateSystem extends OnlineTileSourceBase {

    private CoordinateSystem currentLayerCoordinateSystem = null;


    public XYTileSourceCoordinateSystem(String aName, int aZoomMinLevel, int aZoomMaxLevel, int aTileSizePixels, String aImageFilenameEnding, String[] aBaseUrl, CoordinateSystem coordinateSystem) {
        super(aName, aZoomMinLevel, aZoomMaxLevel, aTileSizePixels, aImageFilenameEnding, aBaseUrl);
        currentLayerCoordinateSystem=coordinateSystem;
    }

    public XYTileSourceCoordinateSystem(String aName, int aZoomMinLevel, int aZoomMaxLevel, int aTileSizePixels, String aImageFilenameEnding, String[] aBaseUrl, String copyright, CoordinateSystem coordinateSystem) {
        super(aName, aZoomMinLevel, aZoomMaxLevel, aTileSizePixels, aImageFilenameEnding, aBaseUrl, copyright);
        currentLayerCoordinateSystem=coordinateSystem;
    }

    public XYTileSourceCoordinateSystem(String aName, int aZoomMinLevel, int aZoomMaxLevel, int aTileSizePixels, String aImageFilenameEnding, String[] aBaseUrl, String copyright, TileSourcePolicy pTileSourcePolicy, CoordinateSystem coordinateSystem) {
        super(aName, aZoomMinLevel, aZoomMaxLevel, aTileSizePixels, aImageFilenameEnding, aBaseUrl, copyright, pTileSourcePolicy);
        currentLayerCoordinateSystem=coordinateSystem;
    }


    /**
     * 获取当前坐标系
     * @param coordinateSystem
     * @return
     */
    public CoordinateSystem getLayerCoordinateSystem(CoordinateSystem coordinateSystem) {
        return currentLayerCoordinateSystem;
    }


    /**
     * 坐标系统
     */
    public static enum CoordinateSystem {
        /**
         * 火星坐标系
         */
        GCJ02("GCJ02"),
        /**
         * WGS84 坐标系
         */
        WGS84("WGS84"),
        /**
         * 大地坐标系2000
         */
        CGCS2000("CGCS2000");

        CoordinateSystem(String coordinateSystem) {

        }
    }

    /**
     * 获取图层类型
     * @return
     */
    public abstract TitleSourceType getTitleSourceType();
}
