package com.example.kimichael.yamblz_forecast.presentation.di.component;

import com.example.kimichael.yamblz_forecast.presentation.di.module.ForecastScreenModule;
import com.example.kimichael.yamblz_forecast.presentation.di.scope.ForecastScreenScope;
import com.example.kimichael.yamblz_forecast.presentation.view.forecast.ForecastFragment;

import dagger.Subcomponent;

/**
 * Created by Kim Michael on 16.07.17
 */

@ForecastScreenScope
@Subcomponent(modules = {ForecastScreenModule.class})
public interface ForecastScreenComponent {

    void inject(ForecastFragment forecastFragment);
}
