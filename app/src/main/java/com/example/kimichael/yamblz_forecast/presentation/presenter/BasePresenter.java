package com.example.kimichael.yamblz_forecast.presentation.presenter;

import android.support.annotation.NonNull;

import com.example.kimichael.yamblz_forecast.presentation.view.BaseView;

/**
 * Created by Kim Michael on 16.07.17
 */
public abstract class BasePresenter<T extends BaseView> {

    private T view;

    public void onAttach(@NonNull T view) {
        this.view = view;
    }

    public void onDetach() {
        this.view = null;
    }

    public T getView() {
        return view;
    }
}
