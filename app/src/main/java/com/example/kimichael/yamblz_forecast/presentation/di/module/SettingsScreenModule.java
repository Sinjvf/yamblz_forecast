package com.example.kimichael.yamblz_forecast.presentation.di.module;

import com.example.kimichael.yamblz_forecast.domain.interactor.forecast.ForecastInteractor;
import com.example.kimichael.yamblz_forecast.domain.interactor.settings.SettingsInteractor;
import com.example.kimichael.yamblz_forecast.presentation.di.scope.ForecastScreenScope;
import com.example.kimichael.yamblz_forecast.presentation.di.scope.SettingsScreenScope;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.PhoneWeatherPresenter;
import com.example.kimichael.yamblz_forecast.presentation.presenter.settings.SettingsIntervalDialogPresenter;
import com.example.kimichael.yamblz_forecast.presentation.presenter.settings.SettingsPresenter;
import com.example.kimichael.yamblz_forecast.presentation.presenter.settings.SettingsUnitDialogPresenter;
import com.example.kimichael.yamblz_forecast.presentation.presenter.settings.SuggestsPresenter;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created on 22.07.2017.
 */
@Module
public class SettingsScreenModule {

    @Provides
    @SettingsScreenScope
    SettingsPresenter provideSettingsPresenter(SettingsInteractor settingsInteractor) {
        return new SettingsPresenter(settingsInteractor);
    }

    @Provides
    @SettingsScreenScope
    SuggestsPresenter provideSuggestsPresenter(SettingsInteractor settingsInteractor) {
        return new SuggestsPresenter(settingsInteractor);
    }

    @Provides
    @SettingsScreenScope
    SettingsIntervalDialogPresenter provideSettingsIntervalPresenter(PreferencesManager manager) {
        return new SettingsIntervalDialogPresenter(manager);
    }

    @Provides
    @SettingsScreenScope
    SettingsUnitDialogPresenter provideSettingsUnitPresenter(PreferencesManager manager) {
        return new SettingsUnitDialogPresenter(manager);
    }

    @Provides
    @SettingsScreenScope
    PhoneWeatherPresenter providePhoneWeatherPresenter(SettingsInteractor forecastInteractor, @Named(SchedulersModule.UI) Scheduler postExecutionThread) {
        return new PhoneWeatherPresenter(forecastInteractor, postExecutionThread);
    }
}
