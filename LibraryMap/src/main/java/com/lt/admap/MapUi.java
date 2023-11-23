package com.lt.admap;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationProvider;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lt.IMapUi;
import com.lt.PluginMap;
import com.lt.admap.overlay.CirclePolygon;
import com.lt.admap.overlay.DistanceTextSymbol;
import com.lt.admap.overlay.PolygonSymbol;
import com.lt.admap.overlay.TextSymbol;
import com.lt.admap.styles.PolygonStyles;
import com.lt.admap.styles.TextStyles;
import com.lt.admap.tilesources.SourceOnlineStreetNormal;
import com.lt.admap.tilesources.SourceOnlineStreetTerrain;
import com.lt.admap.tilesources.TianDituSatelliteNoteSource;
import com.lt.admap.tilesources.TianDituSatelliteSource;
import com.lt.admap.tilesources.TianDituVectorNoteTileSource;
import com.lt.admap.tilesources.TianDituVectorTileSource;
import com.lt.entity.LocationEntity;
import com.lt.entity.map.LineEntity;
import com.lt.entity.map.MarkerEntity;
import com.lt.entity.map.PointEntity;
import com.lt.entity.map.PolygonEntity;
import com.lt.func.ICallBack;
import com.lt.log.LogUtils;
import com.lt.map.R;
import com.lt.util.LocationUtils;
import com.lt.utils.CaculateDistanceUtil;
import com.lt.utils.RxSchedulers;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.FolderOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;


public final class MapUi implements IMapUi {
    /**
     * 这里把这个对象提取出来，作用是防止频繁的创建对象，造成内存过大
     */
    private DistanceTextSymbol textSymbol;
    /**
     * 当前控件中，所有的线
     * {@link #release()}时销毁
     */
    private List<LineEntity> lines;
    /**
     * 当前控件中，所有的点
     * {@link #release()}时销毁
     */
    private List<LocationEntity> locations;

    /**
     * 所有的 线
     */
    private final Map<LineEntity, Polyline> lineMap = new Hashtable<>();

    /**
     * 当前飞机 绘制对象
     */
    private Marker flyMarker;
    /**
     * 返航点 绘制对象
     */
    private Marker goHomeMarker;


    /**
     * 距离文字-杆塔距离图层
     */
    private final FolderOverlay mPoleDistanceOverlay = new FolderOverlay();
    //    返航点图层
    private final FolderOverlay mGoHomeOverlay = new FolderOverlay();
    //杆塔图层
    private final FolderOverlay mTowerOverlay = new FolderOverlay();

    //线图层
    private final FolderOverlay mLineOverlay = new FolderOverlay();

    //航点图层
    private final FolderOverlay mPointOverlay = new FolderOverlay();

    //飞机位置图层
    private final FolderOverlay mFlyOverlay = new FolderOverlay();

    //多边形 常用语空域显示如:禁飞区、限飞区、适航区 或其他空域的图形展示
    private final FolderOverlay mZoneOverlay = new FolderOverlay();

    /**
     * 多边形---网格化台账内容
     */
    private final FolderOverlay mGridOverlay = new FolderOverlay();
    /**
     * 网格文字集合
     */
    private final FolderOverlay mGridTextOverlay = new FolderOverlay();

    //        城市透明图层
    private final FolderOverlay mCityOverlay = new FolderOverlay();

    //        城市街道图层
    private final FolderOverlay mCityStreetOverlay = new FolderOverlay();
    /**
     * 遥控器位置数据
     */
    private LineEntity aircraftLine;

    /**
     * 航点画圆集合
     */
    List<Overlay> mPolygonSymbols;

    /**
     * 航点标注数字集合
     */
    List<Overlay> mTextSymbol;

    /**
     * 杆塔序号集合
     */
    List<Overlay> mTextTowbol;


    /**
     * 杆塔marker集合
     */
    List<Marker> mTowerPolygon;

    Polyline mPolyline;

    private ADMapView mapView;

    /**
     * 当前遥控器的 marker
     */
    private Marker remoteMarker;

    /**
     * 绘制遥控器的计时器
     */
    private Disposable subscribe;

    /**
     * 图层顺序别瞎动
     * 图层顺序从下往上
     */
    public MapUi(ADMapView mapView) {
        this.mapView = mapView;
        //城市图层
        this.mapView.getOverlays().add(mCityOverlay);
        //街道图层
        this.mapView.getOverlays().add(mCityStreetOverlay);
//        返航图标图层
        this.mapView.getOverlays().add(mGoHomeOverlay);
        //网格台账-多边形图层
        this.mapView.getOverlays().add(mGridOverlay);
        //网格台账-多边形图层
        this.mapView.getOverlays().add(mGridTextOverlay);
        //禁飞区图层
        this.mapView.getOverlays().add(mZoneOverlay);
        //杆塔图层
        this.mapView.getOverlays().add(mTowerOverlay);
        //航点线
        this.mapView.getOverlays().add(mLineOverlay);
        //航点图层
        this.mapView.getOverlays().add(mPointOverlay);
        //杆塔距离图层
        this.mapView.getOverlays().add(mPoleDistanceOverlay);
        //飞机位置
        this.mapView.getOverlays().add(mFlyOverlay);
        mPolygonSymbols = new ArrayList<>();
        mTextSymbol = new ArrayList<>();
        mTowerPolygon = new ArrayList<>();
        mTextTowbol = new ArrayList<>();

        //初始化地图图层
        initMapOverlay();

    }

