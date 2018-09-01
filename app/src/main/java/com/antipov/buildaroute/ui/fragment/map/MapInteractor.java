package com.antipov.buildaroute.ui.fragment.map;

import com.antipov.buildaroute.data.pojo.directions.DirectionsResults;
import com.antipov.buildaroute.ui.base.IBaseInteractor;

import rx.Observable;

public interface MapInteractor extends IBaseInteractor {
    Observable<DirectionsResults> calculateRoute(String origin, String destination, String waypoints);

    Observable<Long> getAnimationObservable(int animationSpeed);

    Observable<Long> saveRoute(String encodedRoute);
}
