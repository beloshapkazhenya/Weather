package com.example.weather;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weather.currentweathermodel.CurrentWeatherModel;
import com.example.weather.onecallmodel.Daily;
import com.example.weather.onecallmodel.Hourly;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Response;

public class ViewCreator {
    private static View createHourInformationLayout(String time, String temperature, String iconId, LinearLayout linearLayout, Context context) {
        final View hourWeatherView = ((Activity) context).getLayoutInflater().inflate(R.layout.weather_information_hour, linearLayout, false);
        TextView timeView = hourWeatherView.findViewById(R.id.hour_information_time);
        ImageView iconView = hourWeatherView.findViewById(R.id.hour_information_icon);
        TextView temperatureView = hourWeatherView.findViewById(R.id.hour_information_temperature);
        timeView.setText(time);
        temperatureView.setText(temperature);
        updateIcon(iconId, iconView);
        return hourWeatherView;
    }

    private static View createDayInformationLayout(String date, String temperatureDay, String temperatureNight, String iconId, LinearLayout linearLayout, Context context) {
        final View dayWeatherView = ((Activity) context).getLayoutInflater().inflate(R.layout.weather_information_day, linearLayout, false);
        TextView dateView = dayWeatherView.findViewById(R.id.day_information_date);
        ImageView iconView = dayWeatherView.findViewById(R.id.day_information_icon);
        TextView temperatureDayView = dayWeatherView.findViewById(R.id.day_information_temperature_day);
        TextView temperatureNightView = dayWeatherView.findViewById(R.id.day_information_temperature_night);

        dateView.setText(date);
        updateIcon(iconId, iconView);
        temperatureDayView.setText(temperatureDay);
        temperatureNightView.setText(temperatureNight);

        return dayWeatherView;
    }

    public static void updateCurrentWeather(Response<CurrentWeatherModel> currentWeather, LinearLayout currentWeatherLinearLayout, LinearLayout currentWeatherParametersLayout) {
        TextView currentTemperatureView = currentWeatherLinearLayout.findViewById(R.id.current_temperature);
        TextView currentSunriseView = currentWeatherLinearLayout.findViewById(R.id.sunrise_time);
        TextView currentSunsetView = currentWeatherLinearLayout.findViewById(R.id.sunset_time);
        TextView currentTemperatureFeelsLikeView = currentWeatherLinearLayout.findViewById(R.id.current_weather_feels_like);
        TextView currentWeatherDescriptionView = currentWeatherLinearLayout.findViewById(R.id.current_weather_description);
        ImageView currentWeatherIcon = currentWeatherLinearLayout.findViewById(R.id.current_weather_icon);

        TextView windSpeedView = currentWeatherParametersLayout.findViewById(R.id.wind_speed);
        TextView pressureView = currentWeatherParametersLayout.findViewById(R.id.pressure);
        TextView humidityView = currentWeatherParametersLayout.findViewById(R.id.humidity);

        double currentTemperature = currentWeather.body().getMain().getTemp();
        long sunrise = currentWeather.body().getSys().getSunrise();
        long sunset = currentWeather.body().getSys().getSunset();
        double currentTemperatureFeelsLike = currentWeather.body().getMain().getFeelsLike();
        double windSpeed = currentWeather.body().getWind().getSpeed();
        int pressure = currentWeather.body().getMain().getPressure();
        int humidity = currentWeather.body().getMain().getHumidity();
        String iconId = currentWeather.body().getWeather().get(0).getIcon();
        String currentWeatherDescription = currentWeather.body().getWeather().get(0).getDescription();
        updateCurrentTemperature(currentTemperature, currentTemperatureView);
        updateSunriseTime(sunrise, currentSunriseView);
        updateSunsetTime(sunset, currentSunsetView);
        updateCurrentWeatherFeelsLike(currentTemperatureFeelsLike, currentTemperatureFeelsLikeView);
        updateWindSpeed(windSpeed, windSpeedView);
        updatePressure(pressure, pressureView);
        updateHumidity(humidity, humidityView);
        updateCurrentWeatherDescription(currentWeatherDescription, currentWeatherDescriptionView);
        updateIcon(iconId, currentWeatherIcon);
    }

