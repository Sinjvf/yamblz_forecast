package com.example.kimichael.yamblz_forecast.data.network.forecast;


import com.example.kimichael.yamblz_forecast.data.network.forecast.response.Forecast;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Kim Michael on 16.07.17
 */
public interface OpenWeatherClient {

    String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    String CITY_LAT = "lat";
    String CITY_LON = "lon";
    String LANGUAGE = "lang";
    String RUSSIAN = "ru";
    String APPID = "APPID";

    @GET("weather")
    Single<Forecast> getForecast(@Query(CITY_LAT) String cityLat, @Query(CITY_LON) String cityLon,
                                 @Query(LANGUAGE) String language, @Query(APPID) String weatherKey);
}
