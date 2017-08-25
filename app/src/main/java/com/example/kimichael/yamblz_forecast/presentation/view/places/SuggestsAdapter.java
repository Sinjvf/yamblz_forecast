package com.example.kimichael.yamblz_forecast.presentation.view.places;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.data.network.places.response.Prediction;
import com.example.kimichael.yamblz_forecast.presentation.presenter.settings.SuggestsPresenter;
import com.example.kimichael.yamblz_forecast.presentation.view.forecast.holders.HeaderHolder;

import java.util.List;

/**
 * Created by Sinjvf on 08.08.2017.
 * adapter for cities suggests
 */

class SuggestsAdapter extends RecyclerView.Adapter<SuggestsHolder> {
    private List<Prediction> list;
    private SuggestsPresenter presenter;

    SuggestsAdapter(SuggestsPresenter presenter) {
        this.presenter = presenter;
    }

    public void setList(List<Prediction> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public SuggestsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suggest, parent, false);
        return new SuggestsHolder(v, viewType, presenter);
    }


    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public void onBindViewHolder(SuggestsHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return (list == null) ? 0 : list.size();
    }
}
