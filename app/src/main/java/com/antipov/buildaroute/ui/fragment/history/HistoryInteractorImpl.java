package com.antipov.buildaroute.ui.fragment.history;

import com.antipov.buildaroute.ui.base.BaseInteractor;
import com.antipov.buildaroute.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class HistoryInteractorImpl extends BaseInteractor implements HistoryInteractor {

    @Inject
    public HistoryInteractorImpl(SchedulerProvider scheduler) {
        super(scheduler);
    }
}
