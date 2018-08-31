package com.antipov.buildaroute.ui.dialog;

import com.antipov.buildaroute.data.pojo.autocomplete.AutocompleteResults;
import com.antipov.buildaroute.ui.base.IBaseInteractor;

import rx.Observable;

public interface AddressInteractor extends IBaseInteractor {
    Observable<AutocompleteResults> loadAutoComplete(String string);
}
