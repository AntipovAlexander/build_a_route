package com.antipov.buildaroute.ui.fragment.history;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.antipov.buildaroute.R;
import com.antipov.buildaroute.ui.base.BaseFragment;

import javax.inject.Inject;

public class HistoryFragment extends BaseFragment implements HistoryView {

    @Inject
    HistoryPresenter<HistoryView, HistoryInteractor> presenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAppComponent().inject(this);
        presenter.attachView(this);
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

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void showLoadingFullscreen() {

    }

    @Override
    public void hideLoadingFullscreen() {

    }
}
