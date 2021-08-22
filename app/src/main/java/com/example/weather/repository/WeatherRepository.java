package com.example.weather.repository;


import com.example.weather.api.WeatherAPI;
import com.example.weather.models.onecall.OneCallModel;

import io.reactivex.Observable;

public class WeatherRepository {
    private final String LANGUAGE = "ru";
    private final String API_KEY = "d2c2a3f7b64285943d93871df271f16a";
    private final String UNITS = "metric";
    private final String EXCLUDE = "minutely,alert,currents";


 //TODO Методы должны возвращать response в виде List<Type>

//    public void getCurrentWeather(double latitude, double longitude) {
//        WeatherAPI
//                .getWeather()
//                .getCurrent(latitude, longitude, API_KEY, LANGUAGE, UNITS)
//                .subscribe(new Subscriber<Response<CurrentWeatherModel>>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Response<CurrentWeatherModel> response) {
//
//                    }
//                });
//    }
//
    public Observable<OneCallModel> getWeather(double latitude, double longitude) {
        return WeatherAPI
                .getWeather()
                .getOnecall(latitude, longitude, API_KEY, LANGUAGE, UNITS, EXCLUDE);
    }
}
