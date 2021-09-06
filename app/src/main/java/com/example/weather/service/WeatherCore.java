package com.example.weather.service;

import com.example.weather.models.currentweather.CurrentWeatherResponse;
import com.example.weather.models.onecall.OneCallResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherCore {
    @GET("/data/2.5/weather")
    Observable<CurrentWeatherResponse> getCurrent(
            @Query("lat") double latitude,
            @Query(("lon")) double longitude,
            @Query("appid") String apiKey,
            @Query("lang") String lang,
            @Query("units") String units
    );

    @GET("/data/2.5/onecall")
    Observable<OneCallResponse> getOnecall(
            @Query("lat") double latitude,
            @Query(("lon")) double longitude,
            @Query("appid") String apiKey,
            @Query("lang") String lang,
            @Query("units") String units,
            @Query("exclude") String exclude
    );
}

