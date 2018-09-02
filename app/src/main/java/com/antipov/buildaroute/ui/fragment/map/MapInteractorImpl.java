package com.antipov.buildaroute.ui.fragment.map;

import com.antipov.buildaroute.api.Api;
import com.antipov.buildaroute.api.RetrofitUtils;
import com.antipov.buildaroute.common.Const;
import com.antipov.buildaroute.data.db.entities.DaoSession;
import com.antipov.buildaroute.data.db.entities.Route;
import com.antipov.buildaroute.data.pojo.directions.DirectionsResults;
import com.antipov.buildaroute.ui.base.BaseInteractor;
import com.antipov.buildaroute.utils.rx.SchedulerProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

public class MapInteractorImpl extends BaseInteractor implements MapInteractor {

    private final DaoSession dao;

    @Inject
    public MapInteractorImpl(SchedulerProvider scheduler, DaoSession daoSession) {
        super(scheduler);
        this.dao = daoSession;
    }

    @Override
    public Observable<DirectionsResults> calculateRoute(String origin, String destination, String waypoints) {
        Observable<DirectionsResults> call = RetrofitUtils
                .getApi()
                .create(Api.class)
                .calculateRoute(origin, destination, waypoints);
        return call.subscribeOn(newThread())
                .observeOn(ui())
                .retry(Const.RETRY_COUNT);
    }

    @Override
    public Observable<Long> getAnimationObservable(int animationSpeed) {
        return Observable
                .interval(animationSpeed, TimeUnit.SECONDS)
                .subscribeOn(newThread())
                .observeOn(ui());
    }

    @Override
    public Observable<Long> saveRoute(String encodedRoute, long time) {
        return Observable
                .fromCallable(() -> dao.getRouteDao()
                .insert(new Route(encodedRoute, time)))
                .subscribeOn(newThread())
                .observeOn(ui())
                .retry(Const.RETRY_COUNT);
    }
}
