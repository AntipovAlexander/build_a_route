package com.antipov.buildaroute.ui.dialog;

import com.antipov.buildaroute.api.Api;
import com.antipov.buildaroute.api.RetrofitUtils;
import com.antipov.buildaroute.common.Const;
import com.antipov.buildaroute.data.pojo.AutocompleteResults;
import com.antipov.buildaroute.ui.base.BaseInteractor;
import com.antipov.buildaroute.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import rx.Observable;

public class AddressInteractorImpl extends BaseInteractor implements AddressInteractor {

    @Inject
    public AddressInteractorImpl(SchedulerProvider scheduler) {
        super(scheduler);
    }

    @Override
    public Observable<AutocompleteResults> loadAutoComplete(String string) {
        Observable<AutocompleteResults> call = RetrofitUtils
                .getApi()
                .create(Api.class)
                .loadAutoComplete(string);
        return call.subscribeOn(newThread())
                .observeOn(ui())
                .retry(Const.RETRY_COUNT);
    }
}
