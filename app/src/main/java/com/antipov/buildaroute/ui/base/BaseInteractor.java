package com.antipov.buildaroute.ui.base;

import com.antipov.buildaroute.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import rx.Scheduler;

/**
 * Base class for interactors.
 *
 * Implements:
 * @see IBaseInteractor - interface for all interactors in app
 *
 * @author AlexanderAntipov
 * @since 21.08.2018.
 */
public class BaseInteractor implements IBaseInteractor {

    private SchedulerProvider mScheduler;

    /**
     * @param scheduler injecting instance of SchedulerProvider
     * @see SchedulerProvider
     */
    @Inject
    public BaseInteractor(SchedulerProvider scheduler) {
        this.mScheduler = scheduler;
    }

    /**
     * @return io-scheduler
     */
    public Scheduler io() {
        return mScheduler.io();
    }

    /**
     * @return ui-scheduler
     */
    public Scheduler ui() {
        return mScheduler.ui();
    }

    /**
     * @return computation-scheduler
     */
    public Scheduler computation() {
        return mScheduler.computation();
    }

    /**
     * @return new thread
     */
    public Scheduler newThread() {
        return mScheduler.newThread();
    }
}
