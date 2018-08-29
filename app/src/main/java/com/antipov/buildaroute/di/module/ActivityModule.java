package com.antipov.buildaroute.di.module;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.antipov.buildaroute.ui.fragment.map.MapInteractor;
import com.antipov.buildaroute.ui.fragment.map.MapInteractorImpl;
import com.antipov.buildaroute.ui.fragment.map.MapPresenter;
import com.antipov.buildaroute.ui.fragment.map.MapPresenterImpl;
import com.antipov.buildaroute.ui.fragment.map.MapView;

import dagger.Module;
import dagger.Provides;

/**
 * Module for providing Activities dependencies
 *
 * Created by AlexanderAntipov on 21.08.2018.
 */
@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }


    @Provides
    public MapPresenter<MapView, MapInteractor> provideMapPresenter(MapPresenterImpl<MapView, MapInteractor> presenter) {
        return presenter;
    }

    @Provides
    public MapInteractor provideMapInteractor(MapInteractorImpl interactor) {
        return interactor;
    }
}
