package com.example.weather.repository;

import com.example.weather.api.WeatherAPI;
import com.example.weather.models.local.currentweatherlocal.CurrentWeatherLocalModel;
import com.example.weather.models.local.currentweatherlocal.MainLocal;
import com.example.weather.models.local.currentweatherlocal.WeatherLocal;
import com.example.weather.models.local.onecalllocal.DayLocal;
import com.example.weather.models.local.onecalllocal.HourLocal;
import com.example.weather.models.local.onecalllocal.OneCallLocalModel;

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
                                .map(weather -> new WeatherLocal(
                                        weather.getDescription(),
                                        weather.getIcon()
                                ))
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

    public Observable<OneCallLocalModel> getWeather(double latitude, double longitude) {
        String EXCLUDE = "minutely,alert,currents";
        return WeatherAPI
                .getWeather()
                .getOnecall(latitude, longitude, API_KEY, LANGUAGE, UNITS, EXCLUDE)
                .map(oneCallModel -> new OneCallLocalModel(
                        oneCallModel
                                .getHourly()
                                .stream()
                                .map(hourly -> new HourLocal(
                                        hourly.getDt(),
                                        hourly.getTemp(),
                                        hourly.getWeather().get(0).getIcon()
                                ))
                                .collect(
                                        Collectors.toCollection(RealmList::new)
                                ),
                        oneCallModel
                                .getDaily()
                                .stream()
                                .map(daily -> new DayLocal(
                                        daily.getDt(),
                                        daily.getWeather().get(0).getIcon(),
                                        daily.getTemp().getDay(),
                                        daily.getTemp().getNight()
                                ))
                                .collect(
                                        Collectors.toCollection(RealmList::new)
                                )
                ));
    }
}
