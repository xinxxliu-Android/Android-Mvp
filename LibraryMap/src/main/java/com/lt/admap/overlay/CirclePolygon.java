package com.lt.admap.overlay;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.PointL;
import org.osmdroid.util.TileSystem;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.Overlay;

public class CirclePolygon extends Overlay {

    private PointL mMapCoordsProjected = new PointL();
    private Point mMapCoordsTranslated = new Point();
    private Paint mFillPaint;
    private Paint mOutLinePaint ;

    private MapView mapView;

    private double mRadius;
    private IGeoPoint locationPoint;

    public CirclePolygon(MapView mapView) {
        this.mapView = mapView;
        mFillPaint = new Paint();
        mFillPaint.setStyle(Paint.Style.FILL);
        mOutLinePaint = new Paint();
        mOutLinePaint.setStyle(Paint.Style.STROKE);
    }

    public IGeoPoint getLocationPoint() {
        return locationPoint;
    }

    public void setLocationPoint(IGeoPoint locationPoint) {
        this.locationPoint = locationPoint;
        mapView.postInvalidate();
    }

    public double getRadius() {
        return mRadius;
    }

    public void setRadius(double mRadius) {
        this.mRadius = mRadius;
    }

    public Paint getFillPaint(){
        return mFillPaint;
    }

    public Paint getOutLinePaint() {
        return mOutLinePaint;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void draw(Canvas pCanvas, Projection pProjection) {
        super.draw(pCanvas, pProjection);
        if(locationPoint == null){
            return;
        }
        mapView.getProjection().toProjectedPixels(locationPoint.getLatitude(), locationPoint.getLongitude(), mMapCoordsProjected);
        Projection pj = mapView.getProjection();
        pj.toPixelsFromProjected(mMapCoordsProjected, mMapCoordsTranslated);
        //根据地图当前的分辨率重新计算圆形半径
        double computeRadius = mRadius
                / (float) TileSystem.GroundResolution(locationPoint.getLatitude(),
                pj.getZoomLevel());
        pCanvas.drawCircle(mMapCoordsTranslated.x, mMapCoordsTranslated.y, (float) computeRadius, mFillPaint);
        pCanvas.drawCircle(mMapCoordsTranslated.x, mMapCoordsTranslated.y,(float) computeRadius,mOutLinePaint);
    }

    public void setVisible(boolean visible){
        setEnabled(visible);
    }
}
