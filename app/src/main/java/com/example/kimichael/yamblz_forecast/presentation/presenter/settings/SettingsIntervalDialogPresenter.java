package com.example.kimichael.yamblz_forecast.presentation.presenter.settings;

import android.support.annotation.NonNull;

import com.example.kimichael.yamblz_forecast.presentation.presenter.BasePresenter;
import com.example.kimichael.yamblz_forecast.presentation.view.settings.SettingsView;
import com.example.kimichael.yamblz_forecast.presentation.view.settings.dialogs.select.SelectableDialogView;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Sinjvf on 06.08.2017.
 * dialog for interval choosing
 */

public class SettingsIntervalDialogPresenter extends BasePresenter<SelectableDialogView> {

    PreferencesManager manager;

    public SettingsIntervalDialogPresenter(PreferencesManager manager) {
        this.manager = manager;
    }

    @Override
    public void onAttach(@NonNull SelectableDialogView view) {
        super.onAttach(view);
        getView().checkPosition(manager.getIntervalPos());
    }

    public void saveLastInterval(int position){
        Timber.d("saveLastInterval: "+position);
        manager.saveInterval(position);
    }


    public int getCurrent(){
       return (manager.getIntervalPos());
    }
}
