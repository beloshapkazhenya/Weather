package com.example.weather;

import com.example.weather.onecallmodel.OneCallModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OnecallWeatherCore {
    @GET("/data/2.5/onecall")
    Call<OneCallModel> getData(@Query("lat") double latitude, @Query(("lon")) double longitude, @Query("appid") String apiKey, @Query("lang") String lang, @Query("units") String units, @Query("exclude") String exclude);
}
