package com.example.kimichael.yamblz_forecast.presentation.view.main.tablet.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.kimichael.yamblz_forecast.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sinjvf on 12.08.2017.
 * Recycler holder. Shows city in 2-pane tablet layout
 */

public class CityHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.suggest_text)
    TextView textView;

    public CityHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(String text) {
        textView.setText(text);
    }
}
