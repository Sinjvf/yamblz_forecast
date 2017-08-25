package com.example.kimichael.yamblz_forecast.data.common;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.kimichael.yamblz_forecast.data.database.ForecastsTable;
import com.example.kimichael.yamblz_forecast.data.network.forecast.response.WeatherResponse;
import com.example.kimichael.yamblz_forecast.utils.Utility;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Kim Michael on 18.07.17
 * saved forecast info
 */

@StorIOSQLiteType(table = ForecastsTable.TABLE)
public class ForecastInfo {
    @Nullable
    @StorIOSQLiteColumn(name = ForecastsTable.COLUMN_ID, key = true)
    Integer id;

    @StorIOSQLiteColumn(name = ForecastsTable.COLUMN_CITY_ID)
    int cityId;

    @StorIOSQLiteColumn(name = ForecastsTable.COLUMN_TEMPR)
    double temp;

    private double lat;
    private double lng;

    @StorIOSQLiteColumn(name = ForecastsTable.COLUMN_WEATHER_ID)
    int weatherId;

    @StorIOSQLiteColumn(name = ForecastsTable.COLUMN_WIND)
    double windSpeed;

    @StorIOSQLiteColumn(name = ForecastsTable.COLUMN_HUMIDITY)
    double humidity;

    @StorIOSQLiteColumn(name = ForecastsTable.COLUMN_PRESSURE)
    double pressure;

    @StorIOSQLiteColumn(name = ForecastsTable.COLUMN_DESCRIPTION)
    String description;

    @Nullable
    @StorIOSQLiteColumn(name = ForecastsTable.COLUMN_DATE)
    Long date;

    //time when we get this forecast
    @NonNull
    @StorIOSQLiteColumn(name = ForecastsTable.COLUMN_GET_DATE)
    Long dateGetForecast;

    public ForecastInfo(@Nullable Integer id, /*@NonNull String city,*/
                        int cityId, double temp,
                        double lat, double lng,
                        int weatherId, double windSpeed,
                        double humidity,  double pressure,
                        @NonNull String description, @Nullable Long date, @NonNull Long dateGetForecast) {

        this.id = id;
        this.cityId = cityId;
        this.temp = temp;
        this.lat = lat;
        this.lng = lng;
        this.weatherId = weatherId;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.pressure = pressure;
        this.description = description;
        this.date = date;
        this.dateGetForecast = dateGetForecast;
    }

    @NonNull
    public static ForecastInfo newForecast(@Nullable Integer id, /*@NonNull String city,*/
                                           int cityId, double temp,
                                           double lat, double lng,
                                           int weatherId, double windSpeed,
                                           double humidity, double pressure,
                                           @NonNull String description, @Nullable Long date,
                                           @NonNull Long dateGetForecast) {
        return new ForecastInfo(id, /*city,*/ cityId, temp,  lat,  lng,  weatherId, windSpeed, humidity, pressure, description, date, dateGetForecast);
    }

    @NonNull
    public static ForecastInfo newForecast(int cityId, double temp,
                                           double lat, double lng,
                                           int weatherId, double windSpeed,
                                           double humidity, double pressure,
                                           @NonNull String description, @Nullable Long date,
                                           @NonNull Long dateGetForecast) {
        return new ForecastInfo(null,/* city,*/ cityId, temp, lat, lng,  weatherId, windSpeed, humidity, pressure, description, date, dateGetForecast);
    }

    public ForecastInfo() {
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @Nullable
    public Long getDate() {
        return date;
    }

    public void setDate(@Nullable Long date) {
        this.date = date;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    @NonNull
    public Long getDateGetForecast() {
        return dateGetForecast;
    }

    public void setDateGetForecast(@NonNull Long dateGetForecast) {
        this.dateGetForecast = dateGetForecast;
    }

    public static ForecastInfo from(WeatherResponse forecast) {
        return new ForecastInfo(null,
                1,
                forecast.getTemp().getTemp(),
                forecast.getCoord().getLat(),
                forecast.getCoord().getLon(),
                forecast.getWeather().get(0).getId(),
                forecast.getWind().getSpeed(),
                forecast.getTemp().getHumidity(),
                forecast.getTemp().getPressure(),
                forecast.getWeather().get(0).getDescription(),
                Utility.parceFromStr(forecast.getDate(), Locale.getDefault()),
                Calendar.getInstance().getTimeInMillis()
        );
    }


    public static ForecastInfo from(WeatherResponse forecast, int cityId) {
        return new ForecastInfo(null,
                cityId,
                forecast.getTemp().getTemp(),
                forecast.getCoord().getLat(),
                forecast.getCoord().getLon(),
                forecast.getWeather().get(0).getId(),
                forecast.getWind().getSpeed(),
                forecast.getTemp().getHumidity(),
                forecast.getTemp().getPressure(),
                forecast.getWeather().get(0).getDescription(),
                Utility.parceFromStr(forecast.getDate(), Locale.getDefault()),
                Calendar.getInstance().getTimeInMillis()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ForecastInfo info = (ForecastInfo) o;

        if (id != null ? !id.equals(info.id) : info.id != null) return false;
        if (cityId != info.cityId) return false;
        return date != null && date.equals(info.date);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + cityId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ForecastInfo{" +
                "id=" + id +
                ", cityId=" + cityId +
                ", temp=" + temp +
                ", weatherId=" + weatherId +
                ", windSpeed=" + windSpeed +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", dateGetForecast=" + dateGetForecast +
                '}';
    }
}
