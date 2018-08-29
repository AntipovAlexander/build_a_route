package com.antipov.buildaroute.ui.dialog;

import com.antipov.buildaroute.ui.base.BasePresenter;

import javax.inject.Inject;

public class AddressPresenterImpl <V extends AddressView, I extends AddressInteractor>
        extends BasePresenter<V, I> implements AddressPresenter<V, I>{

    @Inject
    public AddressPresenterImpl(I interactor) {
        super(interactor);
    }

    @Override
    public void loadAutoComplete(String string) {
        if (isViewNotAttached()) return;
        getView().showLoading();
        getInteractor()
                .loadAutoComplete(string)
                .subscribe(
                        result -> {
                            if (isViewNotAttached()) return;
                            getView().hideLoading();
                            getView().setAutocomplete(result.getResults());

                        },
                        throwable -> {
                            if (isViewNotAttached()) return;
                            getView().hideLoading();
                        });
    }
}
