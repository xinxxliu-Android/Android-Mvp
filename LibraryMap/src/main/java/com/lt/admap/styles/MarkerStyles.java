package com.lt.admap.styles;

import org.osmdroid.views.overlay.Marker;

public class MarkerStyles {
    private int resId;
    private float mAnchorU= Marker.ANCHOR_CENTER, mAnchorV=Marker.ANCHOR_CENTER;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public float getAnchorU() {
        return mAnchorU;
    }

    public void setAnchorU(float mAnchorU) {
        this.mAnchorU = mAnchorU;
    }

    public float getAnchorV() {
        return mAnchorV;
    }

    public void setAnchorV(float mAnchorV) {
        this.mAnchorV = mAnchorV;
    }
}
