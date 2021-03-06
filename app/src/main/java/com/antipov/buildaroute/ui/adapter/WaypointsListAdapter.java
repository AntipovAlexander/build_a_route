package com.antipov.buildaroute.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.antipov.buildaroute.R;
import com.antipov.buildaroute.data.pojo.autocomplete.WayPoint;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WaypointsListAdapter extends RecyclerView.Adapter<WaypointsListAdapter.ViewHolder> {

    private final OnWaypointDeleteListener listener;
    private List<WayPoint> data = new ArrayList<>();
    private boolean isClickable = true;

    public WaypointsListAdapter(OnWaypointDeleteListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.recycler_item_waypoint, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        vh.delete.setOnClickListener(l -> {
            int position = vh.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && isClickable) {
                // remove marker
                data.remove(position);
                listener.onWaypointDeleteListener(position);
                notifyItemRemoved(position);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.address.setText(data.get(i).getFormattedAddress());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(WayPoint item) {
        data.add(item);
        notifyItemInserted(data.size());
    }

    public List<WayPoint> getData() {
        return data;
    }

    public void setClickable(boolean isClickable) {
        this.isClickable = isClickable;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_address) TextView address;
        @BindView(R.id.btn_delete) ImageButton delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnWaypointDeleteListener {
        void onWaypointDeleteListener(int i);
    }
}
