package com.example.kimichael.yamblz_forecast.presentation.view.settings.dialogs.select;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;

import com.example.kimichael.yamblz_forecast.App;
import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.presentation.presenter.settings.SettingsUnitDialogPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Sinjvf on 05.08.2017.
 * dialog of temp unit choosing
 */

public class UnitsDialogFragment extends SelectorDialogFragment {

    @Inject
    SettingsUnitDialogPresenter presenter;

    public static SelectorDialogFragment getInstance(int position) {
        UnitsDialogFragment fgm = new UnitsDialogFragment();
        fgm.setArguments(getBundle(position));
        return fgm;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_units, container, false);
        App.getInstance().getSettingsScreenComponent().inject(this);
        unbinder = ButterKnife.bind(this, v);
        try {
            getDialog().getWindow().setTitle(getString(R.string.pref_temp_units_title));
        } catch (NullPointerException e) {
            Timber.e(e.getMessage());
        }
        initIds();
        presenter.onAttach(this);
        return v;
    }

    @Override
    protected void initIds() {
        ids = new int [2];
        ids[0] = R.id.celsius;
        ids[1] = R.id.fahrenheit;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
    }

    @Override
    public void saveByPos(int position) {
        presenter.saveLastUnit(position);
    }
}
