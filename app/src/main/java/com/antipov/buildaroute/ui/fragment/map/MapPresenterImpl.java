package com.antipov.buildaroute.ui.fragment.map;

import com.antipov.buildaroute.data.pojo.WayPoint;
import com.antipov.buildaroute.ui.base.BasePresenter;

import javax.inject.Inject;

public class MapPresenterImpl <V extends MapView, I extends MapInteractor> extends BasePresenter<V, I>
        implements MapPresenter<V, I> {

    private final int WAYPOINTS_LIMIT = 5;

    @Inject
    public MapPresenterImpl(I interactor) {
        super(interactor);
    }

    @Override
    public void addStartOrFinish(int request) {
        if (isViewNotAttached()) return;
        getView().showAddressDialog(request);
    }

    @Override
    public void addWayPoint(int request) {
        if (getView().getWaypointsCount() < WAYPOINTS_LIMIT) {
            getView().showAddressDialog(request);
        } else {
            getView().notifyWaypointsLimit();
        }
    }

    @Override
    public void onAddressSelected(WayPoint item) {
        if (isViewNotAttached()) return;
        getView().addMarker(item.getGeometry().getLocation().getLat(), item.getGeometry().getLocation().getLng());
    }
}
