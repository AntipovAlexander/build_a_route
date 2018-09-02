package com.antipov.buildaroute.ui.fragment.history;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.antipov.buildaroute.R;
import com.antipov.buildaroute.data.db.entities.Route;
import com.antipov.buildaroute.interfaces.OnReplayRouteClicked;
import com.antipov.buildaroute.ui.adapter.HistoryListAdapter;
import com.antipov.buildaroute.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryFragment extends BaseFragment implements HistoryView, OnReplayRouteClicked {

    @Inject
    HistoryPresenter<HistoryView, HistoryInteractor> presenter;

    @BindView(R.id.fl_progress) FrameLayout progress;
    @BindView(R.id.empty_layout) RelativeLayout emptyState;
    @BindView(R.id.error_layout) RelativeLayout errorLayout;
    @BindView(R.id.rv_history) RecyclerView history;
    @BindView(R.id.btn_refresh) Button refresh;
    @BindView(R.id.btn_try_again) Button tryAgain;
    private HistoryListAdapter adapter;
    private OnReplayRouteClicked listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.listener = (OnReplayRouteClicked) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Host activity must implement OnReplayRouteClicked");
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAppComponent().inject(this);
        initAdapter();
        presenter.attachView(this);
        presenter.loadHistory();
    }

    private void initAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseActivity());
        adapter = new HistoryListAdapter(this);
        history.setLayoutManager(layoutManager);
        history.setAdapter(adapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    public void getExtras() {

    }

    @Override
    public void initViews() {
        ButterKnife.bind(this, getInflatedView());
    }

    @Override
    public void initListeners() {
        View.OnClickListener listener = view -> {
            errorLayout.setVisibility(View.GONE);
            emptyState.setVisibility(View.GONE);
            presenter.loadHistory();
        };
        // same refresh listener for both buttons
        refresh.setOnClickListener(listener);
        tryAgain.setOnClickListener(listener);
    }

    @Override
    public void showLoadingFullscreen() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingFullscreen() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void renderList(List<Route> history) {
        adapter.setData(history);
    }

    @Override
    public void onError(String message) {
        errorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNoHistoryFound() {
        emptyState.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onReplayRouteClicked(String encodedRoute) {
        this.listener.onReplayRouteClicked(encodedRoute);
    }
}
