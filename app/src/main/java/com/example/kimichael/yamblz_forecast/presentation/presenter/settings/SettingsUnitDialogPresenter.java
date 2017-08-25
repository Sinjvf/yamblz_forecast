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
 * dialog for unit choosing
 */

public class SettingsUnitDialogPresenter extends BasePresenter<SelectableDialogView> {
    private PreferencesManager manager;

    public SettingsUnitDialogPresenter(PreferencesManager manager) {
        this.manager = manager;
    }

    @Override
    public void onAttach(@NonNull SelectableDialogView view) {
        super.onAttach(view);
        getView().checkPosition(getCurrent());
    }

    public void saveLastUnit(int position){
        Timber.d("saveLastUnit: "+position);
        manager.saveTempUnit(position);
    }

    public int getCurrent(){
        if(manager.isTempCelsius()) return 0;
        return 1;
    }
}
