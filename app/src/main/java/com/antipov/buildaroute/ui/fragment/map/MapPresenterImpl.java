package com.antipov.buildaroute.ui.fragment.map;

import com.antipov.buildaroute.ui.base.BasePresenter;

import javax.inject.Inject;

public class MapPresenterImpl <V extends MapView, I extends MapInteractor> extends BasePresenter<V, I>
        implements MapPresenter<V, I> {

    @Inject
    public MapPresenterImpl(I interactor) {
        super(interactor);
    }

    @Override
    public void onMapButtonClick(int request) {
        if (isViewNotAttached()) return;
        getView().showAddressDialog(request);
    }
}
