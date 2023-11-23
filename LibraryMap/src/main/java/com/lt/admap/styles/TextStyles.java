package com.lt.admap.styles;

import android.graphics.Color;
import android.graphics.Paint;

public class TextStyles {
    private Paint mTextStyle;

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
        mTextStyle.setColor(Color.WHITE);
        mTextStyle.setTextAlign(Paint.Align.CENTER);
        mTextStyle.setTextSize(30);
    }

    public Paint getTextStyle() {
        if (mTextStyle == null) {
            getDefaultTextStyle();
        }
        return mTextStyle;
    }

}
