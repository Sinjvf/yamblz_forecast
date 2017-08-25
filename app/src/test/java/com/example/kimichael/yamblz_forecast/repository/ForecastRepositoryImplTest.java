package com.example.kimichael.yamblz_forecast.repository;

import com.example.kimichael.yamblz_forecast.data.common.ForecastInfo;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.data.network.forecast.ForecastRepositoryImpl;
import com.example.kimichael.yamblz_forecast.data.network.forecast.OpenWeatherClient;
import com.example.kimichael.yamblz_forecast.data.network.forecast.response.ForecastResponse;
import com.example.kimichael.yamblz_forecast.data.network.forecast.response.WeatherResponse;
import com.example.kimichael.yamblz_forecast.domain.interactor.requests.ForecastRequest;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.ForecastPresenter;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by Sinjvf on 13.08.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class ForecastRepositoryImplTest {
    @Mock
    private OpenWeatherClient openWeatherClient;
    private ForecastRepositoryImpl repository;
    private ForecastResponse resp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Gson gson = new Gson();
        repository = new ForecastRepositoryImpl(openWeatherClient, gson);
        resp = new ForecastResponse();
        List<WeatherResponse>list = new ArrayList<>();
        list.add(new WeatherResponse());
        list.add(new WeatherResponse());
        list.add(new WeatherResponse());
        resp.setList(list);
        Mockito.when(openWeatherClient.getForecast(any(), any(), anyString()))
                .thenReturn(Single.just(resp));
    }

    @Test
    public void updateForecast() {
        repository.updateForecast(new PlaceData(4, "", 5, 6))
            .subscribe(new SingleObserver<List<ForecastInfo>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull List<ForecastInfo> forecastInfos) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
        verify(openWeatherClient).getForecast(any(), any(), any());
    }

    @Test
    public void getForecast() {
        repository.getForecast(new ForecastRequest(new PlaceData(4, "", 5, 6), false))
                .subscribe(new SingleObserver<List<ForecastInfo>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<ForecastInfo> forecastInfos) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
        verify(openWeatherClient).getForecast(any(), any(), any());
    }
}
