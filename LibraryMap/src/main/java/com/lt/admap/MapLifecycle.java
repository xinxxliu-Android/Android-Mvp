package com.lt.admap;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lt.IMapLifecycle;

final class MapLifecycle implements IMapLifecycle {
    ADMapView mapView;

    public MapLifecycle(ADMapView mapView) {
        this.mapView = mapView;
    }

    @Override
    public void release() {
        mapView = null;
    }

    @Override
    public void lifeOnSaveInstanceState(@NonNull Bundle outState) {
    }

    @Override
    public void lifeOnStop() {

    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void lifeOnDestroy() {
        if (mapView.mapUi != null)
            mapView.mapUi().release();
        if (mapView.mapSettings() != null)
            mapView.mapSettings().release();
        if (mapView.mapCommon() != null)
            mapView.mapCommon().release();
        release();
    }

    @Override
    public void lifeOnPause() {
        mapView.onPause();
    }

    @Override
    public void lifeOnResume() {
        mapView.onResume();
    }

    @Override
    public void lifeOnRestoreInstanceState(@NonNull Bundle savedInstanceState) {

    }

    @Override
    public void lifeOnStart() {

    }

    @Override
    public void lifeOnCreate(@Nullable Bundle savedInstanceState) {}
}
