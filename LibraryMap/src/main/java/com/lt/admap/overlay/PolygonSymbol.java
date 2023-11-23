package com.lt.admap.overlay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;

public class PolygonSymbol extends MarkerSymbol {
    /**
     * SQUARE 方形
     */
    public enum Shape {CIRCLE, SQUARE}

    protected Shape mSymbol = Shape.CIRCLE;
    /**
     * 圆形半径
     */
    private float radius = 5;

    private float width = 0, height = 0;

    public PolygonSymbol(MapView mapView) {
        super(mapView);
    }

    public PolygonSymbol(MapView mapView, Context resourceProxy) {
        super(mapView, resourceProxy);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Shape getmSymbol() {
        return mSymbol;
    }

    public void setmSymbol(Shape mSymbol) {
        this.mSymbol = mSymbol;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public void draw(Canvas pCanvas, Projection pProjection) {
        pProjection.toPixels(mPosition, mPositionPixels);
        drawPointAt(pCanvas, mPositionPixels.x, mPositionPixels.y, paintStyle, pProjection.getOrientation());
    }

    private Paint paintStyle;

    private void getDefaultPolygonStyle() {
        paintStyle = new Paint();
        paintStyle.setStyle(Paint.Style.FILL);
        paintStyle.setColor(Color.GREEN);
    }

    public void setPolygonStyle(Paint.Style style, int color) {
        paintStyle = new Paint();
        paintStyle.setStyle(style);
        paintStyle.setColor(color);
    }

    public void setPolygonStyle(Paint paint) {
        this.paintStyle = paint;
    }


    protected void drawPointAt(Canvas canvas, float x, float y, Paint pointStyle, float pOrientation) {
        canvas.save();
        canvas.rotate(pOrientation, x, y);
        if (paintStyle == null)
            getDefaultPolygonStyle();
        if (mSymbol == Shape.CIRCLE) {
            canvas.drawCircle(x, y, radius, pointStyle);
        } else if (mSymbol == Shape.SQUARE) {
            float divWidth = width / 2f;
            float divHeiht = height / 2f;
            canvas.drawRect(x - divWidth, y - divHeiht
                    , x + divWidth, y + divHeiht
                    , pointStyle);
        }
        canvas.restore();
    }
}
