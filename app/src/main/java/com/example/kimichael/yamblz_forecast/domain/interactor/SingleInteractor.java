package com.example.kimichael.yamblz_forecast.domain.interactor;

import com.example.kimichael.yamblz_forecast.domain.interactor.requests.BaseRequest;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Kim Michael on 16.07.17
 */
public abstract class SingleInteractor {
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;


    public SingleInteractor(Scheduler threadExecutor, Scheduler postExecutionThread) {
        this.jobScheduler = threadExecutor;
        this.uiScheduler = postExecutionThread;
    }

    public interface BuildUseCaseObservable<R, P> {
        Single<R> build(P params);
    }

    public  <R, P> Single<R> execute(BuildUseCaseObservable<R, P> build, P params) {
        return build.build(params)
                .subscribeOn(jobScheduler)
                .observeOn(uiScheduler);

    }

}