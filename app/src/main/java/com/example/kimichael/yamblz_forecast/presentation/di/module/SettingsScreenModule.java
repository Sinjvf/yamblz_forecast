package com.example.kimichael.yamblz_forecast.presentation.di.module;

import com.example.kimichael.yamblz_forecast.domain.interactor.settings.SettingsInteractor;
import com.example.kimichael.yamblz_forecast.presentation.di.scope.SettingsScreenScope;
import com.example.kimichael.yamblz_forecast.presentation.presenter.settings.SettingsPresenter;

import dagger.Module;
import dagger.Provides;

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
}