    public static void updateHourlyWeather(List<Hourly> hourlyArrayList, LinearLayout hourlyLinearLayout, Context context) {
        for (int x = 1; x < 26; x++) {
            String time = getTime(hourlyArrayList.get(x).getDt());
            String temperature = temperatureToString(hourlyArrayList.get(x).getTemp());
            String iconId = hourlyArrayList.get(x).getWeather().get(0).getIcon();
            hourlyLinearLayout.addView(createHourInformationLayout(time, temperature, iconId, hourlyLinearLayout, context));
        }
    }

    public static void updateDailyWeather(List<Daily> dailyList, LinearLayout dailyLinearLayout, Context context) {
        for (int x = 0; x < 4; x++) {
            String date = getDate(dailyList.get(x).getDt());
            String temperatureDay = temperatureToString(dailyList.get(x).getTemp().getDay());
            String temperatureNight = temperatureToString(dailyList.get(x).getTemp().getNight());
            String iconId = dailyList.get(x).getWeather().get(0).getIcon();
            dailyLinearLayout.addView(createDayInformationLayout(date, temperatureDay, temperatureNight, iconId, dailyLinearLayout, context));
        }
    }

    private static void updateCurrentTemperature(double temperature, TextView currTemperature) {
        currTemperature.setText(temperatureToString(temperature));
    }

    public static void updateCurrentLocation(String location, TextView currLocation) {
        currLocation.setText(location);
    }

    private static void updateCurrentWeatherDescription(String description, TextView descriptionTextView) {
        descriptionTextView.setText(description);
    }

    private static void updateCurrentWeatherFeelsLike(double feelsTemperature, TextView feelsTemperatureTextView) {
        String feelsTemperatureString = "Ощущается как: " + temperatureToString(feelsTemperature);
        feelsTemperatureTextView.setText(feelsTemperatureString);
    }

    private static void updateSunriseTime(long time, TextView timeTextView) {
        timeTextView.setText(getTime(time));
    }

    private static void updateSunsetTime(long time, TextView timeTextView) {
        timeTextView.setText(getTime(time));
    }

    private static void updateWindSpeed(double windSpeed, TextView windSpeedTextView) {
        String windSpeedStr = Math.round(windSpeed) + " м/с";
        windSpeedTextView.setText(windSpeedStr);
    }

    private static void updatePressure(int pressure, TextView pressureTextView) {
        String pressureStr = pressureConverter(pressure) + " мм рт. ст.";
        pressureTextView.setText(pressureStr);
    }

    private static void updateHumidity(int humidity, TextView humidityTextView) {
        String humidityStr = humidity + "%";
        humidityTextView.setText(humidityStr);
    }

    private static void updateIcon(String iconId, ImageView iconImageView) {
        String path = "https://openweathermap.org/img/wn/" + iconId + "@2x.png";
        Picasso.get().load(path).error(R.drawable.unknown).placeholder(R.drawable.unknown).into(iconImageView);
    }

    private static int pressureConverter(int pressure) {
        return (int) Math.round((pressure / 1.333));
    }

    private static String getTime(long decimal) {
        Date date = new java.util.Date(decimal * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm", Locale.ENGLISH);
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+3"));
        return sdf.format(date);
    }

    private static String getDate(long decimal) {
        Date date = new java.util.Date(decimal * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMMM", Locale.getDefault());
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+3"));
        return sdf.format(date);
    }

    private static String temperatureToString(double temperature) {
        String temperatureString;
        int intTemperature = (int) Math.round(temperature);
        if (intTemperature < 0) {
            temperatureString = intTemperature + "ºC";
        } else if (intTemperature == 0) {
            temperatureString = intTemperature + "ºC";
        } else {
            temperatureString = "+" + intTemperature + "ºC";
        }
        return temperatureString;
    }
}