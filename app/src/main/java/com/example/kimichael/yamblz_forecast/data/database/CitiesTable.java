package com.example.kimichael.yamblz_forecast.data.database;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.queries.Query;

/**
 * Created by Sinjvf on 06.08.2017.
 * db table for cities
 */

public class CitiesTable {

    @NonNull
    public static final String TABLE = "cities";

    @NonNull
    public static final String COLUMN_ID = "_id";
    @NonNull
    public static final String COLUMN_LAT = "lat";
    @NonNull
    public static final String COLUMN_LNG = "lng";
    @NonNull
    public static final String COLUMN_NAME = "name";

    @NonNull
    public static final Query QUERY_ALL = Query.builder()
            .table(TABLE)
            .build();

    // This is just class with Meta Data, we don't need instances
    private CitiesTable() {
        throw new IllegalStateException("No instances please");
    }

    @NonNull
    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE + "("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
                + COLUMN_LAT + " DOUBLE NOT NULL, "
                + COLUMN_LNG + " DOUBLE NOT NULL, "
                + COLUMN_NAME + " TEXT NOT NULL"
                + ");";
    }

}
