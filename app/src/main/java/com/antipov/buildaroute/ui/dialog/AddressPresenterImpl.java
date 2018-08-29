package com.antipov.buildaroute.ui.dialog;

import com.antipov.buildaroute.ui.base.BasePresenter;

import javax.inject.Inject;

public class AddressPresenterImpl <V extends AddressView, I extends AddressInteractor>
        extends BasePresenter<V, I> implements AddressPresenter<V, I>{

    @Inject
    public AddressPresenterImpl(I interactor) {
        super(interactor);
    }
}
