package com.example.kimichael.yamblz_forecast.views;

import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.data.network.places.response.Prediction;
import com.example.kimichael.yamblz_forecast.domain.interactor.requests.PlacesRequest;
import com.example.kimichael.yamblz_forecast.domain.interactor.settings.SettingsInteractor;
import com.example.kimichael.yamblz_forecast.presentation.presenter.settings.SuggestsPresenter;
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

import io.reactivex.Observable;
import io.reactivex.Single;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by Sinjvf on 30.07.2017.
 * check invocation of SuggestView methods when presenter call
 */

@RunWith(MockitoJUnitRunner.class)
public class SuggestsViewTest {
    @Mock
    private SuggestsFragment view;

    private SuggestsPresenter presenter;
    private SettingsInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.interactor = Mockito.mock(SettingsInteractor.class);
        List<Prediction> listPrediction = new ArrayList<>();
        listPrediction.add(new Prediction());
        Mockito.when(interactor.getPlaces(new PlacesRequest(anyString()))).thenReturn(Single.fromObservable(Observable.just(listPrediction)));
        presenter = new SuggestsPresenter(interactor);
        presenter.onAttach(view);
    }


    @Test
    public void checkSuccessResult() {
        presenter.getCitiesIds("Mos");
        verify(view).addList(anyList());
    }

    @Test
    public void addCityClicked() {
        presenter.addCityClicked();
        verify(view).showAddView();
    }

    @Test
    public void citySelected() {
        Mockito.when(interactor.getPlaceDetails(any()))
                .thenReturn(Single.fromObservable(Observable.just(new PlaceData())));
        presenter = new SuggestsPresenter(interactor);
        presenter.onAttach(view);
        presenter.citySelected(new Prediction());
        verify(view).clearAll();
    }

    @Test
    public void withoutView() {
        presenter.onDetach();
        presenter.addCityClicked();
        verify(view, never()).showAddView();

        presenter.getCitiesIds("Mos");
        verify(view, never()).addList(anyList());
    }

    @Test
    public void checkErrorResult() {
        Mockito.when(interactor.getPlaces(new PlacesRequest(anyString())))
                .thenReturn(Single.fromObservable(Observable.error(new Throwable("r"))));
        presenter = new SuggestsPresenter(interactor);
        presenter.getCitiesIds("Mos");
        verify(view, never()).addList(anyList());
    }

    @Test
    public void citySelectedDetach() {
        Mockito.when(interactor.getPlaceDetails(any()))
                .thenReturn(Single.fromObservable
                        (Observable.error(new Throwable("r"))));
        presenter = new SuggestsPresenter(interactor);
        presenter.onDetach();
        presenter.citySelected(new Prediction());
        verify(view, never()).clearAll();
    }



}
