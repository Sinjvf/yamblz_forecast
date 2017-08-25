package com.example.kimichael.yamblz_forecast.presentation.di.component;

import android.support.annotation.NonNull;

import com.example.kimichael.yamblz_forecast.presentation.di.module.AppModule;
import com.example.kimichael.yamblz_forecast.presentation.di.module.ForecastModule;
import com.example.kimichael.yamblz_forecast.presentation.di.module.SchedulersModule;
import com.example.kimichael.yamblz_forecast.presentation.di.module.SettingsModule;
import com.example.kimichael.yamblz_forecast.presentation.view.MainActivity;
import com.example.kimichael.yamblz_forecast.presentation.view.forecast.ForecastFragment;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Kim Michael on 16.07.17
 */

@Singleton
@Component(modules = {AppModule.class, SchedulersModule.class})
public interface AppComponent {

    ForecastComponent plus(ForecastModule forecastModule);
    SettingsComponent plus(SettingsModule settingsModule);

    void inject(MainActivity mainActivity);
}
