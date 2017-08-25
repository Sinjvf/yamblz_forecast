package com.example.kimichael.yamblz_forecast.domain.interactor.forecast;

import android.content.Context;

import com.example.kimichael.yamblz_forecast.data.common.ForecastInfo;
import com.example.kimichael.yamblz_forecast.data.database.DbClientImpl;
import com.example.kimichael.yamblz_forecast.data.network.forecast.ForecastRepository;
import com.example.kimichael.yamblz_forecast.domain.interactor.SingleInteractor;
import com.example.kimichael.yamblz_forecast.domain.interactor.requests.ForecastRequest;
import com.example.kimichael.yamblz_forecast.presentation.di.module.SchedulersModule;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Kim Michael on 16.07.17
 */
public class ForecastInteractor extends SingleInteractor {

    private final ForecastRepository forecastRepository;
    private final Context context;
    private final DbClientImpl dbClient;
    private final Scheduler postExecutionThread;
    private final PreferencesManager manager;

    private Disposable forecastDisposable;


    @Inject
    public ForecastInteractor(@Named(SchedulersModule.JOB) Scheduler threadExecutor,
                              @Named(SchedulersModule.UI) Scheduler postExecutionThread, ForecastRepository forecastRepository,
                              Context context, DbClientImpl dbClient, PreferencesManager manager) {
        super(threadExecutor, postExecutionThread);
        this.forecastRepository = forecastRepository;
        this.postExecutionThread = postExecutionThread;
        this.context = context;
        this.dbClient = dbClient;
        this.manager = manager;
    }

    public void getForecast(ForecastRequest params, SingleObserver<List<ForecastInfo>> observer) {
        if (!params.isForceUpdate()) {
            if (forecastDisposable != null) forecastDisposable.dispose();
            PublishSubject<List<ForecastInfo>> subject = PublishSubject.create();
            subject.observeOn(postExecutionThread)
                    .subscribe(getForecastFromDbObserver(params, observer));
            dbClient.getActualWeather(params.getCityId(), manager.getInterval(), subject);
        } else {
            getForecastsFromNet(params, observer);
        }
    }

    private void getForecastsFromNet(ForecastRequest params, SingleObserver<List<ForecastInfo>> observer) {
        BuildUseCaseObservable<List<ForecastInfo>, ForecastRequest> build = forecastRepository::getForecast;
        execute(build, params).subscribe(observer);
    }

    private Observer<List<ForecastInfo>> getForecastFromDbObserver(ForecastRequest params, SingleObserver<List<ForecastInfo>> observer) {
        return new Observer<List<ForecastInfo>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                forecastDisposable = d;
            }

            @Override
            public void onNext(@NonNull List<ForecastInfo> forecastInfos) {
                if (forecastInfos.size() != 0) {
                    Single.fromObservable(Observable.just(forecastInfos)).subscribe(observer);
                } else {
                    getForecastsFromNet(params, observer);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getForecastsFromNet(params, observer);
            }

            @Override
            public void onComplete() {

            }
        };
    }


    public void saveForecast(List<ForecastInfo> data, Integer cityId) {
        for (ForecastInfo info : data) info.setCityId(cityId);
        dbClient.saveForecast(data);
    }

}
