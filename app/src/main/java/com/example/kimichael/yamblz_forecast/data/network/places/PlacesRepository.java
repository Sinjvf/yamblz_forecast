package com.example.kimichael.yamblz_forecast.data.network.places;

import android.support.annotation.NonNull;

import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.data.network.places.response.DetailResponse;
import com.example.kimichael.yamblz_forecast.data.network.places.response.PlacesResponse;
import com.example.kimichael.yamblz_forecast.domain.interactor.requests.PlacesRequest;

import io.reactivex.Single;

/**
 * Created by Kim Michael on 16.07.17
 */
public interface PlacesRepository {

    Single<PlacesResponse> getPlaces(@NonNull PlacesRequest request);
    Single<DetailResponse> getLocale(@NonNull String id);

}
