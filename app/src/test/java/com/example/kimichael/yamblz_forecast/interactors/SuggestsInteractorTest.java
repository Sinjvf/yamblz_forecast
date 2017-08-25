package com.example.kimichael.yamblz_forecast.interactors;

import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.data.network.places.response.Prediction;
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by Sinjvf on 30.07.2017.
 * check invocation of interactor methods when presenter call
 */

@RunWith(MockitoJUnitRunner.class)
public class SuggestsInteractorTest {
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
        Mockito.when(interactor.getPlaceDetails(any())).thenReturn(Single.fromObservable(Observable.just(new PlaceData(3, "name", 4, 5))));
        presenter = new SuggestsPresenter(interactor);
        presenter.onAttach(view);
    }

    @Test
    public void checkCitySelected() {
        presenter.citySelected(new Prediction());
        verify(interactor).addCity(any());
    }

}
