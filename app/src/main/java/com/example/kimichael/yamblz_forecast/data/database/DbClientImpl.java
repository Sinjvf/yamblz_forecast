package com.example.kimichael.yamblz_forecast.data.database;


import com.example.kimichael.yamblz_forecast.data.common.ForecastInfo;
import com.example.kimichael.yamblz_forecast.data.common.PlaceData;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.subjects.PublishSubject;
import rx.Observer;


/**
 * Created by Sinjvf on 08.08.2017.
 * Class for Db operations
 */


public class DbClientImpl implements DbClient {

    private StorIOSQLite storIOSQLite;

    @Inject
    public DbClientImpl(StorIOSQLite storIOSQLite) {
        this.storIOSQLite = storIOSQLite;
    }

    @Override
    public void saveForecast(List<ForecastInfo> forecastList) {
        storIOSQLite
                .put()
                .objects(forecastList)
                .prepare()
                .executeAsBlocking();
    }

    @Override
    public void deleteCity(PlaceData data) {
        Integer id = data.getId();
        if (id != null) {
            storIOSQLite
                    .delete()
                    .byQuery(DeleteQuery.builder()
                            .table(ForecastsTable.TABLE)
                            .where(ForecastsTable.COLUMN_CITY_ID + "= ?")
                            .whereArgs(id)
                            .build())
                    .prepare()
                    .executeAsBlocking();
        }
        storIOSQLite
                .delete()
                .object(data)
                .prepare()
                .executeAsBlocking();
    }

    @Override
    public void addCity(PlaceData data) {
        storIOSQLite
                .put()
                .object(data)
                .prepare()
                .executeAsBlocking();
    }

    @Override
    public void getAllCities(PublishSubject<List<PlaceData>> observer) {
        storIOSQLite
                .get()
                .listOfObjects(PlaceData.class)
                .withQuery(Query.builder()
                        .table(CitiesTable.TABLE)
                        .orderBy(CitiesTable.COLUMN_ID)
                        .build())
                .prepare()
                .asRxObservable()
                .subscribe(new Observer<List<PlaceData>>() {
                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onNext(List<PlaceData> putResults) {
                        observer.onNext(putResults);
                    }

                    @Override
                    public void onCompleted() {
                    }
                });
    }

    @Override
    public void delOldForecasts(long timeInterval) {
        //forecasts before this time are not actual
        long now = Calendar.getInstance().getTime().getTime();
        storIOSQLite
                .delete()
                .byQuery(DeleteQuery.builder()
                        .table(ForecastsTable.TABLE)
                        .where("? - " + ForecastsTable.COLUMN_GET_DATE + " < ? * 1000")
                        .whereArgs(now, timeInterval)
                        .build())
                .prepare()
                .executeAsBlocking();
    }

    public void getActualWeather(Integer cityId, long timeInterval, PublishSubject<List<ForecastInfo>> observer) {
        if (cityId == null) {
            observer.onNext(new ArrayList<>());
            return;
        }
        long now = Calendar.getInstance().getTime().getTime();
        storIOSQLite
                .get()
                .listOfObjects(ForecastInfo.class)
                .withQuery(RawQuery.builder()
                        .query("SELECT * FROM " + ForecastsTable.TABLE + "  WHERE " +
                                "? - " + ForecastsTable.COLUMN_GET_DATE + " < ? * 1000 and " +
                                ForecastsTable.COLUMN_CITY_ID + " = ?")
                        .args(now, timeInterval, cityId)
                        .build()
                )
                .prepare()
                .asRxObservable()
                .subscribe(new Observer<List<ForecastInfo>>() {
                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onNext(List<ForecastInfo> putResults) {
                        observer.onNext(putResults);
                    }

                    @Override
                    public void onCompleted() {
                    }
                });
        delOldForecasts(timeInterval);

    }

}
