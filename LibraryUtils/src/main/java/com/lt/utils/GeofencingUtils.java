package com.lt.utils;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author:Dell
 * @date：2023/11/2
 * @desc: 电子围栏计算
 * @time:13:15
 */

public class GeofencingUtils {
    /**
     * 地球半径(米)
     */
    private static final double EARTH_RADIUS = 6378137.0;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 计算是否在圆内
     *
     * @param radius 半径（单位/米）
     * @param p1     圆心坐标
     * @param p2     判断点坐标
     * @return: boolean true:在圆内,false:在圆外
     * @date: 2021-11-08 09:44:54
     */
    public static boolean isInCircle(double radius, Point p1, Point p2) {
        double radLat1 = rad(p1.getLat());
        double radLat2 = rad(p2.getLat());
        double a = radLat1 - radLat2;
        double b = rad(p1.getLng()) - rad(p2.getLng());
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return !(s > radius);
    }

    /**
     * 是否在矩形区域内
     *
     * @param lng    测试点经度
     * @param lat    测试点纬度
     * @param minLng 矩形四个点中最小经度
     * @param maxLng 矩形四个点中最大经度
     * @param minLat 矩形四个点中最小纬度
     * @param maxLat 矩形四个点中最大纬度
     * @return boolean true:在矩形内, false:在矩形外
     * @Title: isInArea
     */
    public static boolean isInRectangleArea(double lng, double lat, double minLng, double maxLng,
                                            double minLat, double maxLat) {
        if (isInRange(lat, minLat, maxLat)) {//如果在纬度的范围内
            if (minLng * maxLng > 0) {
                return isInRange(lng, minLng, maxLng);
            } else {
                if (Math.abs(minLng) + Math.abs(maxLng) < 180) {
                    return isInRange(lng, minLng, maxLng);
                } else {
                    double left = Math.max(minLng, maxLng);
                    double right = Math.min(minLng, maxLng);
                    return isInRange(lng, left, 180) || isInRange(lng, right, -180);
                }
            }
        } else {
            return false;
        }
    }

    /**
     * 是否在矩形区域内
     *
     * @param point     测试点
     * @param gpsPoints 矩形GPS四个坐标点
     * @return boolean true:在矩形内, false:在矩形外
     * @Title: isInArea
     */
    public static boolean isInRectangleArea(Point point, Point[] gpsPoints) {
        if (gpsPoints.length != 4) {
            return false;
        }
        double[] lats = new double[4];
        double[] lngs = new double[4];
        for (int i = 0; i < gpsPoints.length; i++) {
            lats[i] = gpsPoints[i].getLat();
            lngs[i] = gpsPoints[i].getLng();
        }
        Arrays.sort(lats);
        Arrays.sort(lngs);
        return isInRectangleArea(point.getLat(), point.getLng(), lats[0], lats[3], lngs[0], lngs[3]);
    }


    /**
     * 判断是否在经纬度范围内
     *
     * @param point
     * @param left
     * @param right
     * @return boolean
     */
    public static boolean isInRange(double point, double left, double right) {
        return point >= Math.min(left, right) && point <= Math.max(left, right);
    }

    /**
     * 判断点是否在多边形内
     *
     * @param point 测试点
     * @param pts   多边形的点
     * @return boolean true:在多边形内, false:在多边形外
     * @throws
     * @Title: IsPointInPoly
     */
    public static boolean isInPolygon(Point point, List<Point> pts) {
        if (pts.size()<1){
            return false;
        }
        int N = pts.size();
        boolean boundOrVertex = true;
        int intersectCount = 0;//交叉点数量
        double precision = 2e-10; //浮点类型计算时候与0比较时候的容差
        Point p1, p2;//临近顶点
        Point p = point; //当前点

        p1 = pts.get(0);
        for (int i = 1; i <= N; ++i) {
            if (p.equals(p1)) {
                return boundOrVertex;
            }

            p2 = pts.get(i % N);
            if (p.getLng() < Math.min(p1.getLng(), p2.getLng()) || p.getLng() > Math.max(p1.getLng(), p2.getLng())) {
                p1 = p2;
                continue;
            }

            //射线穿过算法
            if (p.getLng() > Math.min(p1.getLng(), p2.getLng()) && p.getLng() < Math.max(p1.getLng(), p2.getLng())) {
                if (p.getLat() <= Math.max(p1.getLat(), p2.getLat())) {
                    if (p1.getLng() == p2.getLng() && p.getLat() >= Math.min(p1.getLat(), p2.getLat())) {
                        return boundOrVertex;
                    }

                    if (p1.getLat() == p2.getLat()) {
                        if (p1.getLat() == p.getLat()) {
                            return boundOrVertex;
                        } else {
                            ++intersectCount;
                        }
                    } else {
                        double xinters = (p.getLng() - p1.getLng()) * (p2.getLat() - p1.getLat()) / (p2.getLng() - p1.getLng()) + p1.getLat();
                        if (Math.abs(p.getLat() - xinters) < precision) {
                            return boundOrVertex;
                        }

                        if (p.getLat() < xinters) {
                            ++intersectCount;
                        }
                    }
                }
            } else {
                if (p.getLng() == p2.getLng() && p.getLat() <= p2.getLat()) {
                    com.lt.utils.Point p3 = pts.get((i + 1) % N);
                    if (p.getLng() >= Math.min(p1.getLng(), p3.getLng()) && p.getLng() <= Math.max(p1.getLng(), p3.getLng())) {
                        ++intersectCount;
                    } else {
                        intersectCount += 2;
                    }
                }
            }
            p1 = p2;
        }
        return intersectCount % 2 != 0;
    }
}
