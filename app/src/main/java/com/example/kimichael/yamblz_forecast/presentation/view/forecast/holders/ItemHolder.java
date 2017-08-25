package com.example.kimichael.yamblz_forecast.presentation.view.forecast.holders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.data.common.ForecastInfo;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;
import com.example.kimichael.yamblz_forecast.utils.Utility;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sinjvf on 06.08.2017.
 * holder for forecast elements
 */

public class ItemHolder extends BaseHolder {

    @BindView(R.id.weather_icon)
    ImageView weatherIcon;
    @BindView(R.id.tempr)
    TextView temprView;
    @BindView(R.id.humidity_text)
    TextView humidityView;
    @BindView(R.id.date)
    TextView date;

    public ItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(ForecastInfo forecast) {
        Context context = weatherIcon.getContext();
        PreferencesManager manager  = new PreferencesManager(context);
        temprView.setText(Utility.formatTemperature(manager, context, forecast.getTemp()));
        humidityView.setText(context.getString(R.string.format_humidity,(int)forecast.getHumidity()));
        weatherIcon.setImageDrawable(ContextCompat.getDrawable(context,
                Utility.getImageForWeatherCondition(forecast.getWeatherId())));
        date.setText(Utility.parceToStr(forecast.getDate(), Locale.getDefault()));
    }
}
