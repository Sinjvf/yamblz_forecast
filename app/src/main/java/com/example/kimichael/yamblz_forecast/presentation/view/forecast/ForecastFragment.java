package com.example.kimichael.yamblz_forecast.presentation.view.forecast;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kimichael.yamblz_forecast.App;
import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.data.common.ForecastInfo;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.ForecastPresenter;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.presentation.view.ToolbarOwner;
import com.example.kimichael.yamblz_forecast.presentation.view.settings.SureDialogFragment;


import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class ForecastFragment extends BaseForecastFragment implements ForecastView {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.fragment_weather)
    View content;

    @Inject
    ForecastPresenter forecastPresenter;
    private ForecastAdapter adapter;
    private final static String DATA_KEY = "DataKey";
    private PlaceData data;
    private PublishSubject<Object> subjectViewCreated = PublishSubject.create();
    private PublishSubject<Object> subjectViewOnScreen = PublishSubject.create();

    public static ForecastFragment getInstance(PlaceData data) {

        ForecastFragment fragment = new ForecastFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DATA_KEY, data);
        fragment.setArguments(bundle);
        return fragment;
    }

    public PlaceData getData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            data = bundle.getParcelable(DATA_KEY);
        }
        return data;
    }

    public ForecastFragment() {
        Observable.combineLatest(subjectViewCreated, subjectViewOnScreen,
                (a, b) -> false)
                .subscribe(this::getForecast, this::errorHandling);
    }

    private void errorHandling(Throwable e) {
        Timber.e("errorHandling: " + e.getMessage());
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getInstance().getForecastScreenComponent().inject(this);
        getData();
        forecastPresenter.setData(data);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        forecastPresenter.onAttach(this);
        initRecycler();
        subjectViewCreated.onNext(new Object());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            forecastPresenter.getForecast(true);
            return true;
        }
        if (id == R.id.action_delete) {
            forecastPresenter.showSureDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        forecastPresenter.onDetach();
        App.getInstance().releaseForecastScreenComponent();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        setHasOptionsMenu(true);
        return v;
    }

    private void initRecycler() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ForecastAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        ((ToolbarOwner) getActivity()).clearMenu();
        inflater.inflate(R.menu.forecast_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void showForecast(List<ForecastInfo> forecastsList, int colorId) {
        if (adapter != null) adapter.setList(forecastsList);
        int color = ContextCompat.getColor(getContext(), colorId);
        content.setBackgroundColor(color);

    }

    @Override
    public PlaceData getPlace() {
        return data;
    }

    @Override
    public void showSureDialog() {
        SureDialogFragment dialogFragment = SureDialogFragment.getInstance(data);
        dialogFragment.show(getActivity().getFragmentManager(), null);
    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(getContext(), getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress(boolean show) {
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onScreen() {
        subjectViewOnScreen.onNext(new Object());
    }

    public void getForecast(Boolean bool) {
        forecastPresenter.getForecast(bool);
    }
}
