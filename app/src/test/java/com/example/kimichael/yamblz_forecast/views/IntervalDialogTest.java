package com.example.kimichael.yamblz_forecast.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.data.common.ForecastInfo;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.domain.interactor.forecast.ForecastInteractor;
import com.example.kimichael.yamblz_forecast.domain.interactor.requests.ForecastRequest;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.ForecastPresenter;
import com.example.kimichael.yamblz_forecast.presentation.presenter.settings.SettingsIntervalDialogPresenter;
import com.example.kimichael.yamblz_forecast.presentation.presenter.settings.SettingsUnitDialogPresenter;
import com.example.kimichael.yamblz_forecast.presentation.view.forecast.ForecastView;
import com.example.kimichael.yamblz_forecast.presentation.view.settings.dialogs.select.IntervalsDialogFragment;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;
import com.example.kimichael.yamblz_forecast.utils.Utility;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Sinjvf on 30.07.2017.
 * check invocation of ForecastView methods when presenter call
 */

@RunWith(MockitoJUnitRunner.class)
public class IntervalDialogTest {
    @Mock
    private IntervalsDialogFragment view;

    private SettingsIntervalDialogPresenter presenter;
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

        presenter = new SettingsIntervalDialogPresenter(manager);
        presenter.onAttach(view);
        Utility utility = new Utility();
    }


    @Test
    public void checkGetCurrent() {
        presenter.getCurrent();
        verify(view).checkPosition( 0);
    }

    @Test
    public void saveUnit() {
        PreferencesManager manager = Mockito.mock(PreferencesManager.class);
        presenter = new SettingsIntervalDialogPresenter(manager);
        presenter.saveLastInterval(3);
        verify(manager).saveInterval(3);
    }

    @Test
    public void checkGetCurrentDetach() {
        presenter.onDetach();
        presenter.getCurrent();

        //on attach invokes it once
        verify(view, times(1)).checkPosition( 0);
    }

}
