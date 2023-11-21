package com.lt.func;

import android.view.View;

/**
 * recyclerview 的 条目点击事件 公共函数
 */
public interface ItemClickListener extends IReleasable {
    void onItemClicked(View itemView,int position);

    @Override
    default void release(){

    }
}
