package com.example.kimichael.yamblz_forecast.presentation.view.main.phone;

import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.presentation.view.BaseView;

import java.util.List;

/**
 * Created by Sinjvf on 08.08.2017.
 * main screen view
 */

public interface MainWeatherView extends BaseView{
    void updateCitiesList(List<PlaceData> dataList, int currentPos);
}
