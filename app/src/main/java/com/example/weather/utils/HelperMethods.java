package com.example.weather.utils;

import android.content.Context;

import com.example.weather.R;
import com.example.weather.models.DayWeather;
import com.example.weather.models.HourWeather;
import com.example.weather.models.local.onecalllocal.DayLocal;
import com.example.weather.models.local.onecalllocal.HourLocal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HelperMethods {

    private static final String PATTERN_TIME = "HH:mm";
    private static final String PATTERN_DATE_SHORT = "dd MMM";
    private static final String PATTERN_DATE_FULL = "dd MMMM";
    private static final String TIME_ZONE = "GMT+3";

    public static String pressureToString(int pressure, Context context) {
        return Math.round((pressure / 1.333)) + context.getString(R.string.pressure_units);
    }

    public static String windSpeedToString(double windSpeed, Context context) {
        return Math.round(windSpeed) + context.getString(R.string.wind_speed_units);
    }

    public static String getTime(long decimal) {
        Date date = new java.util.Date(decimal * 1000L);

        SimpleDateFormat sdf = new java.text.SimpleDateFormat(PATTERN_TIME, Locale.ENGLISH);
        sdf.setTimeZone(java.util.TimeZone.getTimeZone(TIME_ZONE));

        return sdf.format(date);
    }

    public static String getShortDate(long decimal) {
        Date date = new java.util.Date(decimal * 1000L);

        SimpleDateFormat sdf = new java.text.SimpleDateFormat(PATTERN_DATE_SHORT, Locale.getDefault());
        sdf.setTimeZone(java.util.TimeZone.getTimeZone(TIME_ZONE));

        return sdf.format(date);
    }

    public static String getFullDate(long decimal) {
        Date date = new java.util.Date(decimal * 1000L);

        SimpleDateFormat sdf = new java.text.SimpleDateFormat(PATTERN_DATE_FULL, Locale.getDefault());
        sdf.setTimeZone(java.util.TimeZone.getTimeZone(TIME_ZONE));

        return sdf.format(date);
    }

    public static String temperatureToString(double temperature, Context context) {
        int intTemperature = (int) Math.round(temperature);

        if (intTemperature <= 0) {
            return intTemperature + context.getString(R.string.temperature_units);
        } else {
            return "+" + intTemperature + context.getString(R.string.temperature_units);
        }

    }

    public static ArrayList<HourWeather> createHourWeatherList(List<HourLocal> list, Context context) {
        ArrayList<HourWeather> hourWeathers = new ArrayList<>();

        for (int i = 0; i < list.toArray().length; i++) {
            hourWeathers.add(new HourWeather(
                    HelperMethods.getTime(list.get(i).getDt()),
                    HelperMethods.getShortDate(list.get(i).getDt()),
                    list.get(i).getIconId(),
                    HelperMethods.temperatureToString(list.get(i).getTemperature(), context)
            ));
        }

        return hourWeathers;
    }

    public static ArrayList<DayWeather> createDayWeatherList(List<DayLocal> list, Context context) {
        ArrayList<DayWeather> dayWeathers = new ArrayList<>();

        for (int i = 0; i < list.toArray().length; i++) {
            dayWeathers.add(new DayWeather(
                    HelperMethods.getFullDate(list.get(i).getDt()),
                    list.get(i).getIconId(),
                    HelperMethods.temperatureToString(list.get(i).getTemperatureDay(), context),
                    HelperMethods.temperatureToString(list.get(i).getTemperatureNight(), context)
            ));
        }

        return dayWeathers;
    }

}