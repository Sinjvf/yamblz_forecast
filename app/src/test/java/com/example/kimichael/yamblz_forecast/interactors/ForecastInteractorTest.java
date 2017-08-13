package com.example.kimichael.yamblz_forecast.interactors;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.kimichael.yamblz_forecast.data.ForecastRepository;
import com.example.kimichael.yamblz_forecast.data.network.forecast.response.Forecast;
import com.example.kimichael.yamblz_forecast.domain.interactor.forecast.ForecastInteractor;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.ForecastPresenter;
import com.example.kimichael.yamblz_forecast.utils.PlaceData;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by Sinjvf on 30.07.2017.
 * check invocation of repository methods when interactor call
 */

@RunWith(MockitoJUnitRunner.class)
public class ForecastInteractorTest {
    @Mock
    private ForecastRepository repository;
    @Mock
    private Scheduler threadExecutor;
    @Mock
    private Scheduler postExecutionThread;
    @Mock
    private Context context;
    @Mock
    private PreferencesManager manager;

    private ForecastInteractor interactor;
    private ForecastPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        interactor = new ForecastInteractor(threadExecutor, postExecutionThread, repository, context, manager);
        presenter = new ForecastPresenter(interactor);

        Mockito.when(manager.getPlace()).thenReturn(new PlaceData("name", 1.0, 1.0));
        Mockito.when(repository.getForecast(anyObject())).thenReturn(new Single<Forecast>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super Forecast> observer) {

            }
        });
    }


    @Test
    public void checkRepositoryGetForecast() {
        presenter.getForecast(false);
        verify(repository).getForecast(anyObject());
    }


}
