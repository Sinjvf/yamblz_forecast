package com.example.kimichael.yamblz_forecast.interactors;

import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.domain.interactor.settings.SettingsInteractor;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.PhoneWeatherPresenter;
import com.example.kimichael.yamblz_forecast.presentation.view.main.phone.MainWeatherView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.subjects.PublishSubject;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;

/**
 * Created by Sinjvf on 13.08.2017.
 * check invocation of interactor methods when presenter call
 */

@RunWith(MockitoJUnitRunner.class)
public class PhoneWeatherInteractorTest {

    private PhoneWeatherPresenter<MainWeatherView> presenter;
    private SettingsInteractor interactor;
    private Scheduler scheduler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.interactor = Mockito.mock(SettingsInteractor.class);
        this.scheduler = Mockito.mock(Scheduler.class);
        presenter = new PhoneWeatherPresenter<MainWeatherView>(interactor,scheduler);

    }

    @Test
    public void checkGetCities() {
        presenter.getCities();
        verify(interactor).getAllCities(any());
    }

    @Test
    public void setItemPos() {
        int item = 9;
        int newItem = presenter.setItemPos(item);
        assertEquals(item, newItem);
    }


}
