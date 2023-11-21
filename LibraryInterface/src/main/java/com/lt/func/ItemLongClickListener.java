package com.lt.func;

import android.view.View;

/**
 * 用作 recyclerview中 长按item的事件响应
 */
public interface ItemLongClickListener extends IReleasable{
    boolean onItemLongClicked(View itemView, int position);

    @Override
    default void release() {

    }
}
