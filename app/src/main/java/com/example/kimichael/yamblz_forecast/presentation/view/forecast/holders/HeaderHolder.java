package com.example.kimichael.yamblz_forecast.presentation.view.forecast.holders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.data.common.ForecastInfo;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.ForecastPresenter;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;
import com.example.kimichael.yamblz_forecast.utils.Utility;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by Sinjvf on 06.08.2017.
 * holder for 0th element - header. Contains current weather
 */

public class HeaderHolder extends BaseHolder {

    @BindView(R.id.humidity_text)
    TextView humidity;
    @BindView(R.id.temp)
    TextView temperature;
    @BindView(R.id.weather_icon)
    ImageView weatherIcon;
    @BindView(R.id.wind_text)
    TextView windSpeed;
    @BindView(R.id.description)
    TextView description;

    public HeaderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(ForecastInfo forecast) {
        Context context = weatherIcon.getContext();
        PreferencesManager manager = new PreferencesManager(context);
        temperature.setText(Utility.formatTemperature(manager, context, forecast.getTemp()));
        weatherIcon.setImageDrawable(ContextCompat.getDrawable(context,
                Utility.getImageForWeatherCondition(forecast.getWeatherId())));
        windSpeed.setText(context.getString(R.string.format_wind, (int) forecast.getWindSpeed()));
        humidity.setText(context.getString(R.string.format_humidity, (int) forecast.getHumidity()));
        description.setText(forecast.getDescription());
    }
}
