package com.antipov.buildaroute.ui.fragment.map;

import com.antipov.buildaroute.common.Const;
import com.antipov.buildaroute.data.pojo.autocomplete.WayPoint;
import com.antipov.buildaroute.data.pojo.directions.DirectionsResults;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import rx.Observable;

@RunWith(MockitoJUnitRunner.class)
public class MapPresenterImplTest {

    @Mock
    MapView mockedMapView;

    @Mock
    MapInteractor mockedMapInteractor;

    private MapPresenterImpl<MapView, MapInteractor> presenter;

    @Before
    public void setUp() {
        presenter = new MapPresenterImpl<>(mockedMapInteractor);
        presenter.attachView(mockedMapView);
    }

    @After
    public void tearDown() {
        presenter.detachView();
    }

    @Test
    public void addStartOrFinish() {
        int request = 1;
        presenter.addStartOrFinish(request);
        Mockito.verify(mockedMapView).showAddressDialog(request);
        Mockito.verifyNoMoreInteractions(mockedMapView);
    }

    @Test
    public void addWayPointPositive() {
        int request = 1;
        int waypointsCount = 1;
        Mockito.doReturn(waypointsCount).when(mockedMapView).getWaypointsCount();
        presenter.addWayPoint(request);
        Mockito.verify(mockedMapView).getWaypointsCount();
        Mockito.verify(mockedMapView).showAddressDialog(request);
        Mockito.verifyNoMoreInteractions(mockedMapView);
    }

    @Test
    public void addWayPointNegative() {
        int request = 1;
        int waypointsCount = 15;
        Mockito.doReturn(waypointsCount).when(mockedMapView).getWaypointsCount();
        presenter.addWayPoint(request);
        Mockito.verify(mockedMapView).getWaypointsCount();
        Mockito.verify(mockedMapView).notifyWaypointsLimit();
        Mockito.verifyNoMoreInteractions(mockedMapView);
    }

    @Test
    public void onAddressSelected() {
        int start = Const.Requests.REQUEST_GET_START;
        int finish = Const.Requests.REQUEST_GET_FINISH;
        int address = Const.Requests.REQUEST_GET_ADDRESS;
        WayPoint wp = WayPoint.getForTests();

        // calling method 3 times
        presenter.onAddressSelected(wp, start);
        presenter.onAddressSelected(wp, finish);
        presenter.onAddressSelected(wp, address);

        // check that updates for start/finish was called only once
        Mockito.verify(mockedMapView, Mockito.times(1)).updateStartText(ArgumentMatchers.anyString());
        Mockito.verify(mockedMapView, Mockito.times(1)).removeOldStart();

        Mockito.verify(mockedMapView, Mockito.times(1)).updateFinishText(ArgumentMatchers.anyString());
        Mockito.verify(mockedMapView, Mockito.times(1)).removeOldFinish();

        // check adding points for three different point types
        Mockito.verify(mockedMapView).addMarker(
                wp.getGeometry().getLocation().getLat(),
                wp.getGeometry().getLocation().getLng(),
                start
        );

        Mockito.verify(mockedMapView).addMarker(
                wp.getGeometry().getLocation().getLat(),
                wp.getGeometry().getLocation().getLng(),
                finish
        );

        Mockito.verify(mockedMapView).addMarker(
                wp.getGeometry().getLocation().getLat(),
                wp.getGeometry().getLocation().getLng(),
                address
        );
        Mockito.verifyNoMoreInteractions(mockedMapView);
    }

    @Test
    public void buildRouteNullStart() {
        Mockito.doReturn(null).when(mockedMapView).getStartPoint();
        Mockito.doReturn(WayPoint.getForTests()).when(mockedMapView).getFinishPoint();

        presenter.buildRoute();
        Mockito.verify(mockedMapView).showLoadingFullscreen();
        Mockito.verify(mockedMapView).getStartPoint();
        Mockito.verify(mockedMapView).getFinishPoint();
        Mockito.verify(mockedMapView).getWaypoints();
        Mockito.verify(mockedMapView).hideLoadingFullscreen();
        Mockito.verify(mockedMapView).notifyNullStart();
        Mockito.verifyNoMoreInteractions(mockedMapView);
    }

    @Test
    public void buildRouteNullFinish() {
        Mockito.doReturn(null).when(mockedMapView).getFinishPoint();
        Mockito.doReturn(WayPoint.getForTests()).when(mockedMapView).getStartPoint();

        presenter.buildRoute();
        Mockito.verify(mockedMapView).showLoadingFullscreen();
        Mockito.verify(mockedMapView).getStartPoint();
        Mockito.verify(mockedMapView).getFinishPoint();
        Mockito.verify(mockedMapView).getWaypoints();
        Mockito.verify(mockedMapView).hideLoadingFullscreen();
        Mockito.verify(mockedMapView).notifyNullFinish();
        Mockito.verifyNoMoreInteractions(mockedMapView);
    }

