package com.example.kimichael.yamblz_forecast.presentation.view.forecast.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.kimichael.yamblz_forecast.data.common.ForecastInfo;

/**
 * Created by Sinjvf on 06.08.2017.
 * super class for weather screen holders
 */

public abstract class BaseHolder extends RecyclerView.ViewHolder{
    BaseHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(ForecastInfo info);
}
