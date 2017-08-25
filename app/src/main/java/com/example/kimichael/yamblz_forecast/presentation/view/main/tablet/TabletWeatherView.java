package com.example.kimichael.yamblz_forecast.presentation.view.main.tablet;

import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.presentation.view.main.phone.MainWeatherView;

/**
 * Created by Sinjvf on 12.08.2017.
 * View for tablet presenter
 */

public interface TabletWeatherView extends MainWeatherView{
    void changeFragment( int fragmentType, PlaceData data);
}
