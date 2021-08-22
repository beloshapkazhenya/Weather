package com.example.weather.repository;


import com.example.weather.api.WeatherAPI;
import com.example.weather.models.currentweather.CurrentWeatherModel;
import com.example.weather.models.onecall.OneCallModel;

import io.reactivex.Observable;

public class WeatherRepository {
    private final String LANGUAGE = "ru";
    private final String API_KEY = "d2c2a3f7b64285943d93871df271f16a";
    private final String UNITS = "metric";

    public Observable<CurrentWeatherModel> getCurrent(double latitude, double longitude) {
        return WeatherAPI
                .getWeather()
                .getCurrent(latitude, longitude, API_KEY, LANGUAGE, UNITS);
    }

    public Observable<OneCallModel> getWeather(double latitude, double longitude) {
        String EXCLUDE = "minutely,alert,currents";
        return WeatherAPI
                .getWeather()
                .getOnecall(latitude, longitude, API_KEY, LANGUAGE, UNITS, EXCLUDE);
    }
}
