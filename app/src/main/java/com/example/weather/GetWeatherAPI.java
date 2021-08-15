package com.example.weather;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.weather.currentweathermodel.CurrentWeatherModel;
import com.example.weather.onecallmodel.Daily;
import com.example.weather.onecallmodel.Hourly;
import com.example.weather.onecallmodel.OneCallModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetWeatherAPI {
    private static final String LANGUAGE = "ru";
    private static final String API_KEY = "d2c2a3f7b64285943d93871df271f16a";
    private static final String UNITS = "metric";
    private static final String EXCLUDE = "minutely,alert,currents";


    public static void getCurrentWeather(double latitude, double longitude, LinearLayout currentWeatherView, LinearLayout currentWeatherParametersView, TextView currLocationView, Context context) {
        WeatherAPI.getCurrentWeather().getData(latitude, longitude, API_KEY, LANGUAGE, UNITS).enqueue(new Callback<CurrentWeatherModel>() {
            @Override
            public void onResponse(@NonNull Call<CurrentWeatherModel> call, @NonNull Response<CurrentWeatherModel> response) {
                if (response.body() != null) {
                    String currentLocation = response.body().getName();
                    ViewCreator.updateCurrentWeather(response, currentWeatherView, currentWeatherParametersView);
                    ViewCreator.updateCurrentLocation(currentLocation, currLocationView);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CurrentWeatherModel> call, @NonNull Throwable t) {
                Toast.makeText(context, "Ошибка: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getWeather(double latitude, double longitude, LinearLayout hourInformationLayout, LinearLayout dailyLinearLayout, Context context) {
        WeatherAPI.getWeatherCore().getData(latitude, longitude, API_KEY, LANGUAGE, UNITS, EXCLUDE).enqueue(new Callback<OneCallModel>() {
            @Override
            public void onResponse(@NonNull Call<OneCallModel> call, @NonNull Response<OneCallModel> response) {
                if (response.body() != null) {
                    List<Hourly> hourlyList = new ArrayList<>(response.body().getHourly());
                    List<Daily> dailyList = new ArrayList<>(response.body().getDaily());
                    ViewCreator.updateHourlyWeather(hourlyList, hourInformationLayout, context);
                    ViewCreator.updateDailyWeather(dailyList, dailyLinearLayout, context);
                }
            }

            @Override
            public void onFailure(@NonNull Call<OneCallModel> call, @NonNull Throwable t) {
                Toast.makeText(context, "Ошибка: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
