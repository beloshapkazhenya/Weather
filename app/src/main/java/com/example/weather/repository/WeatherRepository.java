package com.example.weather.repository;

import com.example.weather.api.WeatherAPI;
import com.example.weather.models.local.currentweatherlocal.CurrentWeatherLocalModel;
import com.example.weather.models.local.currentweatherlocal.MainLocal;
import com.example.weather.models.local.currentweatherlocal.WeatherLocal;
import com.example.weather.models.onecall.OneCallModel;

import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.realm.RealmList;

public class WeatherRepository {
    private final String LANGUAGE = "ru";
    private final String API_KEY = "d2c2a3f7b64285943d93871df271f16a";
    private final String UNITS = "metric";

    public Observable<CurrentWeatherLocalModel> getCurrent(double latitude, double longitude) {
        return WeatherAPI
                .getWeather()
                .getCurrent(latitude, longitude, API_KEY, LANGUAGE, UNITS)
                .map(currentWeatherModel -> new CurrentWeatherLocalModel(
                        currentWeatherModel
                                .getWeather()
                                .stream()
                                .map(weather -> new WeatherLocal(weather.getDescription(), weather.getIcon()))
                                .collect(
                                        Collectors.toCollection(RealmList::new)
                                ),
                        new MainLocal(currentWeatherModel.getMain().getTemp(),
                                currentWeatherModel.getMain().getFeelsLike(),
                                currentWeatherModel.getMain().getPressure(),
                                currentWeatherModel.getMain().getHumidity()),
                        currentWeatherModel.getWind().getSpeed(),
                        currentWeatherModel.getDt(),
                        currentWeatherModel.getSys().getSunrise(),
                        currentWeatherModel.getSys().getSunset(),
                        currentWeatherModel.getName()
                ));
    }

    public Observable<OneCallModel> getWeather(double latitude, double longitude) {
        String EXCLUDE = "minutely,alert,currents";
        return WeatherAPI
                .getWeather()
                .getOnecall(latitude, longitude, API_KEY, LANGUAGE, UNITS, EXCLUDE);
    }
}
