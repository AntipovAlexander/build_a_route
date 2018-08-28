package com.antipov.buildaroute.app;

import android.app.Application;

import com.antipov.buildaroute.di.component.AppComponent;
import com.antipov.buildaroute.di.component.DaggerAppComponent;
import com.antipov.buildaroute.di.module.AppModule;
import com.antipov.buildaroute.utils.rx.AppSchedulerProvider;


public class App extends Application implements com.antipov.buildaroute.app.Application {

    public AppComponent component;


    @Override
    public AppComponent getComponent() {
        if (component == null) {
            component = DaggerAppComponent
                    .builder()
                    .appModule(
                            new AppModule(
                                    this,
                                    new AppSchedulerProvider()
                            )
                    ).build();
        }
        return component;
    }

    public void setComponent(AppComponent component) {
        this.component = component;
    }
}
