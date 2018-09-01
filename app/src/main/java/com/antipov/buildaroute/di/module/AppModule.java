package com.antipov.buildaroute.di.module;

import android.app.Application;

import com.antipov.buildaroute.data.db.DbOpenHelper;
import com.antipov.buildaroute.data.db.entities.DaoMaster;
import com.antipov.buildaroute.data.db.entities.DaoSession;
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

    private final Application application;
    private final AppSchedulerProvider scheduleProvider;
    private final DaoSession dao;

    public AppModule(Application application, AppSchedulerProvider appSchedulerProvider) {
        this.scheduleProvider = appSchedulerProvider;
        this.application = application;
        this.dao = new DaoMaster(new DbOpenHelper(application, "routes.db").getWritableDb()).newSession();
    }

    @Provides
    public SchedulerProvider provideSchedulerProvider() {
        return this.scheduleProvider;
    }


    @Provides
    public DaoSession provideDaoSession(){
        return dao;
    }
}
