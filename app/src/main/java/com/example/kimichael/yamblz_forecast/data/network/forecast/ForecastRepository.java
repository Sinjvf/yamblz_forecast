package com.example.kimichael.yamblz_forecast.data.network.forecast;

import com.example.kimichael.yamblz_forecast.data.common.ForecastInfo;
import com.example.kimichael.yamblz_forecast.domain.interactor.requests.ForecastRequest;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Kim Michael on 16.07.17
 */
public interface ForecastRepository {

    Single<List<ForecastInfo>> updateForecast(PlaceData cityLatLng);
    Single<List<ForecastInfo>> getForecast(ForecastRequest request);
}
