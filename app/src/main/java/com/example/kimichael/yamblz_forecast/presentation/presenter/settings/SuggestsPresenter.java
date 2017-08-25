package com.example.kimichael.yamblz_forecast.presentation.presenter.settings;

import com.example.kimichael.yamblz_forecast.data.network.places.response.Prediction;
import com.example.kimichael.yamblz_forecast.domain.interactor.requests.PlacesRequest;
import com.example.kimichael.yamblz_forecast.domain.interactor.settings.SettingsInteractor;
import com.example.kimichael.yamblz_forecast.presentation.presenter.BasePresenter;
import com.example.kimichael.yamblz_forecast.presentation.view.places.SuggestsView;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created on 22.07.2017.
 */
public class SuggestsPresenter extends BasePresenter<SuggestsView> {

    private SettingsInteractor settingsInteractor;


    public SuggestsPresenter(SettingsInteractor settingsInteractor) {
        this.settingsInteractor = settingsInteractor;

    }

    @Inject
    public void getCitiesIds(String queue) {
        settingsInteractor.getPlaces(new PlacesRequest(queue))
                .subscribe(getTextChangeObserver());
    }

    private SingleObserver<List<Prediction>> getTextChangeObserver() {
        return new SingleObserver<List<Prediction>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull List<Prediction> placesResponse) {
                Timber.d("onNext: " + placesResponse.toString());
                if (getView() != null) getView().addList(placesResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.e(e.getMessage());
            }

        };
    }

    public void citySelected(Prediction prediction) {
        settingsInteractor.getPlaceDetails(prediction)
                .subscribe(getPlaceDetailObserver());
        if (getView() != null) getView().clearAll();
    }

    private SingleObserver<PlaceData> getPlaceDetailObserver() {
        return new SingleObserver<PlaceData>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull PlaceData placesResponse) {
                Timber.d("onNext: " + placesResponse.toString());
                settingsInteractor.addCity(placesResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.e(e.getMessage());
            }

        };
    }

    public void addCityClicked() {
        if (getView() != null) getView().showAddView();
    }
}
