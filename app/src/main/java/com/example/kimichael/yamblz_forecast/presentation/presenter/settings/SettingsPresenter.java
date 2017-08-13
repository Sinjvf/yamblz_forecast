package com.example.kimichael.yamblz_forecast.presentation.presenter.settings;

import com.example.kimichael.yamblz_forecast.domain.interactor.settings.SettingsInteractor;
import com.example.kimichael.yamblz_forecast.presentation.BasePresenter;
import com.example.kimichael.yamblz_forecast.presentation.view.settings.SettingsView;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created on 22.07.2017.
 */
public class SettingsPresenter extends BasePresenter<SettingsView> {

    SettingsInteractor settingsInteractor;

    @Inject
    public SettingsPresenter(SettingsInteractor settingsInteractor) {
        this.settingsInteractor = settingsInteractor;
    }


    public Observer<Place> getPlaceChangeObserver(){
        return settingsInteractor.getPlaceChangeObserver();
    }

}
