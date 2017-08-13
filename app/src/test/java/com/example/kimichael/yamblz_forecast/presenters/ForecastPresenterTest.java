package com.example.kimichael.yamblz_forecast.presenters;

import com.example.kimichael.yamblz_forecast.domain.interactor.forecast.ForecastInteractor;
import com.example.kimichael.yamblz_forecast.presentation.BaseView;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.ForecastPresenter;
import com.example.kimichael.yamblz_forecast.presentation.view.forecast.ForecastView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;

/**
 * Created by Sinjvf on 30.07.2017.
 * check invocation of forecastInteractor methods when presenter call
 */

@RunWith(MockitoJUnitRunner.class)
public class ForecastPresenterTest {
    @Mock
    private ForecastInteractor forecastInteractor;

    private ForecastPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new ForecastPresenter(forecastInteractor);
    }


    @Test
    public void checkInteractorExec() {
        presenter.getForecast(false);
        verify(forecastInteractor).execute(anyObject(), anyBoolean());
    }


}
