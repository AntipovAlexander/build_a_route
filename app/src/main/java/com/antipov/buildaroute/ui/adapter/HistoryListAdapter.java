package com.antipov.buildaroute.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.antipov.buildaroute.R;
import com.antipov.buildaroute.data.db.entities.Route;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {

    private List<Route> data = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.recycler_item_history, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        vh.playRoute.setOnClickListener(l -> {});
        return vh;
    }

    private String convertToHumanReadableTime(Long createdAt) {
        Date date = new Date(createdAt);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        return dateFormat.format(date);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.number.setText(String.valueOf(i+1));
        viewHolder.date.setText(
                convertToHumanReadableTime(data.get(i).getCreatedAt())
        );

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Route> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ib_play) ImageButton playRoute;
        @BindView(R.id.tv_number) TextView number;
        @BindView(R.id.tv_date) TextView date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
