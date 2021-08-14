package com.example.weather;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weather.onecallmodel.Hourly;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewCreator {
    public static View createHourInformationLayout(String time, String temperature, int icon, LinearLayout linearLayout, Context context) {

        final View hourWeatherView = ((Activity) context).getLayoutInflater().inflate(R.layout.weather_information_hour, linearLayout, false);
        TextView timeView = hourWeatherView.findViewById(R.id.hour_information_time);
        ImageView iconView = hourWeatherView.findViewById(R.id.hour_information_icon);
        TextView temperatureView = hourWeatherView.findViewById(R.id.hour_information_temperature);


        timeView.setText(time);
        temperatureView.setText(temperature);
        iconView.setImageResource(icon);
        return hourWeatherView;
    }

    public static View createDayInformationLayout(int date, int temperatureDay, int temperatureNight, int icon, LinearLayout linearLayout, Context context) {
        final View dayWeatherView = ((Activity) context).getLayoutInflater().inflate(R.layout.weather_information_day, linearLayout, false);
        TextView dateView = dayWeatherView.findViewById(R.id.day_information_date);
        ImageView iconView = dayWeatherView.findViewById(R.id.day_information_icon);
        TextView temperatureDayView = dayWeatherView.findViewById(R.id.day_information_temperature_day);
        TextView temperatureNightView = dayWeatherView.findViewById(R.id.day_information_temperature_night);

        String currDate = date + " августа";

        dateView.setText(currDate);
        iconView.setImageResource(icon);
        temperatureDayView.setText(temperatureToString(temperatureDay));
        temperatureNightView.setText(temperatureToString(temperatureNight));

        return dayWeatherView;
    }

    public static void updateHourlyWeather(List<Hourly> hourlyArrayList, LinearLayout hourlyLinearLayout, int icon, Context context) {
        for (int x = 0; x < 25; x++) {
            String time = getTime(hourlyArrayList.get(x).getDt());
            String temperature = temperatureToString(hourlyArrayList.get(x).getTemp());
            hourlyLinearLayout.addView(createHourInformationLayout(time, temperature, icon, hourlyLinearLayout, context));
        }
    }

    public static void updateCurrentTemperature(double temperature, TextView currTemperature) {
        currTemperature.setText(temperatureToString(temperature));
    }

    public static void updateCurrentLocation(String location, TextView currLocation) {
        currLocation.setText(location);
    }

    public static void updateCurrentWeatherDescription(String description, TextView descriptionTextView) {
        descriptionTextView.setText(description);
    }

    public static void updateCurrentWeatherFeelsLike(double feelsTemperature, TextView feelsTemperatureTextView) {
        String feelsTemperatureString = "Ощущается как: " + temperatureToString(feelsTemperature);
        feelsTemperatureTextView.setText(feelsTemperatureString);
    }

    public static void updateSunriseTime(long time, TextView timeTextView) {
        timeTextView.setText(getTime(time));
    }

    public static void updateSunsetTime(long time, TextView timeTextView) {
        timeTextView.setText(getTime(time));
    }

    public static void updateWindSpeed(double windSpeed, TextView windSpeedTextView) {
        String windSpeedStr = Math.round(windSpeed) + " м/с";
        windSpeedTextView.setText(windSpeedStr);
    }

    public static void updatePressure(int pressure, TextView pressureTextView) {
        String pressureStr = pressureConverter(pressure) + " мм рт. ст.";
        pressureTextView.setText(pressureStr);
    }

    public static void updateHumidity(int humidity, TextView humidityTextView) {
        String humidityStr = humidity + "%";
        humidityTextView.setText(humidityStr);
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
