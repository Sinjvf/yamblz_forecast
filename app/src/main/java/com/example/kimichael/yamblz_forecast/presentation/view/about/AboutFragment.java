package com.example.kimichael.yamblz_forecast.presentation.view.about;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.presentation.view.ToolbarOwner;

public class AboutFragment extends Fragment {

    public AboutFragment() {}


    public static AboutFragment getInstance() {
        AboutFragment fragment = new AboutFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ToolbarOwner) getActivity()).setToolbarText(getString(R.string.action_about));
        ((ToolbarOwner) getActivity()).lockDrawer(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((ToolbarOwner) getActivity()).lockDrawer(false);
    }
}
