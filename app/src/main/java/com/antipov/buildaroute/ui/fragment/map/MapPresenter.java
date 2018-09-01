package com.antipov.buildaroute.ui.fragment.map;

import com.antipov.buildaroute.data.pojo.autocomplete.WayPoint;
import com.antipov.buildaroute.ui.base.IBasePresenter;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface MapPresenter<V extends MapView, I extends MapInteractor> extends IBasePresenter<V, I> {
    void addStartOrFinish(int request);

    void addWayPoint(int request);

    void onAddressSelected(WayPoint item, int requestCode);

    void buildRoute();

    void simulateDriving();

    void onFinishReached();

    void saveRoute(String encodedRoute);
}
