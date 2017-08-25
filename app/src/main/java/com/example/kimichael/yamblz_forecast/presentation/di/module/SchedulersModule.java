package com.example.kimichael.yamblz_forecast.presentation.di.module;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kim Michael on 16.07.17
 */
@Module
public class SchedulersModule {
    public static final String JOB = "JOB";
    public static final String UI = "UI";

    @Provides
    @Singleton
    @Named(JOB)
    Scheduler provideJobScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Singleton
    @Named(UI)
    Scheduler provideUIScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