    /**
     * 初始化地图图层。
     */
    private void initMapOverlay() {
        //默认一直加载第一个资源
        mapView.setTileSource(new TianDituSatelliteSource());

        List<Overlay> items = mCityOverlay.getItems();
        /*
          这里统一使用AppContext.防止内存泄漏
          ConnectivityManager.sInstance本身是静态的对象。
          而在创建ConnectivityManager的时候，会传递一个Context，如果是Activity.那么将会造成内存泄漏
         */
        Context applicationContext = mapView.getContext().getApplicationContext();
        //设置天地图矢量图层 - 地形 - 数据源
        items.add(TianDituVectorTileSource.getTilesOverlays(applicationContext));
        //设置天地图矢量图层 - 街道 - 数据源
        mCityStreetOverlay.getItems().add(TianDituVectorNoteTileSource.getTilesOverlays(applicationContext));

        //设置天地图卫星图层 - 地形 - 数据源
        items.add(TianDituSatelliteSource.getTilesOverlays(applicationContext));
        //设置天地图卫星图层 - 街道 - 数据源
        mCityStreetOverlay.getItems().add(TianDituSatelliteNoteSource.getTilesOverlays(applicationContext));

        //设置osm矢量图层数据源
        items.add(SourceOnlineStreetNormal.getTilesOverlays(applicationContext));
        //设置osm地形图层数据源
        items.add(SourceOnlineStreetTerrain.getTilesOverlays(applicationContext));

        //默认展示第一个，
        mCityStreetOverlay.getItems().get(0).setEnabled(true);
        mCityOverlay.getItems().get(0).setEnabled(true);
        mapView.invalidate();
    }

    @Override
    public void release() {
        if (subscribe != null && !subscribe.isDisposed())
            subscribe.dispose();
        subscribe = null;
        //停止平移
        this.mapView.getController().stopPanning();
        //释放图块资源
        this.mapView.getTileProvider().detach();
        //释放图块资源
        this.mapView.getMapOverlay().onDetach(null);
        for (Overlay overlay : this.mapView.getOverlays()) {
            overlay.onDetach(this.mapView);
        }
        this.mapView.getOverlays().clear();
        this.mapView.release();
        remoteMarker = null;
        goHomeMarker = null;
        this.mapView = null;
    }

    @Override
    public IMapUi addMarker(@NonNull MarkerEntity... le) {
        TextStyles textStyles = new TextStyles();
        for (MarkerEntity markerEntity : le) {
            int markerImg = markerEntity.getMarkerImg();
            LocationEntity location = markerEntity.getLocation();
            GeoPoint point = new GeoPoint(location.getLatitude(), location.getLongitude());

            Marker ma = new Marker(mapView);
            ma.setIcon(ContextCompat.getDrawable(PluginMap.appContext.getApplicationContext(), markerImg));
            ma.setPosition(point);
            if (StringUtils.isTrimEmpty(markerEntity.deviceName))
                markerEntity.deviceName = "未知设备" + new Random().nextInt(10);
            ma.setTitle(markerEntity.deviceName);
            ma.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
            ma.setOnMarkerClickListener((marker, mapView) -> {
                ICallBack<MarkerEntity> markerClickListener = markerEntity.getMarkerClickListener();
                if (markerClickListener != null)
                    markerClickListener.callback(markerEntity);
                return false;
            });
            //将杆塔号赋值显示
            TextSymbol textSymbol = new TextSymbol(mapView);
            textStyles.setTextStyle(Paint.Style.FILL, Color.parseColor("#FFFB8C00"), Paint.Align.CENTER, 35);
            TextSymbol.DrawOffset drawOffset = new TextSymbol.DrawOffset();
            drawOffset.xOffset = 0;
            drawOffset.yOffset = 65;
            textSymbol.setLable(markerEntity.deviceName);
            textSymbol.setTextStyle(textStyles.getTextStyle());
            textSymbol.setPosition(point);
            textSymbol.setDrawOffset(drawOffset);
            mTextTowbol.add(textSymbol);
            mTowerPolygon.add(ma);
        }
        mTowerOverlay.getItems().addAll(mTowerPolygon);
        mTowerOverlay.getItems().addAll(mTextTowbol);
        return this;
    }

