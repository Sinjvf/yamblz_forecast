package com.example.kimichael.yamblz_forecast.presentation.view.main.tablet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kimichael.yamblz_forecast.App;
import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.PhoneWeatherPresenter;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.TabletWeatherPresenter;
import com.example.kimichael.yamblz_forecast.presentation.view.ToolbarOwner;
import com.example.kimichael.yamblz_forecast.presentation.view.about.AboutFragment;
import com.example.kimichael.yamblz_forecast.presentation.view.forecast.ForecastFragment;
import com.example.kimichael.yamblz_forecast.presentation.view.main.phone.MainWeatherView;
import com.example.kimichael.yamblz_forecast.presentation.view.main.phone.PhoneWeatherFragment;
import com.example.kimichael.yamblz_forecast.presentation.view.places.SuggestsFragment;
import com.example.kimichael.yamblz_forecast.presentation.view.settings.SettingsFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

import static com.example.kimichael.yamblz_forecast.presentation.view.main.tablet.TabletNavigationAdapter.ABOUT;
import static com.example.kimichael.yamblz_forecast.presentation.view.main.tablet.TabletNavigationAdapter.ADD_CITY;
import static com.example.kimichael.yamblz_forecast.presentation.view.main.tablet.TabletNavigationAdapter.CITY;
import static com.example.kimichael.yamblz_forecast.presentation.view.main.tablet.TabletNavigationAdapter.SETTINGS;

/**
 * Created by Sinjvf on 12.08.2017.
 * Main screen for tablets
 */

public class TabletWeatherFragment extends Fragment implements TabletWeatherView {
    @BindView(R.id.recycler_view)
    RecyclerView recycler;
    @Inject
    TabletWeatherPresenter presenter;
    private TabletNavigationAdapter adapter;

    public static TabletWeatherFragment getInstance() {
        return new TabletWeatherFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().getSettingsScreenComponent().inject(this);
        presenter.onAttach(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tablet_weather, container, false);
        ButterKnife.bind(this, v);
        initRecycler();
        return v;
    }


    private void initRecycler() {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(manager);
        adapter = new TabletNavigationAdapter(presenter);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getCities();
    }

    @Override
    public void updateCitiesList(List<PlaceData> dataList, int currentPos) {
        adapter.setData(dataList);
        presenter.setCurrentList(dataList);
        presenter.setItemPos(currentPos);
    }

    @Override
    public void changeFragment(int fragmentType, PlaceData data) {
        Fragment fragment;
        switch (fragmentType) {
            case SETTINGS:
                fragment = SettingsFragment.getInstance();
                break;
            case ABOUT:
                fragment = AboutFragment.getInstance();
                break;
            case ADD_CITY:
                fragment = SuggestsFragment.getInstance();
                break;
            default:
                if (data == null) return;
                fragment = ForecastFragment.getInstance(data);
                changePage(data.getName());
                ((ForecastFragment) fragment).onScreen();
        }

        getActivity().getSupportFragmentManager()
                .beginTransaction().replace(R.id.cities_content, fragment, null).commit();

    }


    private void changePage(String title) {
        ((ToolbarOwner) getActivity()).setToolbarText(title);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
