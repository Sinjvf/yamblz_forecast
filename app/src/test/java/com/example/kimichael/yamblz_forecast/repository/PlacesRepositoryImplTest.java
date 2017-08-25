package com.example.kimichael.yamblz_forecast.repository;

import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.data.network.forecast.ForecastRepositoryImpl;
import com.example.kimichael.yamblz_forecast.data.network.forecast.OpenWeatherClient;
import com.example.kimichael.yamblz_forecast.data.network.forecast.response.ForecastResponse;
import com.example.kimichael.yamblz_forecast.data.network.places.GooglePlacesClient;
import com.example.kimichael.yamblz_forecast.data.network.places.PlacesRepositoryImpl;
import com.example.kimichael.yamblz_forecast.domain.interactor.requests.ForecastRequest;
import com.example.kimichael.yamblz_forecast.domain.interactor.requests.PlacesRequest;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Single;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by Sinjvf on 13.08.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class PlacesRepositoryImplTest {
    @Mock
    private GooglePlacesClient placesClient;
    private PlacesRepositoryImpl repository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        repository = new PlacesRepositoryImpl(placesClient);
    }

    @Test
    public void getPlaces() {
        repository.getPlaces(new PlacesRequest("mos"));
        verify(placesClient).getPlaces(any(), any());
    }

    @Test
    public void getLocale() {
        repository.getLocale("rew");
        verify(placesClient).getPlaceDetail(any(), any());
    }

}
