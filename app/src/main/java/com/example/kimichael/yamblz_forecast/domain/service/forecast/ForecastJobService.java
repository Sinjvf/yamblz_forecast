package com.example.kimichael.yamblz_forecast.domain.service.forecast;

import android.content.Context;

import com.example.kimichael.yamblz_forecast.App;
import com.example.kimichael.yamblz_forecast.data.ForecastRepository;
import com.example.kimichael.yamblz_forecast.data.network.forecast.response.Forecast;
import com.example.kimichael.yamblz_forecast.domain.interactor.forecast.ForecastInteractor;
import com.example.kimichael.yamblz_forecast.domain.interactor.forecast.ForecastRequest;
import com.example.kimichael.yamblz_forecast.presentation.di.module.ForecastModule;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.ForecastPresenter;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;
import com.example.kimichael.yamblz_forecast.utils.Utility;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Trigger;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kim Michael on 18.07.17
 */
public class ForecastJobService extends JobService {

    public static final String TAG = "forecast_service";

    @Inject
    ForecastRepository forecastRepository;

    public ForecastJobService() {
        super();
        App.getInstance().getAppComponent().plus(new ForecastModule()).inject(this);
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        PreferencesManager manager  = new PreferencesManager(getBaseContext());
        forecastRepository.updateForecast(manager.getPlace())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(forecast ->
                        jobFinished(job, false));
        return false;
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
