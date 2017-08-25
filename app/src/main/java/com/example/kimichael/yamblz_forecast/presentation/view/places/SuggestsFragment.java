package com.example.kimichael.yamblz_forecast.presentation.view.places;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kimichael.yamblz_forecast.App;
import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.data.network.places.response.Prediction;
import com.example.kimichael.yamblz_forecast.presentation.presenter.settings.SuggestsPresenter;
import com.example.kimichael.yamblz_forecast.presentation.view.ToolbarOwner;
import com.example.kimichael.yamblz_forecast.presentation.view.forecast.BaseForecastFragment;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;


import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by Sinjvf on 05.08.2017.
 * fragment for city choosing
 */

public class SuggestsFragment extends BaseForecastFragment implements SuggestsView {
    @BindView(R.id.city_input)
    SearchView searchView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.suggest_container)
    ViewGroup suggestContainer;
    @BindView(R.id.add_place_container)
    ViewGroup addPlaceContainer;


    private Unbinder unbinder;
    @Inject
    SuggestsPresenter presenter;
    private SuggestsAdapter adapter;

    public static SuggestsFragment getInstance() {
        return new SuggestsFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_city, container, false);
        unbinder = ButterKnife.bind(this, v);
        App.getInstance().getSettingsScreenComponent().inject(this);
        presenter.onAttach(this);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecycler();
        addList(null);
        RxSearchView.queryTextChangeEvents(searchView)
                .skip(1)
                .debounce(400, TimeUnit.MILLISECONDS)
                .map(event -> event.queryText().toString())
                .filter(s -> s.length() > 2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        presenter.getCitiesIds(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        App.getInstance().releaseForecastScreenComponent();
        unbinder.unbind();
        presenter.onDetach();
    }

    private void initRecycler(){
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        adapter = new SuggestsAdapter(presenter);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void addList(List<Prediction> list) {
        if (list == null) return;
        adapter.setList(list);
    }

    @OnClick(R.id.add_place_container)
    public void addCityClicked(){
        presenter.addCityClicked();
    }

    @Override
    public void clearAll() {
        searchView.setQuery("", false);
        suggestContainer.setVisibility(View.GONE);
        addPlaceContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAddView() {
        suggestContainer.setVisibility(View.VISIBLE);
        addPlaceContainer.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();

        ((ToolbarOwner) getActivity()).setToolbarText(getString(R.string.new_city));
        ((ToolbarOwner) getActivity()).lockDrawer(true);
    }
}
