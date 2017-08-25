package com.example.kimichael.yamblz_forecast.presentation.view.main.tablet;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.TabletWeatherPresenter;
import com.example.kimichael.yamblz_forecast.presentation.view.forecast.holders.HeaderHolder;
import com.example.kimichael.yamblz_forecast.presentation.view.forecast.holders.ItemHolder;
import com.example.kimichael.yamblz_forecast.presentation.view.main.tablet.holders.CityHolder;
import com.example.kimichael.yamblz_forecast.presentation.view.main.tablet.holders.NavElementHolder;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sinjvf on 12.08.2017.
 * Adapter for navigation recycler
 */

public class TabletNavigationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PlaceData> data = new ArrayList<>();

    public static final int CITY = 0;
    public static final int ADD_CITY = 1;
    public static final int SETTINGS = 2;
    public static final int ABOUT = 3;
    private TabletWeatherPresenter presenter;

    public TabletNavigationAdapter(TabletWeatherPresenter presenter) {
        this.presenter = presenter;
    }

    public void setData(List<PlaceData> data) {
        if (data == null) {
            this.data = new ArrayList<>();
            return;
        }
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case CITY:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suggest, parent, false);
                return new CityHolder(v);
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tablet_navigation, parent, false);
                return new NavElementHolder(v, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < data.size()) {
            ((CityHolder) holder).bind(data.get(position).getName());
        } else {
            ((NavElementHolder) holder).bind();
        }
        RxView.clicks(holder.itemView).map(event -> position).subscribe(presenter::setItemPos);
    }


    @Override
    public int getItemViewType(int position) {
        switch (position - data.size()) {
            case 0:
                return ADD_CITY;
            case 1:
                return SETTINGS;
            case 2:
                return ABOUT;
            default:
                return CITY;
        }
    }

    @Override
    public int getItemCount() {
        return  data.size() + 3;
    }
}
