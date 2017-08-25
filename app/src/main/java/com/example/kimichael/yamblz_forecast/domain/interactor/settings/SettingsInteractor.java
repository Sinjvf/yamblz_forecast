package com.example.kimichael.yamblz_forecast.domain.interactor.settings;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.kimichael.yamblz_forecast.data.database.DbClientImpl;
import com.example.kimichael.yamblz_forecast.data.network.places.PlacesRepository;
import com.example.kimichael.yamblz_forecast.data.network.places.response.PlacesResponse;
import com.example.kimichael.yamblz_forecast.data.network.places.response.Prediction;
import com.example.kimichael.yamblz_forecast.domain.interactor.SingleInteractor;
import com.example.kimichael.yamblz_forecast.domain.interactor.requests.PlacesRequest;
import com.example.kimichael.yamblz_forecast.presentation.di.module.SchedulersModule;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;

/**
 * Created on 22.07.2017.
 */
public class SettingsInteractor extends SingleInteractor {
    private final PlacesRepository placesRepository;
    private final Context context;

    private final PreferencesManager manager;

    private final DbClientImpl dbClient;

    @Inject
    public SettingsInteractor(@Named(SchedulersModule.JOB) Scheduler threadExecutor,
                       @Named(SchedulersModule.UI) Scheduler postExecutionThread, PlacesRepository placesRepository,
                       Context context, PreferencesManager manager, DbClientImpl dbClient) {
        super(threadExecutor, postExecutionThread);
        this.placesRepository = placesRepository;
        this.context = context;
        this.manager = manager;
        this.dbClient = dbClient;
    }

    public BuildUseCaseObservable<List<Prediction>, PlacesRequest> getPlacesBuild(PlacesRequest param){
         return params1 -> placesRepository.getPlaces(param)
                        .map(PlacesResponse::getPredictions);
    }

    public BuildUseCaseObservable<PlaceData, Prediction> getPlacesDetailsBuild(Prediction param){
        return  params1 -> placesRepository.getLocale(param.getPlaceId())
                        .map(detail->
                                PlaceData.newPlace(detail.getResult().getName(),
                                        detail.getResult().getGeometry().getLocation().getLat(),
                                        detail.getResult().getGeometry().getLocation().getLng())
                        );
    }

    public Single<List<Prediction>> getPlaces(PlacesRequest param) {
         return execute(getPlacesBuild(param), param);
    }

    public Single<PlaceData> getPlaceDetails(Prediction param) {
        return execute(getPlacesDetailsBuild(param), param);
    }

    public void addCity(@NonNull PlaceData data){
        dbClient.addCity(data);
    }

    public void deleteCity(PlaceData data){
        dbClient.deleteCity(data);
    }

    public void getAllCities(PublishSubject<List<PlaceData>> data){
        dbClient.getAllCities(data);
    }
}
