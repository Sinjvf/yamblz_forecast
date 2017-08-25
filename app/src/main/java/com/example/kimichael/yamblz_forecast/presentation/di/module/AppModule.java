package com.example.kimichael.yamblz_forecast.presentation.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.v7.preference.PreferenceManager;

import com.example.kimichael.yamblz_forecast.data.common.ForecastInfo;
import com.example.kimichael.yamblz_forecast.data.common.ForecastInfoSQLiteTypeMapping;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.data.common.PlaceDataSQLiteTypeMapping;
import com.example.kimichael.yamblz_forecast.data.database.CitiesTable;
import com.example.kimichael.yamblz_forecast.data.database.DbOpenHelper;
import com.example.kimichael.yamblz_forecast.presentation.di.scope.ForecastScope;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kim Michael on 16.07.17
 */
@Module
public class AppModule {

    private Context context;

    public AppModule(@NonNull Context appContext) {
        this.context = appContext;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return context;
    }


    @Provides
    @Singleton
    PreferencesManager providePreferensesManager(Context context) {
        return new PreferencesManager(context);
    }


    @Provides
    @Singleton
    SharedPreferences provideSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    StorIOSQLite provideStorIOSQLite(@NonNull DbOpenHelper sqLiteOpenHelper){
        return DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(sqLiteOpenHelper)
                .addTypeMapping(ForecastInfo.class, new ForecastInfoSQLiteTypeMapping())
                .addTypeMapping(PlaceData.class, new PlaceDataSQLiteTypeMapping())
                .build();
    }

    @Provides
    @Singleton
    DbOpenHelper provideDbOpenHelper(Context context){
        return new DbOpenHelper(context);
    }
}
