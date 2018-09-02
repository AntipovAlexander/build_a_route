package com.antipov.buildaroute.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.antipov.buildaroute.R;
import com.antipov.buildaroute.interfaces.OnReplayRouteClicked;
import com.antipov.buildaroute.ui.adapter.MainPagerAdapter;
import com.antipov.buildaroute.ui.base.BaseActivity;
import com.antipov.buildaroute.ui.fragment.history.HistoryFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements OnReplayRouteClicked {

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

    @Override
    public void onReplayRouteClicked(String encodedRoute) {
        // finding attached fragment and converting into interface
        OnReplayRouteClicked listener = (OnReplayRouteClicked) getSupportFragmentManager()
                .findFragmentByTag(makeFragmentName(R.id.vp_viewpager, 0));
        // calling method
        if (listener != null) listener.onReplayRouteClicked(encodedRoute);
    }

    private String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }
}
