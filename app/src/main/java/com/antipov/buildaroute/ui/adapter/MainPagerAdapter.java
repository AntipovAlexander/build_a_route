package com.antipov.buildaroute.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.antipov.buildaroute.R;
import com.antipov.buildaroute.ui.fragment.history.HistoryFragment;
import com.antipov.buildaroute.ui.fragment.map.MapFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private final int PAGES_COUNT = 2;
    private final String[] names;

    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        names = context.getResources().getStringArray(R.array.tabs_name);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new MapFragment();
            case 1:
                return new HistoryFragment();
            default:
                throw new RuntimeException("Index "+i+" not associated with fragment");
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return names[position];
    }

    @Override
    public int getCount() {
        return PAGES_COUNT;
    }
}
