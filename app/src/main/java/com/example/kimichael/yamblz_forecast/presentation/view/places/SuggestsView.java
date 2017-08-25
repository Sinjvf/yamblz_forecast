package com.example.kimichael.yamblz_forecast.presentation.view.places;

import com.example.kimichael.yamblz_forecast.data.network.places.response.Prediction;
import com.example.kimichael.yamblz_forecast.presentation.view.BaseView;

import java.util.List;

/**
 * Created by Sinjvf on 06.08.2017.
 * view for suggest fragment
 */

public interface SuggestsView extends BaseView {
    void addList(List<Prediction> list);
    void clearAll();
    void showAddView();
}
