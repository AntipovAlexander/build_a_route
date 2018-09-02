package com.antipov.buildaroute.ui.dialog;

import android.annotation.SuppressLint;

import com.antipov.buildaroute.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class AddressPresenterImpl <V extends AddressView, I extends AddressInteractor>
        extends BasePresenter<V, I> implements AddressPresenter<V, I>{

    private Disposable disposable;

    @Inject
    public AddressPresenterImpl(I interactor) {
        super(interactor);
    }

    @SuppressLint("CheckResult")
    @Override
    public void subscribeAutoComplete(Observable<String> observable) {
        if (isViewNotAttached()) return;
        disposable = observable.subscribe(
                string -> {
                    if (isViewNotAttached()) return;
                    getView().showLoading();
                    getInteractor()
                            .loadAutoComplete(string)
                            .subscribe(
                                    result -> {
                                        if (isViewNotAttached()) return;
                                        getView().hideLoading();
                                        if (result.getResults().size() < 1) {
                                            getView().notifyAboutNoResults();
                                            return;
                                        }
                                        getView().setAutocomplete(result.getResults());

                                    },

                                    this::onError);
                },
                this::onError
        );
    }

    @Override
    public void unSubscribeAutoComplete() {
        disposable.dispose();
    }

    private void onError(Throwable t) {
        if (isViewNotAttached()) return;
        getView().hideLoading();
        getView().onError(t.getMessage());
    }
}
