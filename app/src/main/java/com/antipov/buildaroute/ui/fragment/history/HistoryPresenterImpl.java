package com.antipov.buildaroute.ui.fragment.history;

import com.antipov.buildaroute.ui.base.BasePresenter;

import javax.inject.Inject;

public class HistoryPresenterImpl <V extends HistoryView, I extends HistoryInteractor>
        extends BasePresenter<V, I> implements HistoryPresenter<V, I> {

    @Inject
    public HistoryPresenterImpl(I interactor) {
        super(interactor);
    }

    @Override
    public void loadHistory() {
        if (isViewNotAttached()) return;
        getView().showLoadingFullscreen();
        getInteractor()
                .loadHistory()
                .subscribe(history -> {
                            if (isViewNotAttached()) return;
                            getView().hideLoadingFullscreen();
                            if (history.size() == 0) {
                                getView().onNoHistoryFound();
                            } else {
                                getView().renderList(history);
                            }
                        },
                        throwable -> {
                            if (isViewNotAttached()) return;
                            getView().hideLoadingFullscreen();
                            getView().onError(throwable.getMessage());
                        });
    }
}
