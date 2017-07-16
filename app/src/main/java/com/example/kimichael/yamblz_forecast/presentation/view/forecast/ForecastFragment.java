package com.example.kimichael.yamblz_forecast.presentation.view.forecast;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kimichael.yamblz_forecast.App;
import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.data.network.response.Forecast;
import com.example.kimichael.yamblz_forecast.presentation.di.module.ForecastModule;
import com.example.kimichael.yamblz_forecast.presentation.di.module.ForecastScreenModule;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.ForecastPresenter;
import com.example.kimichael.yamblz_forecast.utils.Utility;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastFragment extends Fragment implements ForecastView {

    @BindView(R.id.city_name)
    TextView cityName;
    @BindView(R.id.min_temp)
    TextView minTemp;
    @BindView(R.id.max_temp)
    TextView maxTemp;
    @BindView(R.id.weather_icon)
    ImageView weatherIcon;
    @BindView(R.id.no_internet_block)
    LinearLayout noInternetBlock;
    @BindView(R.id.no_internet_message)
    TextView noInternetMessage;

    @Inject
    ForecastPresenter forecastPresenter;

    public ForecastFragment() {}

    public static ForecastFragment newInstance() {
        ForecastFragment fragment = new ForecastFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getInstance().getAppComponent()
                .plus(new ForecastModule()).plus(new ForecastScreenModule()).inject(this);

        forecastPresenter.onAttach(this);

        if (savedInstanceState == null) {
            forecastPresenter.getForecast();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            forecastPresenter.getForecast();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecast_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void showForecast(Forecast forecast) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        cityName.setText(forecast.getName());
        minTemp.setText(Utility.formatTemperature(getContext(),
                forecast.getTemp().getTempMin(),
                sp.getString(getString(R.string.pref_key_temp_units),
                        getString(R.string.celcius)).equals(getString(R.string.celcius))));
        maxTemp.setText(Utility.formatTemperature(getContext(), forecast.getTemp().getTempMax(),
                sp.getString(getString(R.string.pref_key_temp_units),
                getString(R.string.celcius)).equals(getString(R.string.celcius))));
        weatherIcon.setImageDrawable(getResources().getDrawable(
                Utility.getImageForWeatherCondition(forecast.getWeather().get(0).getId())));
    }

    @Override
    public void showError() {

    }
}