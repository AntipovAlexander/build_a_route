package com.antipov.buildaroute.ui.fragment.map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    public void onMapButtonClick() {
        presenter.onMapButtonClick();
        Mockito.verify(mockedMapView).showAddressDialog(request);
        Mockito.verifyNoMoreInteractions(mockedMapView);
    }
}