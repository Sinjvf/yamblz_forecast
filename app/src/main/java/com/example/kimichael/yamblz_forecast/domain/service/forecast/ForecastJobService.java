package com.example.kimichael.yamblz_forecast.domain.service.forecast;

import android.content.Context;
import android.util.Pair;

import com.example.kimichael.yamblz_forecast.App;
import com.example.kimichael.yamblz_forecast.data.common.ForecastInfo;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.data.database.DbClientImpl;
import com.example.kimichael.yamblz_forecast.data.network.forecast.ForecastRepository;
import com.example.kimichael.yamblz_forecast.presentation.di.module.ForecastModule;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Trigger;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

/**
 * Created by Kim Michael on 18.07.17
 */
public class ForecastJobService extends JobService {

    public static final String TAG = "forecast_service";

    @Inject
    ForecastRepository forecastRepository;
    @Inject
    DbClientImpl dbClient;

    public ForecastJobService() {
        super();
        App.getInstance().getAppComponent().plus(new ForecastModule()).inject(this);
    }

    @Override
    public boolean onStartJob(JobParameters job) {
       // PreferencesManager manager  = new PreferencesManager(getBaseContext());
        PublishSubject<List<PlaceData>> subj = PublishSubject.create();
        dbClient.getAllCities(subj);
        subj.flatMapIterable(list->list)
                .map(data->new Pair<>(data, job))
                .subscribe(this::updateWeather);
        return false;
    }

    private void updateWeather(Pair<PlaceData, JobParameters> data){
        Timber.d("updateForecast: "+data.first.toString());
        forecastRepository.updateForecast(data.first)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new SingleObserver<List<ForecastInfo>>() {
                               @Override
                               public void onSubscribe(@NonNull Disposable d) {

                               }

                               @Override
                               public void onSuccess(@NonNull List<ForecastInfo> forecastInfo) {
                                   dbClient.saveForecast(forecastInfo);
                                   jobFinished(data.second, false);
                               }

                               @Override
                               public void onError(@NonNull Throwable e) {

                               }
                           });
    }


    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }

    // Interval in seconds
    public static void scheduleSync(Context context, int interval) {
        FirebaseJobDispatcher dispatcher =
                new FirebaseJobDispatcher(new GooglePlayDriver(context));
        Job forecastJob = dispatcher.newJobBuilder()
                .setService(ForecastJobService.class)
                .setTag(ForecastJobService.TAG)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        interval, interval
                ))
                .build();

        dispatcher.cancelAll();

        dispatcher.mustSchedule(forecastJob);
    }
}
