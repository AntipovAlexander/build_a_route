package com.antipov.buildaroute.ui.dialog;

import com.antipov.buildaroute.data.pojo.autocomplete.AutocompleteResults;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;

@RunWith(MockitoJUnitRunner.class)
public class AddressPresenterImplTest {

    @Mock
    AddressView mockedView;

    @Mock
    AddressInteractor mockedInteractor;

    private AddressPresenter<AddressView, AddressInteractor> presenter;

    @Before
    public void setUp() {
        presenter = new AddressPresenterImpl<>(mockedInteractor);
        presenter.attachView(mockedView);
    }

    @After
    public void tearDown() {
        presenter.detachView();
    }

    @Test
    public void loadAutoCompletePositive() {
        Observable<String> observableText = Observable.just("abcdef");
        rx.Observable<AutocompleteResults> observableAutocomplete = rx.Observable.just(AutocompleteResults.getForTests(false));
        Mockito.doReturn(observableAutocomplete).when(mockedInteractor).loadAutoComplete(ArgumentMatchers.anyString());
        presenter.subscribeAutoComplete(observableText);
        Mockito.verify(mockedView).showLoading();
        Mockito.verify(mockedInteractor).loadAutoComplete(ArgumentMatchers.anyString());
        Mockito.verify(mockedView).hideLoading();
        Mockito.verify(mockedView).setAutocomplete(ArgumentMatchers.anyList());
        Mockito.verifyNoMoreInteractions(mockedInteractor);
        Mockito.verifyNoMoreInteractions(mockedView);
    }

    @Test
    public void loadAutoCompleteZeroResults() {
        Observable<String> observableText = Observable.just("abcdef");
        rx.Observable<AutocompleteResults> observableAutocomplete = rx.Observable.just(AutocompleteResults.getForTests(true));
        Mockito.doReturn(observableAutocomplete).when(mockedInteractor).loadAutoComplete(ArgumentMatchers.anyString());
        presenter.subscribeAutoComplete(observableText);
        Mockito.verify(mockedView).showLoading();
        Mockito.verify(mockedInteractor).loadAutoComplete(ArgumentMatchers.anyString());
        Mockito.verify(mockedView).hideLoading();
        Mockito.verify(mockedView).notifyAboutNoResults();
        Mockito.verifyNoMoreInteractions(mockedInteractor);
        Mockito.verifyNoMoreInteractions(mockedView);
    }

    @Test
    public void loadAutoCompleteNegative() {
        Observable<String> observableText = Observable.just("abcdef");
        rx.Observable<Throwable> observableAutocomplete = rx.Observable.just(new Throwable());
        Mockito.doReturn(observableAutocomplete).when(mockedInteractor).loadAutoComplete(ArgumentMatchers.anyString());
        presenter.subscribeAutoComplete(observableText);
        Mockito.verify(mockedView).showLoading();
        Mockito.verify(mockedInteractor).loadAutoComplete(ArgumentMatchers.anyString());
        Mockito.verify(mockedView).hideLoading();
        Mockito.verify(mockedView).onError(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(mockedInteractor);
        Mockito.verifyNoMoreInteractions(mockedView);
    }
}