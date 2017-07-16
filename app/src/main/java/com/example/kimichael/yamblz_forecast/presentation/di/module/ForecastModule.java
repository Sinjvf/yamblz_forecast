package com.example.kimichael.yamblz_forecast.presentation.di.module;

import android.content.Context;
import android.support.v7.preference.PreferenceManager;

import com.example.kimichael.yamblz_forecast.BuildConfig;
import com.example.kimichael.yamblz_forecast.data.ForecastRepository;
import com.example.kimichael.yamblz_forecast.data.ForecastRespositoryImpl;
import com.example.kimichael.yamblz_forecast.data.network.forecast.OpenWeatherClient;
import com.example.kimichael.yamblz_forecast.presentation.di.scope.ForecastScope;
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
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url().newBuilder().addQueryParameter("APPID", BuildConfig.OPEN_WEATHER_MAP_API_KEY).build();
                        request = request.newBuilder().url(url).build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(interceptor)
                .build();
        return client;
    }


    @Provides
    @ForecastScope
    @Named("Gson")
    Gson provideGson() {
        Gson gson = new GsonBuilder()
                .create();
        return gson;
    }

    @Provides
    @ForecastScope
    ForecastRepository provideForecastRepository(Context context, OpenWeatherClient openWeatherClient,
                                                 @Named("Gson") Gson gson) {
        return new ForecastRespositoryImpl(PreferenceManager.getDefaultSharedPreferences(context),
                openWeatherClient, gson);
    }

}