package com.example.kimichael.yamblz_forecast.presentation.di.module;

import com.example.kimichael.yamblz_forecast.domain.interactor.forecast.ForecastInteractor;
import com.example.kimichael.yamblz_forecast.domain.interactor.settings.SettingsInteractor;
import com.example.kimichael.yamblz_forecast.presentation.di.scope.ForecastScreenScope;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.ForecastPresenter;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.PhoneWeatherPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kim Michael on 16.07.17
 */

@Module
public class ForecastScreenModule {

    @Provides
    //unscoped! we want have many providers - for each weather screen
    ForecastPresenter provideForecastPresenter(ForecastInteractor forecastInteractor) {
        return new ForecastPresenter(forecastInteractor);
    }


}
