package com.lt.admap.styles;

import android.graphics.Color;
import android.graphics.Paint;

import com.lt.admap.overlay.PolygonSymbol;

public class PolygonStyles {
    private Paint paintStyle;
    /**
     * 圆形半径
     */
    private float radius = 35;

    private float width = 5, height = 5;
    protected PolygonSymbol.Shape mSymbol = PolygonSymbol.Shape.CIRCLE;

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

    public Paint getPolygonStyle() {
        if (paintStyle == null) {
            getDefaultPolygonStyle();
        }
        return paintStyle;
    }

    public void setPolygonStyle(Paint paint) {
        this.paintStyle = paint;
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


    public PolygonSymbol.Shape getmSymbol() {
        return mSymbol;
    }

    public void setmSymbol(PolygonSymbol.Shape mSymbol) {
        this.mSymbol = mSymbol;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
