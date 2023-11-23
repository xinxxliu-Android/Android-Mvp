package com.lt.admap.overlay;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.MapViewRepository;
import org.osmdroid.views.overlay.OverlayWithIW;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

/**
 * Marker自定义的公共父类
 */
public class MarkerSymbol extends OverlayWithIW {
    /*attributes for standard features:*/
    protected GeoPoint mPosition;
    protected float mAlpha;
    protected boolean mDraggable, mIsDragged;
    protected boolean mFlat;
    protected OnMarkerSymbolClickListener mOnMarkerSymbolClickListener;
    protected OnMarkerSymbolDragListener mOnMarkerSymbolDragListener;

    /*attributes for non-standard features:*/
    protected boolean mPanToView;
    protected float mDragOffsetY;

    /*internals*/
    protected Point mPositionPixels;

    /**
     * @since 6.0.3
     */
    private MapViewRepository mMapViewRepository;

    /**
     * Usual values in the (U,V) coordinates system of the icon image
     */
    public static final float ANCHOR_CENTER = 0.5f, ANCHOR_LEFT = 0.0f, ANCHOR_TOP = 0.0f, ANCHOR_RIGHT = 1.0f, ANCHOR_BOTTOM = 1.0f;

    /**
     * @since 6.0.3
     */
    private boolean mDisplayed;
    private final Rect mRect = new Rect();
    private final Rect mOrientedMarkerRect = new Rect();
    private Paint mPaint;

    public MarkerSymbol(MapView mapView) {
        this(mapView, (mapView.getContext()));
    }

    public MarkerSymbol(MapView mapView, final Context resourceProxy) {
        super();
        mMapViewRepository = mapView.getRepository();
        mAlpha = 1.0f; //opaque
        mPosition = new GeoPoint(0.0, 0.0);
        mDraggable = false;
        mIsDragged = false;
        mPositionPixels = new Point();
        mPanToView = true;
        mDragOffsetY = 0.0f;
        mFlat = false; //billboard
        mOnMarkerSymbolClickListener = null;
        mOnMarkerSymbolDragListener = null;
        setInfoWindow(mMapViewRepository.getDefaultMarkerInfoWindow());
    }


    public GeoPoint getPosition() {
        return mPosition;
    }

    /**
     * sets the location on the planet where the icon is rendered
     * @param position
     */
    public void setPosition(GeoPoint position){
        mPosition = position.clone();
    }

    public void setAlpha(float alpha) {
        mAlpha = alpha;
    }

    public float getAlpha() {
        return mAlpha;
    }

    public void setDraggable(boolean draggable) {
        mDraggable = draggable;
    }

    public boolean isDraggable() {
        return mDraggable;
    }

    public void setFlat(boolean flat) {
        mFlat = flat;
    }

    public boolean isFlat() {
        return mFlat;
    }

    /**
     * Removes this Marker from the MapView.
     * Note that this method will operate only if the Marker is in the MapView overlays
     * (it should not be included in a container like a FolderOverlay).
     *
     * @param mapView
     */
    public void remove(MapView mapView) {
        mapView.getOverlays().remove(this);
    }

    public void setOnMarkerSymbolClickListener(OnMarkerSymbolClickListener listener) {
        mOnMarkerSymbolClickListener = listener;
    }

    public void setOnMarkerSymbolDragListener(OnMarkerSymbolDragListener listener) {
        mOnMarkerSymbolDragListener = listener;
    }



    /**
     * set the offset in millimeters that the marker is moved up while dragging
     */
    public void setDragOffset(float mmUp) {
        mDragOffsetY = mmUp;
    }

    /**
     * get the offset in millimeters that the marker is moved up while dragging
     */
    public float getDragOffset() {
        return mDragOffsetY;
    }

    /**
     * Set the InfoWindow to be used.
     * Default is a MarkerInfoWindow, with the layout named "bonuspack_bubble".
     * You can use this method either to use your own layout, or to use your own sub-class of InfoWindow.
     * Note that this InfoWindow will receive the Marker object as an input, so it MUST be able to handle Marker attributes.
     * If you don't want any InfoWindow to open, you can set it to null.
     */
    public void setInfoWindow(MarkerInfoWindow infoWindow) {
        mInfoWindow = infoWindow;
    }

    /**
     * If set to true, when clicking the marker, the map will be centered on the marker position.
     * Default is true.
     */
    public void setPanToView(boolean panToView) {
        mPanToView = panToView;
    }




    @Override
    public void onDetach(MapView mapView) {
        super.onDetach(mapView);
    }


    @Override
    public boolean onSingleTapConfirmed(final MotionEvent event, final MapView mapView) {
        return false;
    }


    @Override
    public boolean onLongPress(final MotionEvent event, final MapView mapView) {
        return false;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event, final MapView mapView) {
        return false;
    }

    public void setVisible(boolean visible) {
        if (visible)
            setAlpha(1f);
        else setAlpha(0f);
    }

    //-- Marker events listener interfaces ------------------------------------

    public interface OnMarkerSymbolClickListener {
        abstract boolean onMarkerSymbolClick(MarkerSymbol marker, MapView mapView);
    }

    public interface OnMarkerSymbolDragListener {
        abstract void onMarkerSymbolDrag(MarkerSymbol marker);
        abstract void onMarkerSymbolDragEnd(MarkerSymbol marker);
        abstract void onMarkerDragSymbolStart(MarkerSymbol marker);
    }


}
