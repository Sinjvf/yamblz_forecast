package com.example.kimichael.yamblz_forecast.presentation.view.settings;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kimichael.yamblz_forecast.App;
import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.domain.service.forecast.ForecastJobService;
import com.example.kimichael.yamblz_forecast.presentation.presenter.settings.SettingsPresenter;
import com.example.kimichael.yamblz_forecast.presentation.view.ToolbarOwner;
import com.example.kimichael.yamblz_forecast.presentation.view.settings.dialogs.select.IntervalsDialogFragment;
import com.example.kimichael.yamblz_forecast.presentation.view.settings.dialogs.select.SelectorDialogFragment;
import com.example.kimichael.yamblz_forecast.presentation.view.places.SuggestsFragment;
import com.example.kimichael.yamblz_forecast.presentation.view.settings.dialogs.select.UnitsDialogFragment;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SettingsFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    PreferencesManager manager;
    @BindView(R.id.temp_units_button)
    TextView tempUnitsButton;
    @BindView(R.id.sync_interval_button)
    TextView syncIntervalButton;

    @Inject
    SettingsPresenter presenter;

    Unbinder unbinder;

    public SettingsFragment() {
    }

    public static SettingsFragment getInstance() {
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
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ToolbarOwner) getActivity()).setToolbarText(getString(R.string.action_settings));
        ((ToolbarOwner) getActivity()).lockDrawer(true);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        ((ToolbarOwner) getActivity()).lockDrawer(false);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (manager.isIntervalChanged(s)) {
            int interval = manager.getInterval();
            ForecastJobService.scheduleSync(getContext(), interval);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onDestroy() {
        unbinder.unbind();
        App.getInstance().releaseSettingsScreenComponent();
        super.onDestroy();
    }

    @OnClick(R.id.temp_units_button)
    public void showTempUnitsDialog() {
        SelectorDialogFragment dialogFragment = UnitsDialogFragment.getInstance(manager.getTempPosition());
        dialogFragment.show(getActivity().getFragmentManager(), null);
    }

    @OnClick(R.id.sync_interval_button)
    public void showSyncIntervalDialog() {
        SelectorDialogFragment dialogFragment = IntervalsDialogFragment.getInstance(manager.getTempPosition());
        dialogFragment.show(getActivity().getFragmentManager(), null);

    }

}