    @Test
    public void buildRoutePositive() {
        Mockito.doReturn(WayPoint.getForTests()).when(mockedMapView).getFinishPoint();
        Mockito.doReturn(WayPoint.getForTests()).when(mockedMapView).getStartPoint();
        Mockito.doReturn(Collections.singletonList(WayPoint.getForTests())).when(mockedMapView).getWaypoints();
        Mockito.doReturn(Observable.just(DirectionsResults.getForTests("ok"))).when(mockedMapInteractor).calculateRoute(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
        );
        presenter.buildRoute();

        Mockito.verify(mockedMapView).showLoadingFullscreen();
        Mockito.verify(mockedMapView).getStartPoint();
        Mockito.verify(mockedMapView).getFinishPoint();
        Mockito.verify(mockedMapView).getWaypoints();
        // never called validation methods, because input is valid
        Mockito.verify(mockedMapView, Mockito.never()).notifyNullFinish();
        Mockito.verify(mockedMapView, Mockito.never()).notifyNullStart();

        Mockito.verify(mockedMapView).hideLoadingFullscreen();
        Mockito.verify(mockedMapView).removeOldPolyline();
        Mockito.verify(mockedMapView).createNewPolyline(ArgumentMatchers.anyString());
        Mockito.verify(mockedMapView).onRouteBuilt();
        Mockito.verifyNoMoreInteractions(mockedMapView);
    }

    @Test
    public void buildRouteBadStatus() {
        Mockito.doReturn(WayPoint.getForTests()).when(mockedMapView).getFinishPoint();
        Mockito.doReturn(WayPoint.getForTests()).when(mockedMapView).getStartPoint();
        Mockito.doReturn(Collections.singletonList(WayPoint.getForTests())).when(mockedMapView).getWaypoints();
        Mockito.doReturn(Observable.just(DirectionsResults.getForTests("FALSE"))).when(mockedMapInteractor).calculateRoute(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
        );
        presenter.buildRoute();

        Mockito.verify(mockedMapView).showLoadingFullscreen();
        Mockito.verify(mockedMapView).getStartPoint();
        Mockito.verify(mockedMapView).getFinishPoint();
        Mockito.verify(mockedMapView).getWaypoints();
        // never called validation methods, because input is valid
        Mockito.verify(mockedMapView, Mockito.never()).notifyNullFinish();
        Mockito.verify(mockedMapView, Mockito.never()).notifyNullStart();

        Mockito.verify(mockedMapView).hideLoadingFullscreen();
        Mockito.verify(mockedMapView).onError(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(mockedMapView);
    }

    @Test
    public void buildRouteNegative() {
        Mockito.doReturn(WayPoint.getForTests()).when(mockedMapView).getFinishPoint();
        Mockito.doReturn(WayPoint.getForTests()).when(mockedMapView).getStartPoint();
        Mockito.doReturn(Collections.singletonList(WayPoint.getForTests())).when(mockedMapView).getWaypoints();
        Mockito.doReturn(Observable.just(new Throwable())).when(mockedMapInteractor).calculateRoute(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
        );
        presenter.buildRoute();

        Mockito.verify(mockedMapView).showLoadingFullscreen();
        Mockito.verify(mockedMapView).getStartPoint();
        Mockito.verify(mockedMapView).getFinishPoint();
        Mockito.verify(mockedMapView).getWaypoints();
        // never called validation methods, because input is valid
        Mockito.verify(mockedMapView, Mockito.never()).notifyNullFinish();
        Mockito.verify(mockedMapView, Mockito.never()).notifyNullStart();

        Mockito.verify(mockedMapView).hideLoadingFullscreen();
        Mockito.verify(mockedMapView).onError(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(mockedMapView);
    }

    @Test
    public void simulateDrivingPositive() {
        Long[] ticks = new Long[10];
        for (int i = 0; i < ticks.length; i++) ticks[i] = (long) i;

        Mockito
                .doReturn(Observable.from(ticks))
                .when(mockedMapInteractor)
                .getAnimationObservable(ArgumentMatchers.anyInt());
        Mockito
                .doReturn(ticks.length - 1)
                .when(mockedMapView)
                .getRouteLength();

        presenter.simulateDriving();
        Mockito.verify(mockedMapView).lockControls();
        Mockito.verify(mockedMapView, Mockito.times(ticks.length - 1)).animateDriving(ArgumentMatchers.anyLong());
        Mockito.verify(mockedMapView, Mockito.times(ticks.length -1)).animateDriving(ArgumentMatchers.anyLong());
        Mockito.verify(mockedMapView).onFinishReached();
    }

    @Test
    public void simulateDrivingNegative() {
        Mockito
                .doReturn(Observable.just(new Throwable()))
                .when(mockedMapInteractor)
                .getAnimationObservable(ArgumentMatchers.anyInt());

        presenter.simulateDriving();
        Mockito.verify(mockedMapView).lockControls();
        Mockito.verify(mockedMapView).onError(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(mockedMapView);
    }

    @Test
    public void onFinishReached() {
        presenter.onFinishReached();
        Mockito.verify(mockedMapView).onFinishReached();
        Mockito.verify(mockedMapView).unLockControls();
        Mockito.verify(mockedMapView).removeOldPolyline();
        Mockito.verifyNoMoreInteractions(mockedMapView);
    }

    @Test
    public void saveRoutePositive() {
        String encodedRoute = "route";
        long time = 0L;
        Mockito
                .doReturn(Observable.just(100L))
                .when(mockedMapInteractor)
                .saveRoute(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong());

        presenter.saveRoute(encodedRoute, time);
        Mockito.verify(mockedMapView).updateHistory();
        Mockito.verifyNoMoreInteractions(mockedMapView);
    }

    @Test
    public void saveRouteNegative() {
        String encodedRoute = "route";
        long time = 0L;
        Mockito
                .doReturn(Observable.just(new Throwable()))
                .when(mockedMapInteractor)
                .saveRoute(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong());

        presenter.saveRoute(encodedRoute, time);
        Mockito.verify(mockedMapView).onError(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(mockedMapView);
    }
}