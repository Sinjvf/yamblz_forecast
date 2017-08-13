package com.example.kimichael.yamblz_forecast.presentation.presenter.forecast;

import com.example.kimichael.yamblz_forecast.data.network.forecast.response.Forecast;
import com.example.kimichael.yamblz_forecast.domain.interactor.forecast.ForecastInfo;
import com.example.kimichael.yamblz_forecast.domain.interactor.forecast.ForecastInteractor;
import com.example.kimichael.yamblz_forecast.domain.interactor.forecast.ForecastRequest;
import com.example.kimichael.yamblz_forecast.presentation.BasePresenter;
import com.example.kimichael.yamblz_forecast.presentation.view.forecast.ForecastView;
import com.example.kimichael.yamblz_forecast.utils.Utility;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Kim Michael on 16.07.17
 */
public class ForecastPresenter extends BasePresenter<ForecastView> {

    private ForecastInteractor forecastInteractor;
    private ForecastInfo cachedForecast;

    @Inject
    public ForecastPresenter(ForecastInteractor forecastInteractor) {
        this.forecastInteractor = forecastInteractor;
    }

    public void getForecast(boolean forceUpdate) {
        if (cachedForecast != null && !forceUpdate && getView() != null) {
            getView().showForecast(cachedForecast);
            return;
        }
        forecastInteractor.execute(getObserver(), forceUpdate);
    }

    public SingleObserver<ForecastInfo> getObserver(){
        return new SingleObserver<ForecastInfo>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull ForecastInfo forecastInfo) {
                if(getView() != null)
                    getView().showForecast(forecastInfo);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                if(getView() != null)
                    getView().showError();
            }
        };
    }

}
