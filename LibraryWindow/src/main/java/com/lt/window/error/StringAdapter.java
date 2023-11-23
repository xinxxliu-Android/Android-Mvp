package com.lt.window.error;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lt.widget.v.TextView;
import com.lt.window.R;

import java.util.List;

/**
 * 列表中只展示文本内容
 *
 * @author Y
 */
public class StringAdapter extends RecyclerView.Adapter<StringAdapter.ViewHolder> {

    private List<String> list;
    private Context mContext;

    public StringAdapter(Context context, List<String> list) {
        this.list = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new ViewHolder(inflater.inflate(R.layout.item_module_window_window_error, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView.findViewById(R.id.item_error_msg);
        }
    }
}
