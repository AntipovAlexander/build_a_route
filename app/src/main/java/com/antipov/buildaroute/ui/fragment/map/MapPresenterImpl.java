package com.antipov.buildaroute.ui.fragment.map;

import com.antipov.buildaroute.data.pojo.autocomplete.WayPoint;
import com.antipov.buildaroute.ui.base.BasePresenter;
import com.antipov.buildaroute.utils.converter.ArgsConverter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

import static com.antipov.buildaroute.common.Const.Requests.REQUEST_GET_FINISH;
import static com.antipov.buildaroute.common.Const.Requests.REQUEST_GET_START;

public class MapPresenterImpl <V extends MapView, I extends MapInteractor> extends BasePresenter<V, I>
        implements MapPresenter<V, I> {

    private final int WAYPOINTS_LIMIT = 5;
    private final int ANIMATION_SPEED = 1;
    private Subscription subs;

    @Inject
    public MapPresenterImpl(I interactor) {
        super(interactor);
    }

    /**
     * adding start\finish point
     *
     * @param request request code
     */
    @Override
    public void addStartOrFinish(int request) {
        if (isViewNotAttached()) return;
        getView().showAddressDialog(request);
    }

    /**
     * adding point to the route
     *
     * @param request request code
     */
    @Override
    public void addWayPoint(int request) {
        if (getView().getWaypointsCount() < WAYPOINTS_LIMIT) {
            getView().showAddressDialog(request);
        } else {
            getView().notifyWaypointsLimit();
        }
    }

    /**
     * when address picked in dialog
     *
     * @param item - picked address
     * @param requestCode - request code
     */
    @Override
    public void onAddressSelected(WayPoint item, int requestCode) {
        if (isViewNotAttached()) return;

        // updating views according to request code
        switch (requestCode) {
            case REQUEST_GET_START:
                getView().updateStartText(item.getFormattedAddress());
                getView().removeOldStart();
                break;
            case REQUEST_GET_FINISH:
                getView().updateFinishText(item.getFormattedAddress());
                getView().removeOldFinish();
                break;
        }

        // place marker in the map
        getView().addMarker(
                item.getGeometry().getLocation().getLat(),
                item.getGeometry().getLocation().getLng(),
                requestCode
        );
    }

    /**
     * build route
     */
    @Override
    public void buildRoute() {
        if (isViewNotAttached()) return;

        getView().showLoadingFullscreen();

        // get way points
        WayPoint start = getView().getStartPoint();
        WayPoint finish = getView().getFinishPoint();
        List<WayPoint> wayPoints = getView().getWaypoints();

        // start can't be null
        if (start == null) {
            getView().hideLoadingFullscreen();
            getView().notifyNullStart();
            return;
        }

        // finish can't be null
        if (finish == null) {
            getView().hideLoadingFullscreen();
            getView().notifyNullFinish();
            return;
        }

        getInteractor().calculateRoute(
                ArgsConverter.convert(start.getGeometry().getLocation()),
                ArgsConverter.convert(finish.getGeometry().getLocation()),
                ArgsConverter.convertToWaypoint(wayPoints)
        ).subscribe(
                directionsResults -> {
                    if (isViewNotAttached()) return;
                    getView().hideLoadingFullscreen();
                    if (!directionsResults.isSuccess()) {
                        // in case when status isn't OK
                        getView().onError(directionsResults.getStatus());
                    } else {
                        // in case of success
                        getView().removeOldPolyline();
                        getView().createNewPolyline(directionsResults.toString());
                    }
                },
                throwable -> {
                    if (isViewNotAttached()) return;
                    getView().hideLoadingFullscreen();
                    getView().onError(throwable.getMessage());
                }
        );

    }

    /**
     * method for simulating route вкшмштп
     */
    @Override
    public void simulateDriving() {
        if (isViewNotAttached()) return;

        if (subs != null) {
            // restart route if was called when route simulating already
            subs.unsubscribe();
        }

        subs = getInteractor()
                .getAnimationObservable(ANIMATION_SPEED)
                .subscribe(tick -> {
                        if (isViewNotAttached()) return;
                        // check if route finished or not
                        if (tick < getView().getRouteLength()) {
                            getView().animateDriving(tick);
                        } else {
                            onFinishReached();
                        }
                    },
                        throwable -> {
                            if (isViewNotAttached()) return;
                            getView().onError(throwable.getMessage());
                        });
    }

    @Override
    public void onFinishReached() {
        if (subs != null && !subs.isUnsubscribed()) {
            subs.unsubscribe();
        }
        if (isViewNotAttached()) return;
        getView().onFinishReached();
    }

    @Override
    public void saveRoute(String encodedRoute) {
        getInteractor().saveRoute(encodedRoute).subscribe();
    }
}
