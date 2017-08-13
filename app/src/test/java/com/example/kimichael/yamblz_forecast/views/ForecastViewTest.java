package com.example.kimichael.yamblz_forecast.views;

import android.database.Observable;

import com.example.kimichael.yamblz_forecast.domain.interactor.forecast.ForecastInfo;
import com.example.kimichael.yamblz_forecast.domain.interactor.forecast.ForecastInteractor;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.ForecastPresenter;
import com.example.kimichael.yamblz_forecast.presentation.view.forecast.ForecastFragment;
import com.example.kimichael.yamblz_forecast.presentation.view.forecast.ForecastView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Single;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;

/**
 * Created by Sinjvf on 30.07.2017.
 * check invocation of ForecastView methods when presenter call
 */

@RunWith(MockitoJUnitRunner.class)
public class ForecastViewTest {
    @Mock
    private ForecastView view;

    private ForecastPresenter presenter = new ForecastPresenter(null);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter.onAttach(view);
    }


    @Test
    public void checkSuccessResult() {
        presenter.getObserver();
        Single <ForecastInfo> singleSuccess = Single.fromObservable(io.reactivex.Observable.fromArray(getInfo()));
        singleSuccess.subscribeWith(presenter.getObserver());
        verify(view).showForecast(anyObject());
    }

    @Test
    public void checkErrorResult() {
        presenter.getObserver();
        Single <ForecastInfo> singleSuccess = Single.error(new Throwable("error"));
        singleSuccess.subscribeWith(presenter.getObserver());
        verify(view).showError();
    }


    private ForecastInfo getInfo(){
        return new ForecastInfo("city", 1.0, 1.0, 1.0, 2, 1.0, 1.0, 1.0, "");
    }
}
