package com.example.kimichael.yamblz_forecast.presentation.di.component;

import com.example.kimichael.yamblz_forecast.presentation.di.module.SettingsScreenModule;
import com.example.kimichael.yamblz_forecast.presentation.di.scope.SettingsScreenScope;
import com.example.kimichael.yamblz_forecast.presentation.view.settings.SettingsFragment;

import dagger.Subcomponent;

/**
 * Created on 22.07.2017.
 */
@SettingsScreenScope
@Subcomponent(modules = {SettingsScreenModule.class})
public interface SettingsScreenComponent {

    void inject(SettingsFragment settingsFragment);
}
