package com.example.kimichael.yamblz_forecast.presentation.view.places;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.data.network.places.response.Prediction;
import com.example.kimichael.yamblz_forecast.presentation.presenter.settings.SuggestsPresenter;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sinjvf on 08.08.2017.
 * holder for cities suggests
 */

class SuggestsHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.suggest_text)
    TextView suggestText;

    private View rootView;

    private static final int EVEN_TYPE = 0;
    private SuggestsPresenter presenter;

    SuggestsHolder(View itemView, int type, SuggestsPresenter presenter) {
        super(itemView);
        rootView = itemView;
        this.presenter = presenter;
        ButterKnife.bind(this, itemView);

    }

    public void bind(Prediction prediction) {
        suggestText.setText(prediction.getDescription());
        RxView.clicks(rootView).map(obj->prediction).subscribe(presenter::citySelected);
    }
}
