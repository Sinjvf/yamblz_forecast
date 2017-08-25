package com.example.kimichael.yamblz_forecast.repository;

import android.content.Context;

import com.example.kimichael.yamblz_forecast.data.common.ForecastInfo;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.data.database.DbClientImpl;
import com.example.kimichael.yamblz_forecast.data.network.forecast.ForecastRepository;
import com.example.kimichael.yamblz_forecast.data.network.places.PlacesRepository;
import com.example.kimichael.yamblz_forecast.domain.interactor.forecast.ForecastInteractor;
import com.example.kimichael.yamblz_forecast.domain.interactor.requests.ForecastRequest;
import com.example.kimichael.yamblz_forecast.domain.interactor.settings.SettingsInteractor;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.ForecastPresenter;
import com.example.kimichael.yamblz_forecast.presentation.presenter.settings.SettingsPresenter;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by Sinjvf on 30.07.2017.
 * check invocation of repository methods when interactor call
 */

@RunWith(MockitoJUnitRunner.class)
public class SettingsRepositoryTest {
    @Mock
    private PlacesRepository repository;
    @Mock
    private Scheduler threadExecutor;
    @Mock
    private Scheduler postExecutionThread;
    @Mock
    private Context context;
    @Mock
    private PreferencesManager manager;
    @Mock
    private DbClientImpl client;

    private SettingsInteractor interactor;
    private SettingsPresenter presenter;
    private long interval = 890;
    private Integer cityId = 897;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        interactor = new SettingsInteractor(threadExecutor, postExecutionThread, repository, context, manager, client);
        presenter = new SettingsPresenter(interactor);
        Mockito.when(manager.getInterval()).thenReturn((int)interval);
    }

    @Test
    public void checkAddCity() {
        PlaceData data = new PlaceData(4, "3", 2, 3);
        interactor.addCity(data);
        verify(client).addCity(data);
    }
    @Test
    public void checkDeleteCity() {
        PlaceData data = new PlaceData(4, "3", 2, 3);
        interactor.deleteCity(data);
        verify(client).deleteCity(data);
    }

    @Test
    public void checkGetAllCities() {
        PublishSubject<List<PlaceData>> data = PublishSubject.create();
        interactor.getAllCities(data);
        verify(client).getAllCities(data);
    }

}
