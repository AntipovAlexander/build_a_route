package com.antipov.buildaroute.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.antipov.buildaroute.data.pojo.WayPoint;

import java.util.ArrayList;
import java.util.List;

public class AutocompleteAdapter extends ArrayAdapter<WayPoint> {
    private final List<WayPoint> data;
    private final int itemLayout;
    private final OnAutocompleteSelected listener;

    public AutocompleteAdapter(@NonNull Context context, int resource, OnAutocompleteSelected listener) {
        super(context, resource);
        this.data = new ArrayList<>();
        this.itemLayout = resource;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public WayPoint getItem(int position) {
        return data.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
        }

        view.setOnClickListener(l -> listener.OnAutocompleteSelected(data.get(position)));

        TextView strName = view.findViewById(android.R.id.text1);
        strName.setText(getItem(position).getFormattedAddress());
        return view;
    }

    public void setAutocomplete(List<WayPoint> results) {
        data.clear();
        data.addAll(results);
        notifyDataSetChanged();
    }

    public interface OnAutocompleteSelected {
        void OnAutocompleteSelected(WayPoint item);
    }
}
