package com.lt.admap.overlay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;

public class TextSymbol extends MarkerSymbol {


    /**
     * 文字
     */
    private String lable = null;

    private Paint mTextStyle;
    /**
     * 绘制偏移数据
     */
    private DrawOffset drawOffset;


    public TextSymbol(MapView mapView) {
        super(mapView);
    }

    public TextSymbol(MapView mapView, Context resourceProxy) {
        super(mapView, resourceProxy);
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    /**
     * 设置字体样式
     *
     * @param style    画笔样式
     * @param color    字体颜色
     * @param align    排列方式
     * @param textSize 字体大小 px
     */
    public void setTextStyle(Paint.Style style, int color, Paint.Align align, int textSize) {
        mTextStyle = new Paint();
        mTextStyle.setStyle(style);
        mTextStyle.setColor(color);
        mTextStyle.setTextAlign(align);
        mTextStyle.setTextSize(textSize);
    }

    /**
     * @param paint 画笔
     */
    public void setTextStyle(Paint paint) {
        mTextStyle = paint;
    }

    private void getDefaultTextStyle() {
        mTextStyle = new Paint();
        mTextStyle.setStyle(Paint.Style.FILL);
        mTextStyle.setColor(Color.parseColor("#000000"));
        mTextStyle.setTextAlign(Paint.Align.CENTER);
        mTextStyle.setTextSize(24);
    }


    @Override
    public void draw(Canvas canvas, Projection pj) {
        pj.toPixels(mPosition, mPositionPixels);
        if (drawOffset != null) {
            drawPointAt(canvas
                    , mPositionPixels.x + drawOffset.xOffset, mPositionPixels.y - drawOffset.yOffset
                    , lable, mTextStyle, pj.getOrientation());
        } else {
            drawPointAt(canvas, mPositionPixels.x, mPositionPixels.y, lable, mTextStyle, pj.getOrientation());
        }
    }

    protected void drawPointAt(Canvas canvas, float x, float y, String label, Paint textStyle, float pOrientation) {
        canvas.save();
        if (pOrientation != 0)
            canvas.rotate(pOrientation, x, y);
        if (mTextStyle == null)
            getDefaultTextStyle();
        //绘制文字
        if (label != null)
            canvas.drawText(label, x, y, textStyle);
        canvas.restore();
    }

    public DrawOffset getDrawOffset() {
        return drawOffset;
    }

    public void setDrawOffset(DrawOffset drawOffset) {
        this.drawOffset = drawOffset;
    }

    public static class DrawOffset {
        public int xOffset = 0;
        public int yOffset = 0;
    }
}
