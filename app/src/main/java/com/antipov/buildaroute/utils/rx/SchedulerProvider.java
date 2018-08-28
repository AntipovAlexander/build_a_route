package com.antipov.buildaroute.utils.rx;


import rx.Scheduler;

/**
 * Created by AlexanderAntipov on 04.06.2018.
 */

public interface SchedulerProvider {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();

    Scheduler newThread();

}
