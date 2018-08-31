package com.antipov.buildaroute.ui.fragment.map;

import com.antipov.buildaroute.data.pojo.autocomplete.WayPoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

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
        presenter.onAddressSelected(WayPoint.getForTests(), requestCode);
        Mockito.verify(mockedMapView).addMarker(ArgumentMatchers.anyFloat(), ArgumentMatchers.anyFloat(), requestCode);
        Mockito.verifyNoMoreInteractions(mockedMapView);
    }
}