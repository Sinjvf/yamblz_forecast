package com.example.kimichael.yamblz_forecast.presentation.presenter.forecast;

import com.example.kimichael.yamblz_forecast.data.common.ForecastInfo;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.domain.interactor.forecast.ForecastInteractor;
import com.example.kimichael.yamblz_forecast.domain.interactor.requests.ForecastRequest;
import com.example.kimichael.yamblz_forecast.presentation.presenter.BasePresenter;
import com.example.kimichael.yamblz_forecast.presentation.view.forecast.ForecastView;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;
import com.example.kimichael.yamblz_forecast.utils.Utility;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by Kim Michael on 16.07.17
 */
public class ForecastPresenter extends BasePresenter<ForecastView> {

    private ForecastInteractor forecastInteractor;
    @Inject
    PreferencesManager manager;
    private PlaceData data;

    public void setData(PlaceData data) {
        this.data = data;
    }

    @Inject
    public ForecastPresenter(ForecastInteractor forecastInteractor) {
        this.forecastInteractor = forecastInteractor;
    }

    public void getForecast(boolean forceUpdate) {
        if (getView() != null) getView().showProgress(true);
        forecastInteractor.getForecast(getRequest(forceUpdate), getForecastObserver());
    }

    public SingleObserver<List<ForecastInfo>> getForecastObserver() {
        return new SingleObserver<List<ForecastInfo>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull List<ForecastInfo> forecastInfo) {
                if (getView() != null) getView().showProgress(false);
                int id = 0;
                if (forecastInfo!=null && forecastInfo.size()>0){
                    id = Utility.getColorWeatherCondition(forecastInfo.get(0).getWeatherId());
                }
                if (getView() != null) getView().showForecast(forecastInfo, id);
                forecastInteractor.saveForecast(forecastInfo, data.getId());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (getView() != null) getView().showProgress(false);
                Timber.e(e);
                if (getView() != null) getView().showError(e);
            }
        };
    }

    public ForecastRequest getRequest(boolean update) {
        return new ForecastRequest(data, update);
    }

    public void showSureDialog() {
        if (getView() == null) return;
        getView().showSureDialog();
    }


}
