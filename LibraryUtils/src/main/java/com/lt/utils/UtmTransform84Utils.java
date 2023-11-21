package com.lt.utils;

import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.CoordinateTransform;
import org.osgeo.proj4j.CoordinateTransformFactory;
import org.osgeo.proj4j.ProjCoordinate;

public class UtmTransform84Utils {

    private static final CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
    private static final CRSFactory crsFactory = new CRSFactory();

    private static CoordinateReferenceSystem createCRS(String crsSpec) {
        CoordinateReferenceSystem crs = null;
        // test if name is a PROJ4 spec
        if (crsSpec.indexOf("+") >= 0 || crsSpec.indexOf("=") >= 0) {
            crs = crsFactory.createFromParameters("Anon", crsSpec);
        } else {
            crs = crsFactory.createFromName(crsSpec);
        }
        return crs;
    }

    public static ProjCoordinate gpsToUtm(double lat, double lng) {
        String WGS84_PARAM = "+proj=longlat +datum=WGS84 +no_defs";
        String tgtCRS = "+proj=utm +zone=50 +ellps=WGS84 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs";

        CoordinateTransform trans = ctFactory.createTransform(createCRS(WGS84_PARAM), createCRS(tgtCRS));
        ProjCoordinate pout = new ProjCoordinate();

        ProjCoordinate p = new ProjCoordinate(lng, lat);
        trans.transform(p, pout);
        return pout;
    }

    public static ProjCoordinate utmToGps(double y, double x) {
        String WGS84_PARAM = "+title=long/lat:WGS84 +proj=longlat +datum=WGS84 +units=degrees";
        String tgtCRS = "+proj=tmerc +lat_0=0 +lon_0=117 +y_0=0 +x_0=500000 +k=0.9996 +zone=50 +to_meter=1 +a=6378137 +ellps=WGS84 +units=m +no_defs";

        CoordinateTransform trans = ctFactory
                .createTransform(createCRS(tgtCRS), createCRS(WGS84_PARAM));
        ProjCoordinate pout = new ProjCoordinate();
        ProjCoordinate p = new ProjCoordinate(x, y);
        trans.transform(p, pout);
        return pout;
    }

    public static String get(){
        return "";
    }
}
