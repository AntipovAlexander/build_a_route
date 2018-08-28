package com.antipov.buildaroute.di.module;

import android.app.Application;

import com.antipov.buildaroute.utils.rx.AppSchedulerProvider;
import com.antipov.buildaroute.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Module for providing App dependencies
 *
 * Created by AlexanderAntipov on 21.08.2018.
 */

@Module
public class AppModule {

    private final Application mApplication;
    private final AppSchedulerProvider mScheduleProvider;

    public AppModule(Application application, AppSchedulerProvider appSchedulerProvider) {
        this.mScheduleProvider = appSchedulerProvider;
        this.mApplication = application;
    }

    @Provides
    public SchedulerProvider provideSchedulerProvider() {
        return this.mScheduleProvider;
    }

//    @Provides
//    public TopPostsRepository provideTopPostsRepository() {
//        return new TopPostsRepositoryImpl();
//    }

//    @Provides
//    public CacheRepository provideCacheRepository() {
//        return new CacheRepositoryImpl(mApplication);
//    }
}
