package com.example.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.lt.func.ItemClickListener;
import com.lt.func.ItemLongClickListener;

import java.util.List;

import me.jessyan.autosize.AutoSize;

/**
 * 项目中 自用 抽象 recyclerview的adapter，结合{@link BaseHolder} 可快速实现列表
 *
 * @param <T> 当前adapter的item绑定的数据类型。
 * @param <S> 当前adapter对应的 viewholder类型
 *            禁止使用普通内部类方式创建viewholder
 */
public abstract class BaseAdapter<T, S extends BaseHolder<T>> extends RecyclerView.Adapter<S> {
    protected List<T> data;
    protected ItemClickListener itemClickListener;
    protected Context mContext;
    protected LayoutInflater layoutInflater;

    protected ItemLongClickListener itemLongClickListener;
    protected RecyclerView recyclerView;

    @Override
    @CallSuper
    public final void onBindViewHolder(@NonNull S holder, int position) {
        AutoSize.autoConvertDensityOfGlobal(ActivityUtils.getTopActivity());
        T t1 = data.get(position);
        holder.bind();
        holder.attachData(position, t1);
        holder.itemView.setOnClickListener((t) -> {
            if (itemClickListener != null)
                itemClickListener.onItemClicked(holder.itemView, position);
        });
        if (itemLongClickListener != null)
            holder.itemView.setOnLongClickListener((it) -> itemLongClickListener.onItemLongClicked(holder.itemView, position));
    }

    @Override
    public void onViewRecycled(@NonNull S holder) {
        super.onViewRecycled(holder);
        holder.itemView.setOnClickListener(null);
        holder.itemView.setOnLongClickListener(null);
        holder.release();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 该函数调用必须在设置适配器之前
     *
     * @param itemLongClickListener
     */
    public void setItemLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    public List<T> getData() {
        return data;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        this.data.addAll(data);
        notifyItemRangeInserted(this.data.size() - data.size(), this.data.size());
    }

    public void addData(int position, List<T> data) {
        this.data.addAll(position, data);
        notifyItemRangeInserted(position, position + data.size());
    }

    public void addData(T data) {
        addData(this.data.size(), data);
    }

    public void addData(int position, T data) {
        this.data.add(position, data);
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        if (position >= data.size())
            return;
        this.data.remove(position);
        notifyItemRemoved(position);
    }

    public void removeData(T data) {
        removeData(this.data.indexOf(data));
    }

    @Override
    public int getItemCount() {
        List<T> data = getData();
        return data == null ? 0 : data.size();
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mContext = recyclerView.getContext();
        this.recyclerView = recyclerView;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
        mContext = null;
        layoutInflater = null;
        data = null;
        itemClickListener = null;
        itemLongClickListener = null;
    }
}
