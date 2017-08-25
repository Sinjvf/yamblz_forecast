package com.example.kimichael.yamblz_forecast.presentation.view.settings;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kimichael.yamblz_forecast.App;
import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.presentation.presenter.settings.SettingsPresenter;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sinjvf on 07.08.2017.
 * dialog for deleting city from db
 */

public class SureDialogFragment extends DialogFragment {
    @BindView(R.id.delete_ok)
    View deleteOk;
    @BindView(R.id.delete_cancel)
    View deleteCancel;

    @Inject
    SettingsPresenter presenter;
    private PlaceData data;
    private static final String DATA_KEY = "detaKey";


    public static SureDialogFragment getInstance(PlaceData data) {
        SureDialogFragment fgm = new SureDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DATA_KEY, data);
        fgm.setArguments(bundle);
        return fgm;
    }

    private void getData(){
        Bundle bundle = getArguments();
        if(bundle!=null) {
            data = bundle.getParcelable(DATA_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        App.getInstance().getSettingsScreenComponent().inject(this);
        getData();
        return inflater.inflate(R.layout.dialog_delete_city, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        getDialog().setTitle(getString(R.string.delete_title, data.getName()));
        RxView.clicks(deleteOk)
                .map(event -> new Pair<PlaceData, DialogFragment>(data, this))
                .subscribe(presenter::sureDeleteCity);
        RxView.clicks(deleteCancel)
                .map(event -> this)
                .subscribe(presenter::notSureDeleteCity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.getInstance().releaseForecastScreenComponent();
    }
}
