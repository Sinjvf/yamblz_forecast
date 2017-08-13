package com.example.kimichael.yamblz_forecast.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceManager;

import com.example.kimichael.yamblz_forecast.R;
import com.google.android.gms.location.places.Place;
import com.google.gson.Gson;

/**
 * Created by Sinjvf on 30.07.2017.
 */

public class PreferencesManager {

    private static final String MOSCOW_NAME = "Moscow";
    private static final double MOSCOW_LAT = 55.7558;
    private static final double MOSCOW_LNG = 37.6173;
    private static final String TEMP_DEFAULT_UNITS = "Celsius";
    private static final String DEFAULT_INTERVAL = "3600";


    private static final String KEY_TEMP_UNITS = "temp_units";
    private static final String KEY_PLACE_DATA = "place_data";
    private static final String KEY_INTERVAL = "sync_interval";


    private SharedPreferences sharedPreferences;

    public PreferencesManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public PreferencesManager(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        this.sharedPreferences = sp;
    }

    public boolean isTempCelsius() {
        return sharedPreferences.getInt(KEY_TEMP_UNITS, 0) == 0;
    }

    public void saveTempUnit(int position) {
        sharedPreferences.edit().putInt(KEY_TEMP_UNITS, position).apply();
    }

    public int getTempPosition(){
        return sharedPreferences.getInt(KEY_TEMP_UNITS, 0);
    }

    public boolean containInterval() {
        return sharedPreferences.contains(KEY_INTERVAL);
    }

    public void saveInterval(String interval) {
        sharedPreferences.edit().putString(KEY_INTERVAL, interval).apply();
    }

    public void saveInterval() {
        sharedPreferences.edit().putString(KEY_INTERVAL, DEFAULT_INTERVAL).apply();
    }

    public boolean isIntervalChanged(String key) {
        return key.equals(KEY_INTERVAL);
    }

    public String getInterval() {
        return sharedPreferences.getString(KEY_INTERVAL, DEFAULT_INTERVAL);
    }

    public void savePlace(@Nullable Place place) {
        PlaceData data;
        if (place != null && place.getName() != null && place.getLatLng() != null) {
            data = new PlaceData(place.getName().toString(), place.getLatLng().latitude, place.getLatLng().longitude);
        } else {
            data = new PlaceData(MOSCOW_NAME, MOSCOW_LAT, MOSCOW_LNG);
        }
        String dataStr = (new Gson()).toJson(data);
        sharedPreferences.edit().putString(KEY_PLACE_DATA, dataStr).apply();

    }

    @NonNull
    public PlaceData getPlace() {
        PlaceData defaultData = new PlaceData(MOSCOW_NAME, MOSCOW_LAT, MOSCOW_LNG);
        String defaultStr = (new Gson()).toJson(defaultData);
        String dataStr = sharedPreferences.getString(KEY_PLACE_DATA, defaultStr);
        return (new Gson()).fromJson(dataStr, PlaceData.class);
    }

    public String getUnit() {
        return isTempCelsius() ? "C" : "F";
    }

}
