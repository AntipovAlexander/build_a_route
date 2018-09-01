package com.antipov.buildaroute.ui.fragment.history;

import com.antipov.buildaroute.ui.base.BasePresenter;

import javax.inject.Inject;

public class HistoryPresenterImpl <V extends HistoryView, I extends HistoryInteractor>
        extends BasePresenter<V, I> implements HistoryPresenter<V, I> {

    @Inject
    public HistoryPresenterImpl(I interactor) {
        super(interactor);
    }
}
