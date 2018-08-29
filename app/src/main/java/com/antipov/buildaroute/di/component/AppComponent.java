package com.antipov.buildaroute.di.component;

import com.antipov.buildaroute.di.module.AppModule;
import com.antipov.buildaroute.utils.rx.SchedulerProvider;

import dagger.Component;

/**
 * Component for App inject-targets
 *
 * Created by AlexanderAntipov on 21.08.2018.
 */

@Component(modules = AppModule.class)
public interface AppComponent {

    // provide dependencies from app module to dependent components
    SchedulerProvider schedulerProvider();

//    TopPostsRepository topPostsRepository();

//    CacheRepository cacheRepository();
}

