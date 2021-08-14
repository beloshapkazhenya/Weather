package com.example.weather;


import com.example.weather.currentweathermodel.CurrentWeatherModel;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrentWeatherCore {
    @GET("/data/2.5/weather")
    Call<CurrentWeatherModel> getData(@Query("lat") double latitude, @Query(("lon")) double longitude, @Query("appid") String apiKey, @Query("lang") String lang, @Query("units") String units);
}

