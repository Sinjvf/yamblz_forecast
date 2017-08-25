package com.example.kimichael.yamblz_forecast.views;

import android.content.Context;

import com.example.kimichael.yamblz_forecast.data.common.ForecastInfo;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.domain.interactor.forecast.ForecastInteractor;
import com.example.kimichael.yamblz_forecast.domain.interactor.requests.ForecastRequest;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.ForecastPresenter;
import com.example.kimichael.yamblz_forecast.presentation.view.forecast.ForecastView;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;
import com.example.kimichael.yamblz_forecast.utils.Utility;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Sinjvf on 30.07.2017.
 * check invocation of ForecastView methods when presenter call
 */

@RunWith(MockitoJUnitRunner.class)
public class ForecastViewTest {
    @Mock
    private ForecastView view;

    private ForecastInteractor interactor;
    private ForecastPresenter presenter;
    private List<ForecastInfo> infos;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.interactor = Mockito.mock(ForecastInteractor.class);
        infos = new ArrayList<>();
        infos.add(new ForecastInfo(1, 2, 3, 4, 5, 6, 7, 8, 9, "info", 0L, 1L));
        presenter = new ForecastPresenter(interactor);
        presenter.onAttach(view);
        presenter.setData(new PlaceData(1, "name", 4.6, 67.9));
    }


    @Test
    public void checkSuccessResult() {
        Single<List<ForecastInfo>> singleSuccess = Single.fromObservable(Observable.fromArray(infos));
        singleSuccess.subscribeWith(presenter.getForecastObserver());
        verify(view).showForecast(anyList(), anyInt());
        verify(interactor).saveForecast(anyList(), anyInt());
    }

    @Test
    public void checkErrorResult() {
        Single<List<ForecastInfo>> singleSuccess = Single.error(new Throwable("error"));
        singleSuccess.subscribeWith(presenter.getForecastObserver());
        verify(view).showError(any());
    }


    @Test
    public void getRequest() {
        boolean force = true;
        ForecastRequest req = presenter.getRequest(force);
        assertEquals(force, req.isForceUpdate());
        boolean force2 = false;
        ForecastRequest req2 = presenter.getRequest(force2);
        assertEquals(force2, req2.isForceUpdate());
    }

    @Test
    public void showSure() {
        presenter.showSureDialog();
        verify(view).showSureDialog();
    }

    @Test
    public void checkSuccessDetach() {
        presenter.onDetach();
        Single<List<ForecastInfo>> singleSuccess = Single.fromObservable(Observable.fromArray(infos));
        singleSuccess.subscribeWith(presenter.getForecastObserver());
        verify(view, never()).showForecast(anyList(), anyInt());
        verify(interactor).saveForecast(anyList(), anyInt());
    }

    @Test
    public void showSureDetach() {
        presenter.onDetach();
        presenter.showSureDialog();
        verify(view, never()).showSureDialog();
    }

    @Test
    public void checkErrorResultDetach() {
        presenter.onDetach();
        Single<List<ForecastInfo>> singleSuccess = Single.error(new Throwable("error"));
        singleSuccess.subscribeWith(presenter.getForecastObserver());
        verify(view, never()).showError(any());
    }

}
