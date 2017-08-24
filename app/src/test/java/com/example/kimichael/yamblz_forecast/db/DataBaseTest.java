package com.example.kimichael.yamblz_forecast.db;

import android.content.Context;

import com.example.kimichael.yamblz_forecast.data.common.ForecastInfo;
import com.example.kimichael.yamblz_forecast.data.common.ForecastInfoSQLiteTypeMapping;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.example.kimichael.yamblz_forecast.data.common.PlaceDataSQLiteTypeMapping;
import com.example.kimichael.yamblz_forecast.data.database.DbClientImpl;
import com.example.kimichael.yamblz_forecast.data.database.DbOpenHelper;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by Sinjvf on 13.08.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DataBaseTest {

    private StorIOSQLite storIOSQLite;
    private DbClientImpl dbClient;
    private List<ForecastInfo>list;
    private int cityId = 100500;
    private int humidity = 89;
    private DbOpenHelper helper ;



    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Context context = RuntimeEnvironment.application.getBaseContext();

        helper = new DbOpenHelper(context);

        this.storIOSQLite = DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(helper)
                .addTypeMapping(ForecastInfo.class, new ForecastInfoSQLiteTypeMapping())
                .addTypeMapping(PlaceData.class, new PlaceDataSQLiteTypeMapping())
                .build();
        list = new ArrayList<>();
        list.add(new ForecastInfo(null, cityId, 23, 45, 67, 500, 3, humidity, 344,
                "descr", Calendar.getInstance().getTimeInMillis(),
                Calendar.getInstance().getTimeInMillis()));
        dbClient = new DbClientImpl(storIOSQLite);
    }

    @Test
    public void saveGetForecast(){
        dbClient.saveForecast(list);
        PublishSubject<List<ForecastInfo>> sub = PublishSubject.create();
        sub.subscribeWith(new Observer<List<ForecastInfo>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull List<ForecastInfo> forecastInfos) {
                assertEquals(1, forecastInfos.size());
                assertEquals(humidity, forecastInfos.get(0).getHumidity(), 1);
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
        dbClient.getActualWeather(cityId, 10000, sub);

    }

    @Test
    public void addAndGetCities(){
        dbClient.addCity(new PlaceData(cityId, "M", 4, 5));
        PublishSubject<List<PlaceData>> sub = PublishSubject.create();
        sub.subscribe(new Observer<List<PlaceData>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<PlaceData> datas) {
                assertEquals(1, datas.size());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        dbClient.getAllCities(sub);
    }

    @Test
    public void deleteCity(){
        dbClient.deleteCity(new PlaceData(cityId, "M", 4, 5));
        PublishSubject<List<PlaceData>> sub = PublishSubject.create();

        sub.subscribe(new Observer<List<PlaceData>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<PlaceData> datas) {
                assertEquals(0, datas.size());
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
        dbClient.getAllCities(sub);
    }

    @Test
    public void deleteOldForecasts(){
        dbClient.delOldForecasts(0L);
        PublishSubject<List<ForecastInfo>> sub = PublishSubject.create();
        sub.subscribeWith(new Observer<List<ForecastInfo>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull List<ForecastInfo> forecastInfos) {
                assertEquals(0, forecastInfos.size());
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
        dbClient.getActualWeather(cityId, 10000, sub);
    }


}