    /**
     * 添加所有的杆塔点、线、文字等信息
     *
     * @param deviceMarkList
     */
    @Override
    public void addMarker(List<List<MarkerEntity>> deviceMarkList) {
        TextStyles textStyles = new TextStyles();
        for (List<MarkerEntity> list : deviceMarkList) {
            if (list.isEmpty()) {
                break;
            }
            //渲染杆塔连线的集合
            List<GeoPoint> points = new ArrayList<>();
            List<LocationEntity> entityList = new ArrayList<>();
            for (MarkerEntity markerEntity : list) {
                entityList.add(markerEntity.location);
                /**
                 *  取出杆塔数据进行渲染(图标)
                 */
                //创建渲染marker对象
                Marker marker = new Marker(mapView);
                //杆塔图标
                int markerImg = markerEntity.getMarkerImg();
                marker.setIcon(ContextCompat.getDrawable(PluginMap.appContext.getApplicationContext(), markerImg));
                //杆塔经纬度位置
                LocationEntity location = markerEntity.getLocation();
                GeoPoint point = new GeoPoint(location.getLatitude(), location.getLongitude());
                marker.setPosition(point);
                //显示杆塔名称
                if (StringUtils.isTrimEmpty(markerEntity.deviceName))
                    markerEntity.deviceName = "未知设备" + new Random().nextInt(10);
                marker.setTitle(markerEntity.deviceName);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
                //点击事件
                marker.setOnMarkerClickListener((markers, mapView) -> {
                    ICallBack<MarkerEntity> markerClickListener = markerEntity.getMarkerClickListener();
                    if (markerClickListener != null)
                        markerClickListener.callback(markerEntity);
                    return false;
                });
                mTowerPolygon.add(marker);

                //将杆塔号赋值显示
                TextSymbol textSymbol = new TextSymbol(mapView);
                textStyles.setTextStyle(Paint.Style.FILL, Color.parseColor("#FFFB8C00"), Paint.Align.CENTER, 35);
                TextSymbol.DrawOffset drawOffset = new TextSymbol.DrawOffset();
                drawOffset.xOffset = 0;
                drawOffset.yOffset = 65;
                textSymbol.setLable(markerEntity.deviceName);
                textSymbol.setTextStyle(textStyles.getTextStyle());
                textSymbol.setPosition(point);
                textSymbol.setDrawOffset(drawOffset);
                mTextTowbol.add(textSymbol);

                /**
                 * 绘制杆塔连线
                 */
                points.add(point);
            }
            mTowerOverlay.getItems().addAll(mTextTowbol);
            //渲染杆塔数据
            mTowerOverlay.getItems().addAll(mTowerPolygon);
            /**
             *  取出杆塔数据进行渲染(杆塔连线)
             */
            if (points.size() > 1) {
                mPolyline = new Polyline();
                mPolyline.setOnClickListener((polyline, mapView, eventPos) -> false);
                mPolyline.setPoints(points);
                Paint outlinePaint = mPolyline.getOutlinePaint();
                outlinePaint.setStrokeWidth(mapView.getResources().getDimensionPixelSize(R.dimen.dp_3));
                outlinePaint.setColor(mapView.getResources().getColor(R.color.yellow, null));
                //polyline.setInfoWindow((InfoWindow) null);
                // polyline.setVisible(true);
                mLineOverlay.add(mPolyline);
                mapView.postInvalidate();

            }
            //绘制距离和文字
            drawMissionTask(entityList, list.get(0).deviceName + "--");
        }
    }


    @Override
    public IMapUi removeMarker(@NonNull MarkerEntity... los) {
        return this;
    }

    @Override
    public IMapUi clearMarker() {
        if (mTowerPolygon.size() > 0 && mTowerPolygon != null) {
            mTowerOverlay.getItems().removeAll(mTowerPolygon);
            mTowerOverlay.getItems().removeAll(mTextTowbol);
            mTowerPolygon.clear();
            mTextTowbol.clear();
        }
        return this;
    }

