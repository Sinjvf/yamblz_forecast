package com.example.kimichael.yamblz_forecast.presentation.di.module;

import android.content.Context;
import android.support.v7.preference.PreferenceManager;

import com.example.kimichael.yamblz_forecast.BuildConfig;
import com.example.kimichael.yamblz_forecast.data.ForecastRepository;
import com.example.kimichael.yamblz_forecast.data.ForecastRepositoryImpl;
import com.example.kimichael.yamblz_forecast.data.network.forecast.OpenWeatherClient;
import com.example.kimichael.yamblz_forecast.presentation.di.scope.ForecastScope;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.Interceptor;
import retrofit2.Retrofit;
import okhttp3.Request;
import okhttp3.OkHttpClient;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kim Michael on 16.07.17
 */

@Module
public class ForecastModule {

    @Provides
    @ForecastScope
    OpenWeatherClient provideOpenWeatherClient(@Named("Gson") Gson gson, @Named("ForecastOkHttpClient") OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(OpenWeatherClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .client(client)
                .build().create(OpenWeatherClient.class);
    }

    @Provides
    @ForecastScope
    @Named("ForecastOkHttpClient")
    OkHttpClient provideForecastOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }


    @Provides
    @ForecastScope
    @Named("Gson")
    Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

    @Provides
    @ForecastScope
    ForecastRepository provideForecastRepository(Context context, OpenWeatherClient openWeatherClient,
                                                 @Named("Gson") Gson gson) {
        return new ForecastRepositoryImpl(PreferenceManager.getDefaultSharedPreferences(context),
                openWeatherClient, gson);
    }

    @Provides
    @ForecastScope
    PreferencesManager providePreferensesManager(Context context) {
        return new PreferencesManager(context);
    }

}
