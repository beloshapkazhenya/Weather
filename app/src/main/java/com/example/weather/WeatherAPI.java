package com.example.weather;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WeatherAPI extends Application {
    private static CurrentWeatherCore currentWeatherCore;
    private static OnecallWeatherCore onecallWeatherCore;
    private static final String BASE_URL = "https://api.openweathermap.org/";


    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        currentWeatherCore = retrofit.create(CurrentWeatherCore.class);
        onecallWeatherCore = retrofit.create(OnecallWeatherCore.class);
    }

    public static CurrentWeatherCore getCurrentWeather() {
        return currentWeatherCore;
    }

    public static OnecallWeatherCore getWeatherCore() {
        return onecallWeatherCore;
    }
}
