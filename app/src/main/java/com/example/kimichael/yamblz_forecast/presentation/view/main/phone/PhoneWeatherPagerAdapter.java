package com.example.kimichael.yamblz_forecast.presentation.view.main.phone;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.kimichael.yamblz_forecast.presentation.view.forecast.BaseForecastFragment;

import java.util.List;

/**
 * Created by Sinjvf on 10.08.2017.
 * Adapter for View pager in main weather screen
 */

public class PhoneWeatherPagerAdapter extends FragmentStatePagerAdapter {
    private List<BaseForecastFragment> fragments;

    public PhoneWeatherPagerAdapter(FragmentManager fm, List<BaseForecastFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public int onScreen(int pos){
        fragments.get(pos).onScreen();
        return pos;
    }
}

