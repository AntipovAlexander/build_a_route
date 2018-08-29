package com.antipov.buildaroute.ui.fragment.map;

import com.antipov.buildaroute.ui.base.BaseInteractor;
import com.antipov.buildaroute.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class MapInteractorImpl extends BaseInteractor implements MapInteractor {

    @Inject
    public MapInteractorImpl(SchedulerProvider scheduler) {
        super(scheduler);
    }
}
