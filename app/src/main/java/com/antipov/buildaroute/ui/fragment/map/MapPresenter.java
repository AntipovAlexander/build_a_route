package com.antipov.buildaroute.ui.fragment.map;

import com.antipov.buildaroute.ui.base.IBasePresenter;

public interface MapPresenter<V extends MapView, I extends MapInteractor> extends IBasePresenter<V, I> {
    void onMapButtonClick(int request);
}
