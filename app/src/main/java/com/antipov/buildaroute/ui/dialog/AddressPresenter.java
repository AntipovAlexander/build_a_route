package com.antipov.buildaroute.ui.dialog;

import com.antipov.buildaroute.ui.base.IBasePresenter;

import io.reactivex.Observable;

public interface AddressPresenter<V extends AddressView, I extends AddressInteractor> extends IBasePresenter<V, I> {
    void subscribeAutoComplete(Observable<String> string);

    void unSubscribeAutoComplete();
}
