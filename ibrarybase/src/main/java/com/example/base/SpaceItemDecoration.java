package com.example.base;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 间距分割线
 */
@SuppressWarnings("unused")
public final class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int space;
    Paint mPaint;

    public SpaceItemDecoration(int space) {
        this.space = space;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xffededed);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private static final int HORIZONTAL = 355;

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager == null)
            return;
        int itemViewType = layoutManager.getItemViewType(view);
        if (itemViewType == HORIZONTAL) {  //根据ViewType类型来判断是否展示线
            outRect.bottom = space;
            if (parent.getChildLayoutPosition(view) % 2 == 0) {
                outRect.right = space / 2;
                outRect.left = space;
            } else {
                outRect.right = space;
                outRect.left = space / 2;
            }
        } else {
            RecyclerView.Adapter adapter = parent.getAdapter();
            if (adapter == null)
                return;
            int childCount = adapter.getItemCount();
            int childLayoutPosition = parent.getChildLayoutPosition(view);
            //最后一个条目不加空隙
            if (childCount != childLayoutPosition + 1)
                outRect.bottom = space;
            else
                outRect.bottom = 0;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, @NonNull RecyclerView.State state) {
        c.save();
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            if ((((GridLayoutManager) parent.getLayoutManager())).getSpanCount() == 2) {
                draw(c, parent);
            }
        }
        c.restore();

    }

    //绘制横向 item 分割线
    private void draw(Canvas canvas, RecyclerView parent) {
        int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft();
            int right = child.getRight();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + space;
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);//绘制图片下放的水平线
            }
            if (parent.getChildLayoutPosition(child) % 2 == 0) {
                canvas.drawRect(0, child.getTop(), left, child.getBottom() + space, mPaint);//绘制左边图片的左间线
            }
            top = child.getTop();
            bottom = child.getBottom() + space;
            left = child.getRight() + layoutParams.rightMargin;
            right = left + space;
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);//绘制图片的右间线
            }
        }
    }

}