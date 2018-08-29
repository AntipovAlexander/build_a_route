package com.antipov.buildaroute.api;


import com.antipov.buildaroute.data.pojo.AutocompleteResults;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Interface with API-endpoints
 *
 * Created by AlexanderAntipov on 21.08.2018.
 */

public interface Api {

    @GET("json")
    Observable<AutocompleteResults> loadAutoComplete(@Query("address") String query);
}

