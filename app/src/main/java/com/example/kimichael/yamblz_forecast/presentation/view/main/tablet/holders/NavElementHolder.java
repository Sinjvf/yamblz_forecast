package com.example.kimichael.yamblz_forecast.presentation.view.main.tablet.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kimichael.yamblz_forecast.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.kimichael.yamblz_forecast.presentation.view.main.tablet.TabletNavigationAdapter.ABOUT;
import static com.example.kimichael.yamblz_forecast.presentation.view.main.tablet.TabletNavigationAdapter.ADD_CITY;
import static com.example.kimichael.yamblz_forecast.presentation.view.main.tablet.TabletNavigationAdapter.SETTINGS;

/**
 * Created by Sinjvf on 12.08.2017.
 * holder for navigation elements in tablets. E.g. setting, about, add city
 */

public class NavElementHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.title)
    TextView titleView;
    @BindView(R.id.image)
    ImageView imageView;
    private int type;

    public NavElementHolder(View itemView, int type) {
        super(itemView);
        this.type = type;
        ButterKnife.bind(this, itemView);
    }

    public void bind() {
        int imageId = 0;
        int textId = 0;
        switch (type) {
            case ADD_CITY:
                imageId = R.drawable.ic_plus;
                textId = R.string.add_city;
                break;
            case SETTINGS:
                imageId = R.drawable.ic_menu_settings;
                textId = R.string.action_settings;
                break;
            case ABOUT:
                imageId = R.drawable.ic_menu_about;
                textId = R.string.action_about;
                break;
        }
        if (imageId != 0) {
            imageView.setImageResource(imageId);
        }
        if (textId != 0) {
            titleView.setText(textId);
        }
    }
}