    /**
     * 绘制遥控器位置
     */
    @Override
    public IMapUi showRemote() {
        if (subscribe != null && !subscribe.isDisposed())
            subscribe.dispose();
        //当前遥控器位置
        if (remoteMarker == null) {
            remoteMarker = new Marker(mapView);
            remoteMarker.setIcon(ContextCompat.getDrawable(ActivityUtils.getTopActivity(), R.mipmap.icon_global_remote));
            remoteMarker.setVisible(true);
            remoteMarker.setInfoWindow(null);
            remoteMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        }

        com.lt.utils.LocationUtils.register(ActivityUtils.getTopActivity(), 0, 1, new com.lt.utils.LocationUtils.OnLocationChangeListener() {
            @Override
            public void getLastKnownLocation(Location location) {
                //2秒后开始定位 并每间隔200ms执行一次
                if (subscribe != null && !subscribe.isDisposed()) {
                    subscribe.dispose();
                }
                subscribe = Flowable.interval(100, 5000L, TimeUnit.MILLISECONDS)
                        .subscribeOn(RxSchedulers.computation())
                        .filter((it) -> {
                            //获取遥控器的位置
                            return location != null;
                        })
                        .map((it) -> {
                            //获取遥控器的位置
                            return new GeoPoint(location.getLatitude(), location.getLongitude());
                        })
                        .filter((geoPoint) -> {
                            LogUtils.d("getLastKnownLocation：遥控器:" + geoPoint.toString());
                            if (geoPoint.getLatitude() == 0 || geoPoint.getLongitude() == 0) {
                                return false;
                            }
                            return mapView != null && remoteMarker != null && mapView.getParent() != null;
                        })
                        .retry(error -> location == null)
                        .observeOn(RxSchedulers.main())
                        .subscribe(geoPoint -> {
                            if (!mapView.getOverlays().contains(remoteMarker)) {
                                mapView.getOverlays().add(remoteMarker);
                                mapView.getController().animateTo(geoPoint, 18.0, 1000L);
                                mapView.getController().setCenter(geoPoint);
                            }
                            /**
                             * 获取到经纬度后在地图上移动
                             */
                            if (remoteMarker != null) {
                                remoteMarker.setPosition(geoPoint);
                            }
                        });
            }

            @Override
            public void onLocationChanged(Location location) {
                //2秒后开始定位 并每间隔200ms执行一次
                if (location == null || location.getLatitude() == 0 || location.getLongitude() == 0) {
                    return;
                }
                if (mapView == null || remoteMarker == null || mapView.getParent() == null) {
                    return;
                }
                GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                //如果不包含。说明是第一次添加，所以需要移动到遥控器的位置
                if (!mapView.getOverlays().contains(remoteMarker)) {
                    mapView.getOverlays().add(remoteMarker);
                    mapView.getController().animateTo(geoPoint, 18.0, 1000L);
                }
                if (remoteMarker != null) {
                    remoteMarker.setPosition(geoPoint);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

                switch (status) {
                    case LocationProvider.AVAILABLE:
                        LogUtils.d("onStatusChanged", "当前GPS状态为可见状态");
                        break;
                    case LocationProvider.OUT_OF_SERVICE:
                        LogUtils.d("onStatusChanged", "当前GPS状态为服务区外状态");
                        break;
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        LogUtils.d("onStatusChanged", "当前GPS状态为暂停服务状态");
                        break;
                    default:
                        break;
                }

            }
        });

//        if (subscribe != null && !subscribe.isDisposed())
//            subscribe.dispose();
//
//        //当前遥控器位置
//        if (remoteMarker == null) {
//            remoteMarker = new Marker(mapView);
//            remoteMarker.setIcon(ContextCompat.getDrawable(ActivityUtils.getTopActivity(), R.mipmap.icon_global_remote));
//            remoteMarker.setVisible(true);
//            remoteMarker.setInfoWindow(null);
//            remoteMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
//        }
//
//        //2秒后开始定位 并每间隔200ms执行一次
//        subscribe = Flowable.interval(100, 5000L, TimeUnit.MILLISECONDS)
//                .subscribeOn(RxSchedulers.computation())
//                .filter((it) -> {
//                    //获取遥控器的位置
//                    Location lastBestLocation = LocationManager.getInstance().getLastBestLocation();
//                    return lastBestLocation != null;
//                })
//                .map((it) -> {
//                    //获取遥控器的位置
//                    Location lastBestLocation = LocationManager.getInstance().getLastBestLocation();
//                    GeoPoint geoPoint = new GeoPoint(0.0, 0.0);
//                    //如果遥控器位置不为null
//                    if (lastBestLocation != null) {
//                        geoPoint = new GeoPoint(lastBestLocation.getLatitude(), lastBestLocation.getLongitude());
//                        geoPoint.setAltitude(lastBestLocation.getAltitude());
//                    }
//                    //如果遥控器位置为null直接return
//                    if (geoPoint.getLatitude() == 0 || geoPoint.getLongitude() == 0) {
//                        return geoPoint;
//                    }
//                    return geoPoint;
//                })
//                .filter((geoPoint) -> {
//                    if (geoPoint.getLatitude() == 0 || geoPoint.getLongitude() == 0) {
//                        LogUtils.e("遥控器", "经纬度获取失败");
//                        return false;
//                    }
//                    if (mapView == null || remoteMarker == null || mapView.getParent() == null) {
//                        LogUtils.e("遥控器", "mapView为null");
//                        return false;
//                    }
//                    return true;
//                })
//                .retry(error -> {
//                    LogUtils.e("遥控器", error.getMessage());
//                    return LocationManager.getInstance().getLastBestLocation() == null;
//                })
//                .observeOn(RxSchedulers.main())
//                .map((it) -> {  //先定位到遥控器的位置。
//                    //如果不包含。说明是第一次添加，所以需要移动到遥控器的位置
//                    if (!mapView.getOverlays().contains(remoteMarker)) {
//                        mapView.getController().animateTo(it, 18.0, 1000L);
//                        mapView.getController().setCenter(it);
//                    }
//                    return it;
//                })
//                .subscribe(geoPoint -> {
//                    if (!mapView.getOverlays().contains(remoteMarker)) {
//                        mapView.getOverlays().add(remoteMarker);
//                    }
//                    if (remoteMarker != null) {
//                        remoteMarker.setPosition(geoPoint);
//                    }
//                });
        return this;
    }

    @Override
    public IMapUi showRemote(Location location) {
        GeoPoint startPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
        if (mapView == null || mapView.getParent() == null) {
            return this;
        }
        //当前遥控器位置
        if (remoteMarker == null) {
            remoteMarker = new Marker(mapView);
            remoteMarker.setVisible(true);
            remoteMarker.setIcon(ContextCompat.getDrawable(ActivityUtils.getTopActivity(), R.mipmap.icon_global_remote));
            remoteMarker.setPosition(startPoint);
            remoteMarker.setInfoWindow(null);
            remoteMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
            mFlyOverlay.add(remoteMarker);
        } else {
            remoteMarker.setPosition(startPoint);
            mapView.postInvalidate();
        }
        return this;
    }

    @Override
    public IMapUi showAircraft(LocationEntity aircraftLocation) {
        GeoPoint startPoint = new GeoPoint(aircraftLocation.getLatitude(), aircraftLocation.getLongitude());
        if (mapView == null || mapView.getParent() == null)
            return this;
        if (flyMarker == null) {
            flyMarker = new Marker(mapView);
            flyMarker.setVisible(true);
            flyMarker.setIcon(ContextCompat.getDrawable(this.mapView.getContext(), R.mipmap.global_flight_icon));
            flyMarker.setPosition(startPoint);
            flyMarker.setInfoWindow(null);
            flyMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
            //地图航向是镜像航向，需计算转换一下
            flyMarker.setRotation(-aircraftLocation.getYaw());
            mFlyOverlay.add(flyMarker);
        } else {
            //地图航向是镜像航向 需计算转换一下
            flyMarker.setRotation(-aircraftLocation.getYaw());
            flyMarker.setPosition(startPoint);
            mapView.postInvalidate();
        }
        //防止 清除数据后，flymaker被移除
//        if (!mFolderOverlay.getItems().contains(flyMarker))
//            mFolderOverlay.add(flyMarker);
        return this;
    }

    /**
     * 画遥控器到飞机的线
     */
    @Override
    public IMapUi showAircraftLine(LocationEntity remoteLocation, LocationEntity aircraftLocation) {
        //绘制 遥控器到飞机的线
        if (aircraftLine == null) {
            aircraftLine = new LineEntity();
            aircraftLine.points = new ArrayList<>();
        } else {
            removeLine(aircraftLine);
            if (aircraftLine.points != null)
                aircraftLine.points.clear();
        }
        if (remoteLocation == null)
            return this;
        aircraftLine.points.add(remoteLocation);
        aircraftLine.points.add(aircraftLocation);
        aircraftLine.setColor(R.color.blue);
        aircraftLine.setWidth(R.dimen.dp_2);
        addLine(aircraftLine);
        return this;
    }


    /**
     * 画遥控器到飞机的线的距离
     */
    @Override
    public IMapUi showAircraftLineText(LocationEntity remoteLocation, LocationEntity aircraftLocation) {
        GeoPoint gPointCenter = GeoPoint.fromCenterBetween(new GeoPoint(remoteLocation.getLatitude(), remoteLocation.getLongitude(),
                        remoteLocation.getAltitude()),
                new GeoPoint(aircraftLocation.getLatitude(), aircraftLocation.getLongitude(), aircraftLocation.getAltitude()));
        String distance = CaculateDistanceUtil.getDistance(remoteLocation.getLatitude(), remoteLocation.getLongitude(),
                aircraftLocation.getLatitude(),
                aircraftLocation.getLongitude());
        double course = remoteLocation.getLatitude() > aircraftLocation.getLatitude()
                ? LocationUtils.getPointYaw(remoteLocation, aircraftLocation)
                : LocationUtils.getPointYaw(aircraftLocation, remoteLocation);
        //最后刷新
        List<Overlay> items = mPoleDistanceOverlay.getItems();
        if (textSymbol == null) {
            textSymbol = new DistanceTextSymbol(mapView);
            TextStyles textStyles = new TextStyles();
            DistanceTextSymbol.DrawOffset drawOffset = new DistanceTextSymbol.DrawOffset();
            drawOffset.xOffset = -20;
            drawOffset.yOffset = 20;
            textSymbol.setmOrientation((float) (course + 90));
            textSymbol.setDrawOffset(drawOffset);
            textStyles.setTextStyle(Paint.Style.FILL, Color.parseColor("#FFFB8C00"), Paint.Align.CENTER,
                    mapView.getResources().getDimensionPixelSize(R.dimen.dp_10));
            textSymbol.setLable(distance + " M");
            textSymbol.setPosition(gPointCenter);
            textSymbol.setTextStyle(textStyles.getTextStyle());
            items.add(textSymbol);
        } else {
            if (!items.contains(textSymbol)) {
                items.add(textSymbol);
            }
            textSymbol.setLable(distance + " M");
            textSymbol.setPosition(gPointCenter);
        }
        return this;
    }

    @Override
    public IMapUi hideAircraft() {
        if (flyMarker != null)
            flyMarker.setVisible(false);
        return this;
    }

    @Override
    public IMapUi addLine(@NonNull LineEntity lines) {
        List<GeoPoint> points = lines.points
                .stream()
                .map((it) -> new GeoPoint(it.getLatitude(), it.getLongitude()))
                .collect(Collectors.toList());
        if (points.size() > 0) {
            mPolyline = new Polyline();
            mPolyline.setOnClickListener((polyline, mapView, eventPos) -> false);
            mPolyline.setPoints(points);
            Paint outlinePaint = mPolyline.getOutlinePaint();
            outlinePaint.setStrokeWidth(mapView.getResources().getDimensionPixelSize(lines.getWidth()));
            outlinePaint.setColor(mapView.getResources().getColor(lines.getColor(), null));
            //polyline.setInfoWindow((InfoWindow) null);
            // polyline.setVisible(true);
            mLineOverlay.add(mPolyline);
            lineMap.put(lines, mPolyline);
            mapView.postInvalidate();
        }
        if (lines.isLineMarkingDistance) {
            drawMissionTask(lines.points);
        }

        return this;
    }

    @Override
    public IMapUi addPolygon(@NonNull PolygonEntity lines) {
        List<GeoPoint> points = new ArrayList<>();
        for (LocationEntity location : lines.points) {
            GeoPoint point = new GeoPoint(location.getLatitude(), location.getLongitude());
            points.add(point);
        }

        if (points.size() > 0) {
            if (lines.graphicsType == 1) {
                Polygon polygon = new Polygon();
                Paint outlinePaint = polygon.getOutlinePaint();
                outlinePaint.setStrokeWidth(mapView.getResources().getDimensionPixelSize(lines.getWidth()));
                outlinePaint.setColor(mapView.getResources().getColor(lines.getColor(), null));
//            outlinePaint.setAlpha(0x8032B5EB);
                polygon.getFillPaint().setColor(0x7ffc641b);
                polygon.setPoints(points);
                mZoneOverlay.add(polygon);
                mapView.postInvalidate();
            } else if (lines.graphicsType == 2) {
                CirclePolygon circleOverlay = new CirclePolygon(mapView);
                circleOverlay.setLocationPoint(points.get(0));
                circleOverlay.setRadius(Double.parseDouble(lines.radius));
                circleOverlay.getFillPaint().setColor(0x7ffc641b);
                circleOverlay.getOutLinePaint().setStrokeWidth(mapView.getResources().getDimensionPixelSize(lines.getWidth()));
                circleOverlay.getOutLinePaint().setColor(mapView.getResources().getColor(lines.getColor(), null));
                mZoneOverlay.add(circleOverlay);
                mapView.postInvalidate();

            } else if (lines.graphicsType == 3) {
                CirclePolygon circleOverlay = new CirclePolygon(mapView);
                circleOverlay.setLocationPoint(points.get(0));
                circleOverlay.setRadius(Double.parseDouble(lines.radius));
                circleOverlay.getFillPaint().setColor(0x7ffc641b);
                circleOverlay.getOutLinePaint().setStrokeWidth(mapView.getResources().getDimensionPixelSize(lines.getWidth()));
                circleOverlay.getOutLinePaint().setColor(mapView.getResources().getColor(lines.getColor(), null));
                mZoneOverlay.add(circleOverlay);
                mapView.postInvalidate();
            }
        }
        return this;
    }

    /**
     * 添加网格
     */
    public void addPolygonGrid(@NonNull PolygonEntity lines) {
        List<GeoPoint> points = new ArrayList<>();
        for (LocationEntity location : lines.points) {
            LogUtils.d("Map-多边形",
                    location.getLatitude() + "," + location.getLongitude());
            GeoPoint point = new GeoPoint(location.getLatitude(), location.getLongitude());
            points.add(point);
        }

        if (points.size() > 0) {
            LogUtils.d("Map-多边形", "类型为：" + lines.graphicsType);
            if (lines.graphicsType == 1) {
                Polygon polygon = new Polygon();
                Paint outlinePaint = polygon.getOutlinePaint();
                outlinePaint.setStrokeWidth(mapView.getResources().getDimensionPixelSize(lines.getWidth()));
                outlinePaint.setColor(mapView.getResources().getColor(lines.getColor(), null));
//            outlinePaint.setAlpha(0x8032B5EB);
                polygon.getFillPaint().setColor(Color.parseColor("#F80303"));
                polygon.setPoints(points);
                mGridOverlay.add(polygon);
                //获取方形格子的中心点
                GeoPoint centerBoxLocation = getCenterLocation(points);
                //添加网格名称
                addPolygonGridName(lines.polygonName, centerBoxLocation);

                mapView.postInvalidate();
            } else if (lines.graphicsType == 2) {
                //获取中心点经纬度
                GeoPoint centerPoint = points.get(0);
                CirclePolygon circleOverlay = new CirclePolygon(mapView);
                circleOverlay.setLocationPoint(centerPoint);
                circleOverlay.setRadius(Double.parseDouble(lines.radius));
                circleOverlay.getFillPaint().setColor(0x3368C8FF);
                circleOverlay.getOutLinePaint().setStrokeWidth(mapView.getResources().getDimensionPixelSize(lines.getWidth()));
                circleOverlay.getOutLinePaint().setColor(mapView.getResources().getColor(lines.getColor(), null));
                mGridOverlay.add(circleOverlay);
                //添加网格名称
                addPolygonGridName(lines.polygonName, centerPoint);
                mapView.postInvalidate();

            } else if (lines.graphicsType == 3) {
                //获取中心点名称
                GeoPoint locationPoint = points.get(0);
                CirclePolygon circleOverlay = new CirclePolygon(mapView);
                circleOverlay.setLocationPoint(locationPoint);
                circleOverlay.setRadius(Double.parseDouble(lines.radius));
                circleOverlay.getFillPaint().setColor(0x3368C8FF);
                circleOverlay.getOutLinePaint().setStrokeWidth(mapView.getResources().getDimensionPixelSize(lines.getWidth()));
                circleOverlay.getOutLinePaint().setColor(mapView.getResources().getColor(lines.getColor(), null));
                mGridOverlay.add(circleOverlay);
                //添加网格名称
                addPolygonGridName(lines.polygonName, locationPoint);
                mapView.postInvalidate();
            }
        }
    }

    /**
     * 清除网格内容
     * 并且清除网格名称
     */
    @Override
    public void clearPolygonGrid() {
        mGridOverlay.getItems().clear();
        mGridTextOverlay.getItems().clear();
    }

    /**
     * 更新控件视图
     */
    public void postInvalidate() {
        mapView.postInvalidate();
    }


    /**
     * 绘制网格 名称
     */
    private GeoPoint getCenterBoxLocation(GeoPoint location, GeoPoint location1) {
        double lat1 = Math.toRadians(location.getLatitude());
        double lat2 = Math.toRadians(location1.getLatitude());
        double lon1 = Math.toRadians(location.getLongitude());
        double lon2 = Math.toRadians(location1.getLongitude());

        double avgLat = (lat1 + lat2) / 2;
        double avgLon = (lon1 + lon2) / 2;

        //获取图形中间的维度
        double latitudeCenter = Math.toDegrees(avgLat);
        //获取图形中间的经度
        double longitudeCenter = Math.toDegrees(avgLon);

        return new GeoPoint(latitudeCenter, longitudeCenter);
    }

    /**
     * 绘制网格 名称
     */
    private GeoPoint getCenterLocation(List<GeoPoint> circlePoints) {
        double sumLatitude = 0.0;
        double sumLongitude = 0.0;

        for (GeoPoint point : circlePoints) {
            sumLatitude += point.getLatitude();
            sumLongitude += point.getLongitude();
        }

        double averageLatitude = sumLatitude / circlePoints.size();

        double averageLongitude = sumLongitude / circlePoints.size();

        return new GeoPoint(averageLatitude, averageLongitude);
    }

    /**
     * 绘制网格 名称
     */
    @Override
    public IMapUi addPolygonGridName(@NonNull String name, GeoPoint location) {
        LogUtils.d("Map-多边形", "名称为：" + name);
        LogUtils.d("Map-多边形", "latitudeCenter：" + location.getLatitude() + "-------longitudeCenter: " + location.getLongitude());
        //获取文字样式
        TextStyles textStyles = new TextStyles();
        //获取文字的Overlay
        TextSymbol textSymbol = new TextSymbol(mapView);
        textStyles.setTextStyle(Paint.Style.FILL, Color.parseColor("#FFFB8C00"), Paint.Align.CENTER, 35);
        textSymbol.setLable(name);
        textSymbol.setTextStyle(textStyles.getTextStyle());
        textSymbol.setPosition(location);
        mGridTextOverlay.getItems().add(textSymbol);
        mapView.postInvalidate();
        return this;
    }

    @Override
    public IMapUi removeLine(@NonNull LineEntity line) {
        Polyline remove = lineMap.remove(line);
        if (remove != null) {
            mapView.getOverlays().remove(remove);
            mLineOverlay.remove(remove);
        }
        return this;
    }

    /**
     * 展示隐藏网格化台账
     */
    @Override
    public void showPolygonGrid(Boolean show) {
        for (Overlay overlay : mGridOverlay.getItems()) {
            overlay.setEnabled(show);
        }
        mapView.invalidate();
    }

    /**
     * 展示隐藏网格化台账名称
     */
    @Override
    public IMapUi showPolygonGridText(Boolean show) {
        for (Overlay overlay : mGridTextOverlay.getItems()) {
            overlay.setEnabled(show);
        }
        mapView.invalidate();
        return this;
    }

    /**
     * 清除标杆距离文字
     *
     * @return
     */
    public IMapUi clearTowerDistanceText() {
        mPoleDistanceOverlay.getItems().clear();
        mapView.postInvalidate();
        return this;
    }

    @Override
    public IMapUi addPoint(@NonNull PointEntity... points) {
        List<GeoPoint> geoPoints = new ArrayList<>();
        PolygonStyles polygonStyles = new PolygonStyles();
        TextStyles textStyles = new TextStyles();

        for (PointEntity point : points) {
            LocationEntity location = point.location;
            GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
            geoPoints.add(geoPoint);
        }

        for (int i = 0; i < geoPoints.size(); i++) {
            //坐标点画圆
            PolygonSymbol polygonSymbol = new PolygonSymbol(mapView);
            polygonSymbol.setmSymbol(polygonStyles.getmSymbol());
            polygonSymbol.setRadius(polygonStyles.getRadius());
            polygonSymbol.setPolygonStyle(polygonStyles.getPolygonStyle());
            polygonSymbol.setPosition(geoPoints.get(i));
            mPolygonSymbols.add(polygonSymbol);

            //坐标点添加序号
            TextSymbol textSymbol = new TextSymbol(mapView);
            textSymbol.setLable(String.valueOf(i + 1));
            textSymbol.setTextStyle(textStyles.getTextStyle());
            textSymbol.setPosition(geoPoints.get(i));
            mTextSymbol.add(textSymbol);
        }

        mPointOverlay.getItems().addAll(mPolygonSymbols);
        mPointOverlay.getItems().addAll(mTextSymbol);

        return this;
    }

    @Override
    public IMapUi removePoint(@NonNull PointEntity... points) {
        List<Overlay> items = mPointOverlay.getItems();
        for (Overlay item : items) {
            if (!(item instanceof Marker))
                continue;
            GeoPoint position = ((Marker) item).getPosition();
            for (PointEntity point : points) {
                LocationEntity location = point.location;
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                double altitude = location.getAltitude();

                if (position.getLatitude() == latitude
                        && position.getLongitude() == longitude
                        && position.getAltitude() == altitude)
                    mPointOverlay.remove(item);
            }
        }
        return this;
    }

    @Override
    public IMapUi clearLine() {
        Set<Map.Entry<LineEntity, Polyline>> entries = lineMap.entrySet();
        for (Map.Entry<LineEntity, Polyline> entry : entries) {
            Polyline value = entry.getValue();
            if (value != null) {
                mLineOverlay.remove(value);
                mapView.getOverlays().remove(value);
            }
        }
        //清除杆塔线的同时，清除距离文字
        clearTowerDistanceText();
        mLineOverlay.getItems().remove(mPolyline);
        mLineOverlay.getItems().clear();
        return this;
    }

    @Override
    public IMapUi clearPoint() {
        if (mPolygonSymbols.size() > 0 && mPolygonSymbols != null) {
            mPointOverlay.getItems().removeAll(mPolygonSymbols);
            mPolygonSymbols.clear();
        }
        if (mTextSymbol.size() > 0 && mTextSymbol != null) {
            mPointOverlay.getItems().removeAll(mTextSymbol);
            mTextSymbol.clear();
        }
        return this;
    }

    @Override
    public IMapUi changTileSource(int sourceType) {
//        mapView.setTileSource(new SourceOnlineStreetNormal());
        List<Overlay> items = mCityOverlay.getItems();
        List<Overlay> items1 = mCityStreetOverlay.getItems();
        //如果当前选中图层是展示状态，跳出
        if (items.get(sourceType).isEnabled()) {
            return this;
        }

        //获取所有的街道，设置为关闭
        for (Overlay item : items1) {
            item.setEnabled(false);
        }
        //获取所有的图层，设置为关闭
        for (Overlay item : items) {
            item.setEnabled(false);
        }
        //设置当前选中图层展示
        items.get(sourceType).setEnabled(true);

        if (sourceType < items1.size()) {
            //设置当前选中街道展示
            items1.get(sourceType).setEnabled(true);
        }
        switch (sourceType) {
            case 0:
                //默认一直加载第一个资源
                mapView.setTileSource(new TianDituVectorTileSource());
                break;
            case 1:
                //默认一直加载第一个资源
                mapView.setTileSource(new TianDituSatelliteNoteSource());
                break;
            case 2:
                //默认一直加载第一个资源
                mapView.setTileSource(new SourceOnlineStreetNormal());
                break;
            case 3:
                //默认一直加载第一个资源
                mapView.setTileSource(new SourceOnlineStreetTerrain());
                break;
        }
//        mapView.getTileProvider().clearTileCache();
        mapView.postInvalidate();
        return this;
    }

    @Override
    public List<Marker> towerMarkerList() {
        return mTowerPolygon;
    }

    public void drawMissionTask(List<LocationEntity> points, String unitText) {
        //防止坐标不全出错
        if (points == null || points.size() <= 1) return;
        List<GeoPoint> gPoints = new ArrayList<>();
        List<String> distanceList = new ArrayList<>();
        List<Float> angleList = new ArrayList<>();
        LocationEntity currentLoction = null;
        for (LocationEntity location : points) {
            if (currentLoction != null && currentLoction != location) {
                GeoPoint gPointCenter = GeoPoint.fromCenterBetween(new GeoPoint(currentLoction.getLatitude(),
                        currentLoction.getLongitude(), currentLoction.getAltitude()), new GeoPoint(location.getLatitude(),
                        location.getLongitude(), location.getAltitude()));
                String distance = CaculateDistanceUtil.getDistance(currentLoction.getLatitude(), currentLoction.getLongitude(),
                        location.getLatitude(), location.getLongitude());
                gPoints.add(gPointCenter);
                float pointYaw = (float) LocationUtils.getPointYaw(currentLoction, location);
                angleList.add(pointYaw);
                distanceList.add(unitText + distance);
            }
            currentLoction = location;
        }
        //添加距离文字
        markTower(gPoints, distanceList, angleList);
    }

    public void drawMissionTask(List<LocationEntity> points) {
        drawMissionTask(points, "");
    }

    public IMapUi markTower(List<GeoPoint> gPoints, List<String> distanceList, List<Float> angleList) {
        for (int i = 0; i < gPoints.size(); i++) {
            DistanceTextSymbol textSymbol = new DistanceTextSymbol(mapView);
            TextStyles textStyles = new TextStyles();
            DistanceTextSymbol.DrawOffset drawOffset = new DistanceTextSymbol.DrawOffset();
            drawOffset.xOffset = 0;
            drawOffset.yOffset = 0;
            textSymbol.setmOrientation(angleList.get(i) + 90);
            textSymbol.setDrawOffset(drawOffset);
            textStyles.setTextStyle(Paint.Style.FILL, Color.parseColor("#FFFB8C00"), Paint.Align.CENTER,
                    mapView.getResources().getDimensionPixelSize(R.dimen.dp_10));
            textSymbol.setLable(distanceList.get(i) + " M");
            textSymbol.setPosition(gPoints.get(i));
            textSymbol.setTextStyle(textStyles.getTextStyle());
            mPoleDistanceOverlay.getItems().add(textSymbol);
        }
        mapView.postInvalidate();
        return this;
    }

    @Override
    public IMapUi showGoHomeLocation(LocationEntity goHomeLocation) {
        GeoPoint startPoint = new GeoPoint(goHomeLocation.getLatitude(), goHomeLocation.getLongitude());
        if (mapView == null || mapView.getParent() == null)
            return this;
        if (goHomeMarker == null) {
            goHomeMarker = new Marker(mapView);
            goHomeMarker.setVisible(true);
            goHomeMarker.setIcon(ContextCompat.getDrawable(this.mapView.getContext(), R.mipmap.global_go_home_icon));
            goHomeMarker.setPosition(startPoint);
            goHomeMarker.setInfoWindow(null);
            goHomeMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
            mGoHomeOverlay.add(goHomeMarker);
        } else {
            //地图航向是镜像航向 需计算转换一下
//            flyMarker.setRotation(-aircraftLocation.getYaw());
            goHomeMarker.setPosition(startPoint);
            mapView.postInvalidate();
        }
        return this;
    }
}
