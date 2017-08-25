package com.example.kimichael.yamblz_forecast.data.network.places;

import android.support.annotation.NonNull;

import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.data.network.places.response.DetailResponse;
import com.example.kimichael.yamblz_forecast.data.network.places.response.PlacesResponse;
import com.example.kimichael.yamblz_forecast.domain.interactor.requests.PlacesRequest;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;

/**
 * Created by Kim Michael on 16.07.17
 */
public class PlacesRepositoryImpl implements PlacesRepository {
    private GooglePlacesClient googlePlacesClient;

    @Inject
    public PlacesRepositoryImpl(GooglePlacesClient googlePlacesClient) {
        this.googlePlacesClient = googlePlacesClient;
    }

    @Override
    public Single<PlacesResponse> getPlaces(@NonNull PlacesRequest request) {
        return googlePlacesClient.getPlaces(request.getQueue(), GooglePlacesClient.TYPE_CITY);
    }

    @Override
    public Single<DetailResponse> getLocale(@NonNull String id) {
        return googlePlacesClient.getPlaceDetail(id, Locale.getDefault().getLanguage().toLowerCase());
    }

}
