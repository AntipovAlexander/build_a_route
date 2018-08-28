package com.antipov.buildaroute.di.component;


import com.antipov.buildaroute.MainActivity;
import com.antipov.buildaroute.di.module.ActivityModule;

import dagger.Component;

/**
 * Component for Activity inject-targets
 *
 * Created by AlexanderAntipov on 21.08.2018.
 */
@Component(
        dependencies = AppComponent.class, // inheriting dependencies from AppModule.class
        modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
}
