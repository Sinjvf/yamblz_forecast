package com.example.kimichael.yamblz_forecast.data.database;

import com.example.kimichael.yamblz_forecast.data.common.ForecastInfo;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;

import java.util.List;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by Sinjvf on 08.08.2017.
 * interface for db working
 */

interface DbClient {

    void saveForecast(List<ForecastInfo> forecast);
    void deleteCity(PlaceData data);
    void addCity(PlaceData data);
    void getAllCities(PublishSubject<List<PlaceData>> observer);
    void delOldForecasts(long timeInterval);
    void getActualWeather(Integer cityId, long timeInterval, PublishSubject<List<ForecastInfo>> observer);
}
