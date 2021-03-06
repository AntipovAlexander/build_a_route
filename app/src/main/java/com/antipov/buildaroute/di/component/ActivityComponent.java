package com.antipov.buildaroute.di.component;


import com.antipov.buildaroute.di.module.ActivityModule;
import com.antipov.buildaroute.ui.activity.MainActivity;
import com.antipov.buildaroute.ui.dialog.AddressDialog;
import com.antipov.buildaroute.ui.fragment.history.HistoryFragment;
import com.antipov.buildaroute.ui.fragment.map.MapFragment;

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
    void inject(MapFragment mapFragment);

    void inject(AddressDialog addressDialog);

    void inject(HistoryFragment historyFragment);
}
