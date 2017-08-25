package com.example.kimichael.yamblz_forecast.data.database;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.queries.Query;

/**
 * Created by Sinjvf on 06.08.2017.
 * db table for forecasts
 */

public class ForecastsTable {

    @NonNull
    public static final String TABLE = "forecasts";
    @NonNull
    public static final String COLUMN_ID = "_id";
    @NonNull
    public static final String COLUMN_CITY_ID = "city_id";
    @NonNull
    public static final String COLUMN_TEMPR = "tempr";
    @NonNull
    public static final String COLUMN_HUMIDITY = "humidity";
    @NonNull
    public static final String COLUMN_DATE = "date";
    @NonNull
    public static final String COLUMN_WEATHER_ID = "weather_ID";
    @NonNull
    public static final String COLUMN_WIND = "wind";
    @NonNull
    public static final String COLUMN_PRESSURE = "pressure";
    @NonNull
    public static final String COLUMN_DESCRIPTION = "description";
    @NonNull
    public static final String COLUMN_GET_DATE = "getDate";


    @NonNull
    public static final Query QUERY_ALL = Query.builder()
            .table(TABLE)
            .build();

    // This is just class with Meta Data, we don't need instances
    private ForecastsTable() {
        throw new IllegalStateException("No instances please");
    }

    @NonNull
    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE + "("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
                + COLUMN_CITY_ID + " INTEGER NOT NULL, "
                + COLUMN_TEMPR + " DOUBLE NOT NULL, "
                + COLUMN_HUMIDITY + " DOUBLE NOT NULL, "
                + COLUMN_WEATHER_ID + " INTEGER NOT NULL, "
                + COLUMN_WIND + " DOUBLE NOT NULL, "
                + COLUMN_PRESSURE + " DOUBLE NOT NULL, "
                + COLUMN_DESCRIPTION + " TEXT NOT NULL, "
                + COLUMN_DATE + " LONG NULLABLE, "
                + COLUMN_GET_DATE + " LONG NOT NULL "
                + ");";
    }

}
