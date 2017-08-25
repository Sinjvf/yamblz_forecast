package com.example.kimichael.yamblz_forecast.presentation.di.module;

import android.content.Context;
import android.support.v7.preference.PreferenceManager;

import com.example.kimichael.yamblz_forecast.BuildConfig;
import com.example.kimichael.yamblz_forecast.data.network.forecast.ForecastRepository;
import com.example.kimichael.yamblz_forecast.data.network.forecast.ForecastRepositoryImpl;
import com.example.kimichael.yamblz_forecast.data.network.forecast.OpenWeatherClient;
import com.example.kimichael.yamblz_forecast.data.network.places.GooglePlacesClient;
import com.example.kimichael.yamblz_forecast.data.network.places.PlacesRepository;
import com.example.kimichael.yamblz_forecast.data.network.places.PlacesRepositoryImpl;
import com.example.kimichael.yamblz_forecast.presentation.di.scope.ForecastScope;
import com.example.kimichael.yamblz_forecast.presentation.di.scope.SettingsScope;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sinjvf on 03.08.17
 */

@Module
public class SettingsModule {

    @Provides
    @SettingsScope
    GooglePlacesClient provideGooglePlacesClient(@Named("GsonGoogle") Gson gson,
                                                 @Named("GoogleOkHttpClient") OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(GooglePlacesClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .client(client)
                .build().create(GooglePlacesClient.class);
    }

    @Provides
    @SettingsScope
    @Named("GoogleOkHttpClient")
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    HttpUrl url = request.url().newBuilder()
                            .addQueryParameter("key", BuildConfig.GOOGLE_API_KEY)
                            .build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                })
                .addInterceptor(interceptor)
                .build();
    }


    @Provides
    @SettingsScope
    @Named("GsonGoogle")
    Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

    @Provides
    @SettingsScope
    PlacesRepository providePlacesRepository(GooglePlacesClient placesClient) {
        return new PlacesRepositoryImpl(placesClient);
    }


}
