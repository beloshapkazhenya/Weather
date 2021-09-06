package com.example.weather.repository;

import com.example.weather.api.WeatherAPI;
import com.example.weather.models.local.currentweatherlocal.CurrentWeatherLocal;
import com.example.weather.models.local.currentweatherlocal.MainLocal;
import com.example.weather.models.local.currentweatherlocal.WeatherLocal;
import com.example.weather.models.local.onecalllocal.DayLocal;
import com.example.weather.models.local.onecalllocal.HourLocal;
import com.example.weather.models.local.onecalllocal.OnecallLocal;

import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.realm.RealmList;

public class WeatherRepository {
    private final String LANGUAGE = "ru";
    private final String API_KEY = "d2c2a3f7b64285943d93871df271f16a";
    private final String UNITS = "metric";

    public Observable<CurrentWeatherLocal> getCurrent(double latitude, double longitude) {
        return WeatherAPI
                .getWeather()
                .getCurrent(latitude, longitude, API_KEY, LANGUAGE, UNITS)
                .map(currentWeatherResponse -> new CurrentWeatherLocal(
                        currentWeatherResponse
                                .getWeather()
                                .stream()
                                .map(weather -> new WeatherLocal(
                                        weather.getDescription(),
                                        weather.getIcon()
                                ))
                                .collect(
                                        Collectors.toCollection(RealmList::new)
                                ),
                        new MainLocal(currentWeatherResponse.getMain().getTemp(),
                                currentWeatherResponse.getMain().getFeelsLike(),
                                currentWeatherResponse.getMain().getPressure(),
                                currentWeatherResponse.getMain().getHumidity()),
                        currentWeatherResponse.getWind().getSpeed(),
                        currentWeatherResponse.getDt(),
                        currentWeatherResponse.getSys().getSunrise(),
                        currentWeatherResponse.getSys().getSunset(),
                        currentWeatherResponse.getName()
                ));
    }

    public Observable<OnecallLocal> getWeather(double latitude, double longitude) {
        String EXCLUDE = "minutely,alert,currents";
        return WeatherAPI
                .getWeather()
                .getOnecall(latitude, longitude, API_KEY, LANGUAGE, UNITS, EXCLUDE)
                .map(oneCallResponse -> new OnecallLocal(
                        oneCallResponse
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
                        oneCallResponse
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
