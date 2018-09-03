package com.antipov.buildaroute.ui.fragment.history;

import com.antipov.buildaroute.data.db.entities.Route;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;

@RunWith(MockitoJUnitRunner.class)
public class HistoryPresenterImplTest {

    @Mock
    HistoryView mockedView;

    @Mock
    HistoryInteractor mockedInteractor;

    HistoryPresenter<HistoryView, HistoryInteractor> presenter;

    @Before
    public void setUp() {
        presenter = new HistoryPresenterImpl<>(mockedInteractor);
        presenter.attachView(mockedView);
    }

    @After
    public void tearDown() {
        presenter.detachView();
    }

    @Test
    public void loadHistory() {
        Route route = new Route();
        List<Route> routes = Collections.singletonList(route);


        Mockito
                .doReturn(Observable.just(routes))
                .when(mockedInteractor)
                .loadHistory();

        presenter.loadHistory();

        Mockito.verify(mockedView).showLoadingFullscreen();
        Mockito.verify(mockedView).hideLoadingFullscreen();
        Mockito.verify(mockedView).renderList(ArgumentMatchers.anyList());
        Mockito.verifyNoMoreInteractions(mockedView);
    }

    @Test
    public void loadHistoryZeroElements() {
        List<Route> routes = new ArrayList<>();


        Mockito
                .doReturn(Observable.just(routes))
                .when(mockedInteractor)
                .loadHistory();

        presenter.loadHistory();

        Mockito.verify(mockedView).showLoadingFullscreen();
        Mockito.verify(mockedView).hideLoadingFullscreen();
        Mockito.verify(mockedView).onNoHistoryFound();
        Mockito.verifyNoMoreInteractions(mockedView);
    }

    @Test
    public void loadHistoryOnError() {
        Mockito
                .doReturn(Observable.just(new Throwable()))
                .when(mockedInteractor)
                .loadHistory();

        presenter.loadHistory();

        Mockito.verify(mockedView).showLoadingFullscreen();
        Mockito.verify(mockedView).hideLoadingFullscreen();
        Mockito.verify(mockedView).onError(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(mockedView);
    }
}