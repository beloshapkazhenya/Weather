package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.currentweathermodel.CurrentWeatherModel;
import com.example.weather.currentweathermodel.Weather;
import com.example.weather.onecallmodel.Daily;
import com.example.weather.onecallmodel.Hourly;
import com.example.weather.onecallmodel.OneCallModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final double LATITUDE = 53.1384;
    private final double LONGITUDE = 29.2214;
    private final String LANGUAGE = "ru";
    private static final String API_KEY = "d2c2a3f7b64285943d93871df271f16a";
    private static final String UNITS = "metric";
    private final String EXCLUDE = "minutely,alert,currents";


    TextView currLocationView;
    TextView currTemperatureView;
    TextView currWeatherDescriptionView;
    TextView currWeatherFeelsView;
    LinearLayout currentWeatherView;
    TextView sunriseView;
    TextView sunsetView;
    TextView windSpeedView;
    TextView pressureView;
    TextView humidityView;
    LinearLayout hourInformationLayout;
    int currentIcon;

    Context appContext;

    String currentLocation;
    double currentTemperature;
    long sunrise;
    long sunset;
    String currentWeatherDescription;
    double currentTemperatureFeelsLike;
    double windSpeed;
    int pressure;
    int humidity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appContext = MainActivity.this;

        currLocationView = findViewById(R.id.current_location);
        currTemperatureView = findViewById(R.id.current_temperature);
        currWeatherDescriptionView = findViewById(R.id.current_weather_description);
        currWeatherFeelsView = findViewById(R.id.current_weather_feels_like);
        currentWeatherView = findViewById(R.id.current_weather);
        sunriseView = findViewById(R.id.sunrise_time);
        sunsetView = findViewById(R.id.sunset_time);
        windSpeedView = findViewById(R.id.wind_speed);
        pressureView = findViewById(R.id.pressure);
        humidityView = findViewById(R.id.humidity);

        hourInformationLayout = findViewById(R.id.hour_information_layout);
        currentIcon = R.drawable.test_icon;
        getCurrentWeather();
        getWeather();

    }

    public void getCurrentWeather() {
        WeatherAPI.getCurrentWeather().getData(LATITUDE, LONGITUDE, API_KEY, LANGUAGE, UNITS).enqueue(new Callback<CurrentWeatherModel>() {
            @Override
            public void onResponse(@NonNull Call<CurrentWeatherModel> call, @NonNull Response<CurrentWeatherModel> response) {
                if (response.body() != null) {
                    currentLocation = response.body().getName();
                    currentTemperature = response.body().getMain().getTemp();
                    currentTemperatureFeelsLike = response.body().getMain().getFeelsLike();
                    sunrise = response.body().getSys().getSunrise();
                    sunset = response.body().getSys().getSunset();
                    List<Weather> weather = new ArrayList<>(response.body().getWeather());
                    currentWeatherDescription = weather.get(0).getDescription();
                    windSpeed = response.body().getWind().getSpeed();
                    pressure = response.body().getMain().getPressure();
                    humidity = response.body().getMain().getHumidity();
                    ViewCreator.updateCurrentTemperature(currentTemperature, currTemperatureView);
                    ViewCreator.updateCurrentLocation(currentLocation, currLocationView);
                    ViewCreator.updateCurrentWeatherDescription(currentWeatherDescription, currWeatherDescriptionView);
                    ViewCreator.updateCurrentWeatherFeelsLike(currentTemperatureFeelsLike, currWeatherFeelsView);
                    ViewCreator.updateSunriseTime(sunrise, sunriseView);
                    ViewCreator.updateSunsetTime(sunset, sunsetView);
                    ViewCreator.updateWindSpeed(windSpeed, windSpeedView);
                    ViewCreator.updatePressure(pressure, pressureView);
                    ViewCreator.updateHumidity(humidity, humidityView);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CurrentWeatherModel> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Ошибка: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getWeather() {
        WeatherAPI.getWeatherCore().getData(LATITUDE, LONGITUDE, API_KEY, LANGUAGE, UNITS, EXCLUDE).enqueue(new Callback<OneCallModel>() {
            @Override
            public void onResponse(@NonNull Call<OneCallModel> call, @NonNull Response<OneCallModel> response) {
                if (response.body() != null) {
                    List<Hourly> hourlyList = new ArrayList<>(response.body().getHourly());
                    List<Daily> dailyList = new ArrayList<>(response.body().getDaily());
                    ViewCreator.updateHourlyWeather(hourlyList, hourInformationLayout, currentIcon, appContext);
                }
            }

            @Override
            public void onFailure(@NonNull Call<OneCallModel> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Ошибка: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}