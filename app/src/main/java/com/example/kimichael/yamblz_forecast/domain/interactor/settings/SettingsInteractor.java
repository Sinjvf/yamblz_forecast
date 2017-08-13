package com.example.kimichael.yamblz_forecast.domain.interactor.settings;

import android.content.Context;

import com.example.kimichael.yamblz_forecast.data.ForecastRepository;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;
import com.example.kimichael.yamblz_forecast.utils.Utility;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created on 22.07.2017.
 */
public class SettingsInteractor {
    private final Context context;
    @Inject
    SettingsInteractor(ForecastRepository forecastRepository, Context context) {
        this.context = context;
    }

    public Observer<Place> getPlaceChangeObserver(){
        return new Observer<Place>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Place place) {
                PreferencesManager manager  = new PreferencesManager(context);
                manager.savePlace( place);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
}
