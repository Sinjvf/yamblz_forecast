package com.example.kimichael.yamblz_forecast.presentation.view.settings.dialogs.select;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.kimichael.yamblz_forecast.App;
import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.presentation.presenter.settings.SettingsIntervalDialogPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by Sinjvf on 05.08.2017.
 * dialog of interval choosing
 */

public class IntervalsDialogFragment extends SelectorDialogFragment {

    private static final String POSITION = "position";
    @Inject
    SettingsIntervalDialogPresenter presenter;

    public static IntervalsDialogFragment getInstance(int position) {
        IntervalsDialogFragment fgm = new IntervalsDialogFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        fgm.setArguments(args);
        return fgm;
    }

    @Override
    protected void initIds() {
        ids = new int [5];
        ids[0] = R.id.time_1;
        ids[1] = R.id.time_2;
        ids[2] = R.id.time_3;
        ids[3] = R.id.time_5;
        ids[4] = R.id.time_day;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_intervals, container, false);
        App.getInstance().getSettingsScreenComponent().inject(this);
        unbinder = ButterKnife.bind(this, v);
        try {
            getDialog().getWindow().setTitle(getString(R.string.pref_sync_title));
        } catch (NullPointerException e) {
            Timber.e(e.getMessage());
        }
        initIds();
        presenter.onAttach(this);
        return v;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
    }

    @Override
    public void saveByPos(int position) {
        presenter.saveLastInterval(position);
    }
}
