package com.lt.window.settings.rtk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lt.dj.rtk.RtkBaseStationInfor;
import com.lt.widget.v.ImageView;
import com.lt.widget.v.TextView;
import com.lt.window.R;

import java.util.List;

/**
 * Rtk基站展示列表
 * 适配器
 */
public class RtkBaseStationAdapter extends RecyclerView.Adapter<RtkBaseStationAdapter.ViewHolder> {

    private List<RtkBaseStationInfor> list;

    private Context mContext;

    private itemClickListener clickListener;

    public RtkBaseStationAdapter(Context context) {
        this.mContext = context;
    }

    public void setList(List<RtkBaseStationInfor> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setClickListener(itemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    interface itemClickListener {
        void onClick(RtkBaseStationInfor infor);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new RtkBaseStationAdapter.ViewHolder(inflater.inflate(R.layout.item_module_window_base_station_rtk, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list == null) {
            return;
        }
        RtkBaseStationInfor infor = list.get(position);
        holder.name.setText(infor.getBaseStationName());
        int signalLevel = infor.getSignalLevel();
        holder.imageView.setImageLevel(signalLevel);
        holder.itemView.setOnClickListener(v -> {
            if (clickListener == null)
                return;
            clickListener.onClick(infor);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;

        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.rtk_name);
            imageView = itemView.findViewById(R.id.icon_satellite_signal);
        }
    }
}
