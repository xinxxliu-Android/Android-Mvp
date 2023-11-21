package com.example.base;

import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lt.func.IReleasable;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 结合{@link BaseAdapter} 快速实现列表。
 *
 * @param <T> 当前itemview绑定的数据类型
 */
public abstract class BaseHolder<T> extends RecyclerView.ViewHolder implements IReleasable {
    protected Unbinder unbinder;

    public BaseHolder(@NonNull View itemView) {
        super(itemView);
    }

    final void bind() {
        unbinder = ButterKnife.bind(this, itemView);
    }

    protected abstract void attachData(int position, T t);

    @CallSuper
    @Override
    public void release() {
        if (unbinder != null)
            unbinder.unbind();
        unbinder = null;
    }
}
