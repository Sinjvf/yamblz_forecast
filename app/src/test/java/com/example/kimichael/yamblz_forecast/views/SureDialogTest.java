package com.example.kimichael.yamblz_forecast.views;


import android.app.DialogFragment;
import android.support.v4.util.Pair;

import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.domain.interactor.settings.SettingsInteractor;
import com.example.kimichael.yamblz_forecast.presentation.presenter.settings.SettingsPresenter;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by Sinjvf on 30.07.2017.
 * check invocation of ForecastView methods when presenter call
 */

@RunWith(MockitoJUnitRunner.class)
public class SureDialogTest {
    @Mock
    private DialogFragment data;
    @Mock
    private SettingsInteractor interactor;

    private SettingsPresenter presenter;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new SettingsPresenter(interactor);
    }

    @Test
    public void dontDelete() {
        presenter.notSureDeleteCity(data);
        verify(data).dismiss();
    }

    @Test
    public void dontDeleteNull() {
        presenter.notSureDeleteCity(null);
        verify(data, never()).dismiss();
    }

    @Test
    public void delete() {
        presenter.sureDeleteCity(new Pair<>(new PlaceData(3, "", 6, 7), data));
        verify(data).dismiss();
    }
}
