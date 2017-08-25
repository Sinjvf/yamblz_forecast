package com.example.kimichael.yamblz_forecast.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.presentation.presenter.settings.SettingsIntervalDialogPresenter;
import com.example.kimichael.yamblz_forecast.presentation.presenter.settings.SettingsUnitDialogPresenter;
import com.example.kimichael.yamblz_forecast.presentation.view.settings.dialogs.select.IntervalsDialogFragment;
import com.example.kimichael.yamblz_forecast.presentation.view.settings.dialogs.select.UnitsDialogFragment;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Sinjvf on 30.07.2017.
 * check invocation of ForecastView methods when presenter call
 */

@RunWith(MockitoJUnitRunner.class)
public class UnitDialogTest {
    @Mock
    private UnitsDialogFragment view;

    private SettingsUnitDialogPresenter presenter;
    private Context context;
    private Resources res;
    private PreferencesManager manager;
    private SharedPreferences sharedPrefs;
    int intervals[] = {3600, 7200, 10800, 18000, 86400};

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.sharedPrefs = Mockito.mock(SharedPreferences.class);
        this.context = Mockito.mock(Context.class);
        this.res = Mockito.mock(Resources.class);
        Mockito.when(res.getIntArray(R.array.interval_values)).thenReturn(intervals);
        Mockito.when(sharedPrefs.getInt("sync_interval_pos", 0)).thenReturn(0);
        Mockito.when(context.getResources()).thenReturn(res);
        manager = new PreferencesManager(context, sharedPrefs);

        presenter = new SettingsUnitDialogPresenter(manager);
        presenter.onAttach(view);
    }


    @Test
    public void checkGetCurrent() {
        presenter.getCurrent();
        verify(view).checkPosition( 0);
    }

    @Test
    public void saveUnit() {
        PreferencesManager manager = Mockito.mock(PreferencesManager.class);
        presenter = new SettingsUnitDialogPresenter(manager);
        presenter.saveLastUnit(1);
        verify(manager).saveTempUnit(1);
    }

    @Test
    public void checkGetCurrent1() {
        PreferencesManager manager = Mockito.mock(PreferencesManager.class);
        Mockito.when(manager.isTempCelsius()).thenReturn(false);
        presenter = new SettingsUnitDialogPresenter(manager);
        assertEquals(1, presenter.getCurrent());
    }

    @Test
    public void checkGetCurrentDetach() {
        presenter.onDetach();
        presenter.getCurrent();
        //on attach invokes it
        verify(view, times(1)).checkPosition( 0);
    }
}
