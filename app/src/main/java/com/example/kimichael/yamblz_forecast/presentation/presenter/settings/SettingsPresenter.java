package com.example.kimichael.yamblz_forecast.presentation.presenter.settings;

import android.app.DialogFragment;
import android.support.v4.util.Pair;

import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.domain.interactor.settings.SettingsInteractor;
import com.example.kimichael.yamblz_forecast.presentation.presenter.BasePresenter;
import com.example.kimichael.yamblz_forecast.presentation.view.settings.SettingsView;

import javax.inject.Inject;

/**
 * Created on 22.07.2017.
 */
public class SettingsPresenter extends BasePresenter<SettingsView> {

    private SettingsInteractor settingsInteractor;

    @Inject
    public SettingsPresenter(SettingsInteractor settingsInteractor) {
        this.settingsInteractor = settingsInteractor;
    }


    public void sureDeleteCity(Pair<PlaceData, DialogFragment> data) {
        settingsInteractor.deleteCity(data.first);
        if (data.second != null) data.second.dismiss();
    }

    public void notSureDeleteCity(DialogFragment data) {
        if (data == null) return;
        data.dismiss();
    }
}
