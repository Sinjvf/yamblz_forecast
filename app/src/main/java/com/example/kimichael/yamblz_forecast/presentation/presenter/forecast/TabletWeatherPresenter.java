package com.example.kimichael.yamblz_forecast.presentation.presenter.forecast;

import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.domain.interactor.settings.SettingsInteractor;
import com.example.kimichael.yamblz_forecast.presentation.di.module.SchedulersModule;
import com.example.kimichael.yamblz_forecast.presentation.view.main.tablet.TabletWeatherView;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;

/**
 * Created by Sinjvf on 12.08.2017.
 * Presenter for main tablet fragment
 */

public class TabletWeatherPresenter extends PhoneWeatherPresenter<TabletWeatherView> {
    private SettingsInteractor interactor;


    @Inject
    public TabletWeatherPresenter(SettingsInteractor forecastInteractor, @Named(SchedulersModule.UI) Scheduler postExecutionThread) {
        super(forecastInteractor, postExecutionThread);
        this.interactor = forecastInteractor;
    }

    public int setItemPos(int currentPos) {
        this.currentCityPos = currentPos;
        PlaceData data = null;
        int type = 0;
        if (currentPos >= currentList.size()) {
            type = currentPos - currentList.size() + 1;
        }else{
            data = currentList.get(currentPos);
        }
        if (getView() != null) getView().changeFragment(type, data);
        return currentCityPos;
    }


}
