package com.example.kimichael.yamblz_forecast.domain.interactor.forecast;

import com.example.kimichael.yamblz_forecast.data.network.forecast.response.Forecast;

/**
 * Created by Kim Michael on 18.07.17
 */
public class ForecastInfo {

    private final String city;
    private final double temp;
    private final double minTemp;
    private final double maxTemp;
    private final int weatherId;
    private final double windSpeed;
    private final double humidity;
    private final double pressure;
    private final String description;

    public ForecastInfo(String city, double temp, double minTemp, double maxTemp, int weatherId, double windSpeed, double humidity, double pressure, String description) {
        this.city = city;
        this.temp = temp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.weatherId = weatherId;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.pressure = pressure;
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public double getTemp() {
        return temp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getHumidity() {
        return humidity;
    }

    public String getDescription() {
        return description;
    }

    public double getPressure() {
        return pressure;
    }

    public static ForecastInfo from(Forecast forecast) {
        return new ForecastInfo(
                forecast.getName(),
                forecast.getTemp().getTemp(),
                forecast.getTemp().getTempMin(),
                forecast.getTemp().getTempMax(),
                forecast.getWeather().get(0).getId(),
                forecast.getWind().getSpeed(),
                forecast.getTemp().getHumidity(),
                forecast.getTemp().getPressure(),
                forecast.getWeather().get(0).getDescription()
        );

    }
}
