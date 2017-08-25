package com.example.kimichael.yamblz_forecast.data.network.places;

import com.example.kimichael.yamblz_forecast.data.network.places.response.DetailResponse;
import com.example.kimichael.yamblz_forecast.data.network.places.response.PlacesResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Sinjvf on 02.08.2017.
 * client for google api
 */

public interface GooglePlacesClient {

    String BASE_URL = "https://maps.googleapis.com/maps/api/place/";
    String SUGGESTS = "autocomplete/json?";
    String PLACE_DETAIL = "details/json?";

    String INPUT = "input";
    String TYPE = "type";
    String TYPE_CITY = "(cities)";
    String ID = "placeid";
    String LANGUAGE = "language";

    @GET(SUGGESTS)
    Single<PlacesResponse> getPlaces(@Query(INPUT) String location, @Query(TYPE) String type);

    @GET(PLACE_DETAIL)
    Single<DetailResponse> getPlaceDetail(@Query(ID) String id, @Query(LANGUAGE) String language);
}
