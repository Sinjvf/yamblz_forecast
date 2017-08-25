package com.example.kimichael.yamblz_forecast.presentation.view.main.phone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kimichael.yamblz_forecast.App;
import com.example.kimichael.yamblz_forecast.R;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.presentation.presenter.forecast.PhoneWeatherPresenter;
import com.example.kimichael.yamblz_forecast.presentation.view.ToolbarOwner;
import com.example.kimichael.yamblz_forecast.presentation.view.forecast.BaseForecastFragment;
import com.example.kimichael.yamblz_forecast.presentation.view.forecast.ForecastFragment;
import com.example.kimichael.yamblz_forecast.presentation.view.places.SuggestsFragment;
import com.jakewharton.rxbinding2.support.v4.view.RxViewPager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by Sinjvf on 08.08.2017.
 * main screen fragment
 */

public class PhoneWeatherFragment extends Fragment implements MainWeatherView {
  /*  @BindView(R.id.recycler)
    RecyclerView recycler;*/

    @BindView(R.id.view_pager)
    ViewPager pager;
    @Inject
    PhoneWeatherPresenter<MainWeatherView> presenter;
    private Disposable dispose;

    public static PhoneWeatherFragment getInstance() {
        return new PhoneWeatherFragment();
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
        View v = inflater.inflate(R.layout.fragment_phone_weather, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getCities();
    }

    @Override
    public void updateCitiesList(List<PlaceData> dataList, int currentPos) {
        if (dispose!=null ) dispose.dispose();
        List<BaseForecastFragment> fragments = new ArrayList<>();
        for(PlaceData data:dataList) {
            fragments.add(ForecastFragment.getInstance(data));
        }
        fragments.add(SuggestsFragment.getInstance());
        PhoneWeatherPagerAdapter adapter = new PhoneWeatherPagerAdapter(getActivity().getSupportFragmentManager(), fragments);

        pager.setAdapter(adapter);
        //show city name on the toolbar
        RxViewPager.pageSelections(pager)
                .map(presenter::setItemPos)
                .map(adapter::onScreen)
                .map(i ->
                (i < dataList.size())
                        ? dataList.get(i).getName()
                        : getString(R.string.new_city))
                .subscribeWith(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        dispose = d;
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        changePage(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        writeError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        pager.setCurrentItem(currentPos);
    }

    private void writeError(Throwable e){
        Timber.e("writeError: "+e.getMessage() );
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
