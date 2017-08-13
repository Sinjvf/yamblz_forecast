package com.example.kimichael.yamblz_forecast.domain.interactor.forecast;

import android.content.Context;

import com.example.kimichael.yamblz_forecast.data.ForecastRepository;
import com.example.kimichael.yamblz_forecast.data.network.forecast.response.Forecast;
import com.example.kimichael.yamblz_forecast.domain.interactor.SingleInteractor;
import com.example.kimichael.yamblz_forecast.presentation.di.module.SchedulersModule;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;
import com.example.kimichael.yamblz_forecast.utils.Utility;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;

/**
 * Created by Kim Michael on 16.07.17
 */
public class ForecastInteractor extends SingleInteractor<ForecastInfo, Boolean> {

    private final ForecastRepository forecastRepository;
    private final Context context;

    private PreferencesManager manager;

    @Inject
    public ForecastInteractor(@Named(SchedulersModule.JOB) Scheduler threadExecutor,
                       @Named(SchedulersModule.UI) Scheduler postExecutionThread, ForecastRepository forecastRepository,
                       Context context, PreferencesManager manager) {
        super(threadExecutor, postExecutionThread);
        this.forecastRepository = forecastRepository;
        this.context = context;
        this.manager = manager;
    }

    @Override
    protected Single<ForecastInfo> buildUseCaseObservable(Boolean forceUpdate) {
        Single<Forecast> response = forecastRepository.getForecast(new ForecastRequest(manager.getPlace(), forceUpdate));
        response.subscribe(forecastRepository::saveForecast);
        return response.map(ForecastInfo::from);
    }


}
