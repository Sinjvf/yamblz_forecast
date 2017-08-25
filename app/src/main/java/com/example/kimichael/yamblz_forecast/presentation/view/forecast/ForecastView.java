package com.example.kimichael.yamblz_forecast.presentation.view.forecast;

import com.example.kimichael.yamblz_forecast.data.common.ForecastInfo;
import com.example.kimichael.yamblz_forecast.presentation.view.BaseView;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;

import java.util.List;

/**
 * Created by Kim Michael on 16.07.17
 */
public interface ForecastView extends BaseView {

    void showForecast(List<ForecastInfo> forecastsList, int color);
    PlaceData getPlace();
    void showSureDialog();
    void showProgress(boolean show);
    void showError(Throwable e);
}
