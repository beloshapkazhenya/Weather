package com.example.weather.api;

import android.app.Application;

import com.example.weather.service.WeatherCore;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherAPI extends Application {
    private static WeatherCore weatherCore;
    private static final String BASE_URL = "https://api.openweathermap.org/";

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherCore = retrofit.create(WeatherCore.class);
    }

    public static WeatherCore getWeather() {
        return weatherCore;
    }

}
