package com.antipov.buildaroute.ui.dialog;

import com.antipov.buildaroute.ui.base.BaseInteractor;
import com.antipov.buildaroute.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class AddressInteractorImpl extends BaseInteractor implements AddressInteractor {

    @Inject
    public AddressInteractorImpl(SchedulerProvider scheduler) {
        super(scheduler);
    }
}
