package com.example.kimichael.yamblz_forecast.presentation.view.settings;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.kimichael.yamblz_forecast.utils.PreferencesManager;
import com.example.kimichael.yamblz_forecast.utils.Utility;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;

import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Sinjvf on 23.07.2017.
 * Custom google places fragment
 */

public class GooglePlaceFragment extends SupportPlaceAutocompleteFragment {

    PublishSubject<Place> placeSelected = PublishSubject.create();
    Observer<Place> observer;

    public GooglePlaceFragment() {
        super();
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        this.setFilter(typeFilter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PreferencesManager manager  = new PreferencesManager(getContext());
        setText(manager.getPlace().getName());
    }

    public void setPlaceSelected(Observer<Place> observer) {
        this.observer = observer;
        placeSelected.subscribeWith(observer);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 30421) {
            if (resultCode == -1) {
                Place place = PlaceAutocomplete.getPlace(this.getActivity(), intent);
                if (this.placeSelected != null) {
                    placeSelected.onNext(place);
                }
            } else if (resultCode == 2) {
                Status var5 = PlaceAutocomplete.getStatus(this.getActivity(), intent);
                if (this.placeSelected != null) {
                    placeSelected.onError(new Throwable(var5.getStatusMessage()));
                    placeSelected = PublishSubject.create();
                    placeSelected.subscribeWith(observer);
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }


}
