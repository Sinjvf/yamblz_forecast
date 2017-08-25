package com.example.kimichael.yamblz_forecast.views;

import android.support.v7.widget.LinearLayoutManager;

import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.domain.interactor.settings.SettingsInteractor;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.PhoneWeatherPresenter;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.TabletWeatherPresenter;
import com.example.kimichael.yamblz_forecast.presentation.view.main.phone.MainWeatherView;
import com.example.kimichael.yamblz_forecast.presentation.view.main.tablet.TabletWeatherFragment;
import com.example.kimichael.yamblz_forecast.presentation.view.places.SuggestsFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by Sinjvf on 13.08.2017.
 * check invocation of TabletWeatherView methods when presenter call
 */

@RunWith(MockitoJUnitRunner.class)
public class TabletWeatherViewTest {
    @Mock
    private TabletWeatherFragment view;

    private TabletWeatherPresenter presenter;
    private SettingsInteractor interactor;
    private Scheduler scheduler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.interactor = Mockito.mock(SettingsInteractor.class);
        this.scheduler = Mockito.mock(Scheduler.class);
        presenter = new TabletWeatherPresenter(interactor,scheduler);
        presenter.onAttach(view);
        presenter.setCurrentList(new ArrayList<>());
    }

    @Test
    public void checkAddCity() {
        presenter.setItemPos(0);
        verify(view).changeFragment(1, null);
    }
    @Test
    public void checkSettings() {
        presenter.setItemPos(1);
        verify(view).changeFragment(2, null);
    }

    @Test
    public void checkAbout() {
        presenter.setItemPos(2);
        verify(view).changeFragment(3, null);
    }

    @Test
    public void checkToCity() {
        List<PlaceData> list = new ArrayList<>();
        PlaceData data = new PlaceData(4, "name", 4, 5);
        list.add(data);
        presenter.setCurrentList(list);
        presenter.setItemPos(0);
        verify(view).changeFragment(0, data);
    }


    @Test
    public void checkAddCityDetach() {
        presenter.onDetach();
        presenter.setItemPos(0);
        verify(view, never()).changeFragment(1, null);
    }
    @Test
    public void checkSettingsDetach() {
        presenter.onDetach();
        presenter.setItemPos(1);
        verify(view, never()).changeFragment(2, null);
    }

    @Test
    public void checkAboutDetach() {
        presenter.onDetach();
        presenter.setItemPos(2);
        verify(view, never()).changeFragment(3, null);
    }

    @Test
    public void checkToCityDetach() {
        presenter.onDetach();
        List<PlaceData> list = new ArrayList<>();
        PlaceData data = new PlaceData(4, "name", 4, 5);
        list.add(data);
        presenter.setCurrentList(list);
        presenter.setItemPos(0);
        verify(view, never()).changeFragment(0, data);
    }
}
