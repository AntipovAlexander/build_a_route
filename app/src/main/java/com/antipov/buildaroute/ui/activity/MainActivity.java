package com.antipov.buildaroute.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.antipov.buildaroute.R;
import com.antipov.buildaroute.ui.adapter.MainPagerAdapter;
import com.antipov.buildaroute.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tl_tab_layout) TabLayout tabLayout;
    @BindView(R.id.vp_viewpager) ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPager();
    }

    private void initPager() {
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), this));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initToolbar() {

    }

    @Override
    public void showLoadingFullscreen() {

    }

    @Override
    public void hideLoadingFullscreen() {

    }
}
