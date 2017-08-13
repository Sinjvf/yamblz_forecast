package com.example.kimichael.yamblz_forecast;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.example.kimichael.yamblz_forecast.domain.service.forecast.ForecastJobService;
import com.example.kimichael.yamblz_forecast.presentation.di.component.AppComponent;
import com.example.kimichael.yamblz_forecast.presentation.di.component.DaggerAppComponent;
import com.example.kimichael.yamblz_forecast.presentation.di.component.ForecastComponent;
import com.example.kimichael.yamblz_forecast.presentation.di.component.ForecastScreenComponent;
import com.example.kimichael.yamblz_forecast.presentation.di.component.SettingsScreenComponent;
import com.example.kimichael.yamblz_forecast.presentation.di.module.AppModule;
import com.example.kimichael.yamblz_forecast.presentation.di.module.ForecastModule;
import com.example.kimichael.yamblz_forecast.presentation.di.module.ForecastScreenModule;
import com.example.kimichael.yamblz_forecast.presentation.di.module.SettingsScreenModule;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Kim Michael on 16.07.17
 */
public class App extends Application {

    private static App instance;
    private AppComponent appComponent;
    private ForecastComponent forecastComponent;
    private ForecastScreenComponent forecastScreenComponent;
    private SettingsScreenComponent settingsScreenComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        setInstance(this);
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        PreferencesManager manager = new PreferencesManager(sp);
        if (!manager.containInterval()) {
            int interval = 3600;
            manager.saveInterval();
            ForecastJobService.scheduleSync(this, interval);
        }
    }

    private static void setInstance(App instance) {
        App.instance = instance;
    }

    public static App getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public ForecastComponent getForecastComponent() {
        if (forecastComponent == null) {
            forecastComponent = getAppComponent().plus(new ForecastModule());
        }
        return forecastComponent;
    }

    public void releaseForecastComponent() {
        forecastComponent = null;
    }

    public ForecastScreenComponent getForecastScreenComponent() {
        if (forecastScreenComponent == null) {
            forecastScreenComponent = getForecastComponent().plus(new ForecastScreenModule());
        }
        return forecastScreenComponent;
    }

    public void releaseForecastScreenComponent() {
        forecastScreenComponent = null;
    }

    public SettingsScreenComponent getSettingsScreenComponent() {
        if (settingsScreenComponent == null) {
            settingsScreenComponent = getForecastComponent().plus(new SettingsScreenModule());
        }
        return settingsScreenComponent;
    }

    public void releaseSettingsScreenComponent() {
        settingsScreenComponent = null;
    }

}
