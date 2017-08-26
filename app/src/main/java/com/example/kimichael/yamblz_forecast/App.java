package com.example.kimichael.yamblz_forecast;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.example.kimichael.yamblz_forecast.domain.service.forecast.ForecastJobService;
import com.example.kimichael.yamblz_forecast.presentation.di.component.AppComponent;
import com.example.kimichael.yamblz_forecast.presentation.di.component.DaggerAppComponent;
import com.example.kimichael.yamblz_forecast.presentation.di.component.ForecastComponent;
import com.example.kimichael.yamblz_forecast.presentation.di.component.ForecastScreenComponent;
import com.example.kimichael.yamblz_forecast.presentation.di.component.SettingsComponent;
import com.example.kimichael.yamblz_forecast.presentation.di.component.SettingsScreenComponent;
import com.example.kimichael.yamblz_forecast.presentation.di.module.AppModule;
import com.example.kimichael.yamblz_forecast.presentation.di.module.ForecastModule;
import com.example.kimichael.yamblz_forecast.presentation.di.module.ForecastScreenModule;
import com.example.kimichael.yamblz_forecast.presentation.di.module.SettingsModule;
import com.example.kimichael.yamblz_forecast.presentation.di.module.SettingsScreenModule;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

/**
 * Created by Kim Michael on 16.07.17
 */
public class App extends Application {

    private static App instance;
    private AppComponent appComponent;
    private ForecastComponent forecastComponent;
    private SettingsComponent settingsComponent;
    private ForecastScreenComponent forecastScreenComponent;
    private SettingsScreenComponent settingsScreenComponent;
    private PreferencesManager manager;

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
        manager = new PreferencesManager(getBaseContext());
        if (!manager.containInterval()) {
            int interval = PreferencesManager.DEFAULT_INTERVAL;
            manager.saveInterval(0);
            ForecastJobService.scheduleSync(this, interval);
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
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

    public SettingsComponent getSettingsComponent() {
        if (settingsComponent == null) {
            settingsComponent = getAppComponent().plus(new SettingsModule());
        }
        return settingsComponent;
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
            settingsScreenComponent = getSettingsComponent().plus(new SettingsScreenModule());
        }
        return settingsScreenComponent;
    }

    public void releaseSettingsScreenComponent() {
        settingsScreenComponent = null;
    }

    private static class CrashReportingTree extends Timber.Tree {
        @Override protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }
            log(priority, tag, message);
        }
    }
}
