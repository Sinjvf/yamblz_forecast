package com.example.kimichael.yamblz_forecast.interactors;

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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by Sinjvf on 30.07.2017.
 * check invocation of SettingsInteractor methods when presenter call
 */

@RunWith(MockitoJUnitRunner.class)
public class SettingsInteractorTest {
    @Mock
    private SettingsInteractor interactor;

    private SettingsPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new SettingsPresenter(interactor);
    }

    @Test
    public void checkDeleteCity() {
        presenter.sureDeleteCity(new Pair<>(new PlaceData(2, "name", 1,2), null));
        verify(interactor).deleteCity(any());
    }

}
