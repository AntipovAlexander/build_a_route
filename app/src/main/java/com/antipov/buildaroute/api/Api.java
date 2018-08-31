package com.antipov.buildaroute.api;


import com.antipov.buildaroute.data.pojo.autocomplete.AutocompleteResults;
import com.antipov.buildaroute.data.pojo.directions.DirectionsResults;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Interface with API-endpoints
 *
 * Created by AlexanderAntipov on 21.08.2018.
 */

public interface Api {

    @GET("geocode/json")
    Observable<AutocompleteResults> loadAutoComplete(@Query("address") String query);

    @GET("directions/json")
    Observable<DirectionsResults> calculateRoute(
            @Query("origin") String origin,
            @Query("destination") String destination,
            @Query("waypoints") String waypoints
    );
}

