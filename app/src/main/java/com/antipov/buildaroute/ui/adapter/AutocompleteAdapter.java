package com.antipov.buildaroute.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.antipov.buildaroute.data.pojo.AutocompleteItem;

import java.util.ArrayList;
import java.util.List;

public class AutocompleteAdapter extends ArrayAdapter<String> {
    private final List<String> data;
    private final Context context;
    private final int itemLayout;

    public AutocompleteAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.data = new ArrayList<>();
        this.itemLayout = resource;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
        }

        TextView strName = view.findViewById(android.R.id.text1);
        strName.setText(getItem(position));
        return view;
    }

    public void setAutocomplete(List<AutocompleteItem> results) {
        data.clear();
        for (AutocompleteItem result: results) {
            data.add(result.getFormattedAddress());
        }
        notifyDataSetChanged();
    }
}
