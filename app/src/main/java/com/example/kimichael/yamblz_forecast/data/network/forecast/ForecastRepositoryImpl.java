package com.example.kimichael.yamblz_forecast.data.network.forecast;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.kimichael.yamblz_forecast.data.database.CitiesTable;
import com.example.kimichael.yamblz_forecast.data.network.forecast.response.ForecastResponse;
import com.example.kimichael.yamblz_forecast.data.network.forecast.response.WeatherResponse;
import com.example.kimichael.yamblz_forecast.data.common.ForecastInfo;
import com.example.kimichael.yamblz_forecast.domain.interactor.requests.ForecastRequest;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.google.gson.Gson;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Single;
import timber.log.Timber;

/**
 * Created by Kim Michael on 16.07.17
 */
public class ForecastRepositoryImpl implements ForecastRepository {

    private static final String PREF_LAST_RESPONSE = "pref_last_response";

    private OpenWeatherClient openWeatherClient;
    private Gson gson;

    @Inject
    public ForecastRepositoryImpl(OpenWeatherClient openWeatherClient,
                                  Gson gson) {
        this.openWeatherClient = openWeatherClient;
        this.gson = gson;
    }

    @Override
    public Single<List<ForecastInfo>> updateForecast(PlaceData cityLatLng) {
        String lat = String.valueOf(cityLatLng.getLatitude());
        String lng = String.valueOf(cityLatLng.getLongitude());
        return openWeatherClient.getForecast(lat, lng,
                Locale.getDefault().getLanguage().toLowerCase())
                .map(ForecastResponse::getList)
                .map(list -> {
                    List<ForecastInfo> resultList = new ArrayList<>();
                    for (WeatherResponse item : list) {
                        resultList.add(ForecastInfo.from(item));
                    }
                    return resultList;
                });
    }

    @Override
    public Single<List<ForecastInfo>> getForecast(ForecastRequest request) {
        return openWeatherClient.getForecast(request.getCityLat(), request.getCityLon(),
                Locale.getDefault().getLanguage().toLowerCase())
                .map(ForecastResponse::getList)
                .map(list -> {
                    List<ForecastInfo> resultList = new ArrayList<>();
                    for (WeatherResponse item : list) {
                        resultList.add(ForecastInfo.from(item));
                    }
                    return resultList;
                });
    }

}
