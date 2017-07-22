package com.example.kimichael.yamblz_forecast.presentation.view.settings;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kimichael.yamblz_forecast.App;
import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.domain.service.forecast.ForecastJobService;
import com.example.kimichael.yamblz_forecast.presentation.presenter.settings.SettingsPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SettingsFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences sharedPreferences;
    @BindView(R.id.temp_units_button)
    TextView tempUnitsButton;
    @BindView(R.id.sync_interval_button)
    TextView syncIntervalButton;

    @Inject
    SettingsPresenter presenter;

    Unbinder unbinder;


    public SettingsFragment() {}

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getInstance().getSettingsScreenComponent().inject(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(getString(R.string.pref_key_sync_interval))) {
            int interval = Integer.valueOf(sharedPreferences.getString(getString(R.string.pref_key_sync_interval), "3600"));
            ForecastJobService.scheduleSync(getContext(), interval);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        App.getInstance().releaseSettingsScreenComponent();
        super.onDestroy();
    }

    @OnClick(R.id.temp_units_button)
    public void showTempUnitsDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setSingleChoiceItems(R.array.temp_units, 0, null)
        .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
            ListView lv = ((AlertDialog)dialogInterface).getListView();
            String chosenUnits = (String) lv.getAdapter().getItem(lv.getCheckedItemPosition());
            sharedPreferences.edit().putString(getString(R.string.pref_key_temp_units), chosenUnits)
                    .apply();
            dialogInterface.dismiss();
        });
        dialogBuilder.show();
    }

    @OnClick(R.id.sync_interval_button)
    public void showSyncIntervalDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setSingleChoiceItems(R.array.interval, 0, null)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                    ListView lv = ((AlertDialog)dialogInterface).getListView();
                    int chosenInterval = lv.getCheckedItemPosition();
                    sharedPreferences.edit().putString(getString(R.string.pref_key_sync_interval),
                            getResources().getStringArray(R.array.interval_values)[chosenInterval])
                            .apply();
                });
        dialogBuilder.show();
    }

}
