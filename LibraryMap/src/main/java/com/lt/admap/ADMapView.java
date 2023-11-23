package com.lt.admap;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.lt.IMap;
import com.lt.IMapCommon;
import com.lt.IMapLifecycle;
import com.lt.IMapSettings;
import com.lt.IMapUi;

import org.osmdroid.tileprovider.MapTileProviderBase;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.FolderOverlay;

public final class ADMapView extends MapView implements IMap {
    private int type;
    IMapUi mapUi;
    IMapCommon mapCommon;
    IMapLifecycle lifecycle;
    IMapSettings mapSettings;

    public ADMapView(Context context, MapTileProviderBase tileProvider, Handler tileRequestCompleteHandler, AttributeSet attrs) {
        super(context, tileProvider, tileRequestCompleteHandler, attrs);
    }

    public ADMapView(Context context, MapTileProviderBase tileProvider, Handler tileRequestCompleteHandler, AttributeSet attrs,
                     boolean hardwareAccelerated) {
        super(context, tileProvider, tileRequestCompleteHandler, attrs, hardwareAccelerated);
    }

    public ADMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        //初始化配置
        mapUi = new MapUi(this);
        mapSettings = new MapSettings(this);
        mapCommon = new MapCommon(this);
        lifecycle = new MapLifecycle(this);
        //初始化配置
        mapSettings.initSettings();
//        PluginMap.initNoNetInstall();
//        if (NetworkUtils.isConnected()) {
//            this.setTileSource(new TianDituVectorTileSource());
//        } else {
//            XYTileSource offlineTileSource = new XYTileSource("map", 1, 22, 256, "png", new String[]{});
//            this.setTileSource(offlineTileSource);
//        }
    }

    /**
     * 关闭页面的时候才进行释放MapView中的所有组件
     * 生命周期顺序。
     * 1.设定isFinishing为true
     * 2.开始执行onDestroy()
     * 3.通常在onDestroy()的内部执行onDetachedFromWindow()
     */
    @Override
    protected void onDetachedFromWindow() {
        Context context = getContext();
        if (context instanceof AppCompatActivity) {
            /*
             * 这里的这个状态用于判断MapView在执行onDetachedFromWindow()函数时
             * 要不要进行map中所有的图层资源进行释放。
             * true 代表释放  默认时true
             *
             * 如果说当前是被removeView执行的onDetachedFromWindow()
             * 那么这里是不进行移除View中的所有图层的。
             * 用于之后再次add的时候地图图层还在，不需要再次的赋值加载。
             */
            setDestroyMode(((Activity) context).isFinishing());
        }
        super.onDetachedFromWindow();
    }

    public ADMapView(Context context, AttributeSet attrs, FolderOverlay mFolderOverlay) {
        super(context, attrs);
        mapUi = new MapUi(this);
        mapSettings = new MapSettings(this);
        mapCommon = new MapCommon(this);
        lifecycle = new MapLifecycle(this);
    }

    public ADMapView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ADMapView(Context context, MapTileProviderBase aTileProvider) {
        super(context, aTileProvider);
    }

    public ADMapView(Context context, MapTileProviderBase aTileProvider, Handler tileRequestCompleteHandler) {
        super(context, aTileProvider, tileRequestCompleteHandler);
    }

    @Override
    public void release() {
        mapUi = null;
        lifecycle = null;
        mapCommon = null;
        mapSettings = null;
    }

    @NonNull
    @Override
    public IMapCommon mapCommon() {
        return mapCommon;
    }

    @NonNull
    @Override
    public IMapSettings mapSettings() {
        return mapSettings;
    }

    @NonNull
    @Override
    public IMapLifecycle lifecycle() {
        return lifecycle;
    }

    @NonNull
    @Override
    public IMapUi mapUi() {
        return mapUi;
    }

    /**
     * 重写onTouchEvent方法 解决当地图移动时不能缩放地图
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mapCommon.setCamera(true);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            mapCommon.setCamera(false);
        }
        return super.onTouchEvent(event);
    }
}
