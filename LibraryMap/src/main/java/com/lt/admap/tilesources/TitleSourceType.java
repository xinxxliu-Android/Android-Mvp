package com.lt.admap.tilesources;

public enum TitleSourceType {
    AUTO_NAVI_VECTOR("高德矢量图层", 0x0001, XYTileSourceCoordinateSystem.CoordinateSystem.GCJ02),
    GOOGLE_SATELLITE("谷歌卫星图层", 0x0002, XYTileSourceCoordinateSystem.CoordinateSystem.GCJ02),
    GOOGLE_TERRAIN("谷歌地形图层", 0x0003, XYTileSourceCoordinateSystem.CoordinateSystem.GCJ02),
    GOOGLE_VECTOR("谷歌矢量图层", 0x0004, XYTileSourceCoordinateSystem.CoordinateSystem.GCJ02),
    TIAN_DI_TU_VECTOR("天地图矢量图层", 0x0005, XYTileSourceCoordinateSystem.CoordinateSystem.CGCS2000),
    TIAN_DI_TU_SATELLITE("天地图矢量图层", 0x0006, XYTileSourceCoordinateSystem.CoordinateSystem.CGCS2000),
    OPEN_STREET_MAP_VECTOR("OpenStreetMap图层", 0x0007, XYTileSourceCoordinateSystem.CoordinateSystem.WGS84),
    OPEN_STREET_MAP_TERRAIN("OpenStreetMap地形", 0x0008, XYTileSourceCoordinateSystem.CoordinateSystem.WGS84);

    public String type;
    public int uniqueValue;
    public XYTileSourceCoordinateSystem.CoordinateSystem coordinateSystem;

    TitleSourceType(String type, int uniqueValue, XYTileSourceCoordinateSystem.CoordinateSystem coordinateSystem) {
        this.type = type;
        this.uniqueValue = uniqueValue;
        this.coordinateSystem = coordinateSystem;
    }

}
