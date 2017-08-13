package com.example.kimichael.yamblz_forecast.domain.interactor.forecast;

import com.example.kimichael.yamblz_forecast.utils.PlaceData;

/**
 * Created by Kim Michael on 18.07.17
 */
public class ForecastRequest {

    private final PlaceData cityData;
    private final boolean forceUpdate;



    public ForecastRequest(PlaceData cityLatLng, boolean forceUpdate) {
        this.cityData = cityLatLng;
        this.forceUpdate = forceUpdate;
    }


    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public String getCityLat() {
        return String.valueOf(cityData.getLatitude());
    }

    public String getCityLon() {
        return String.valueOf(cityData.getLongitude());
    }
}
