package com.example.weather.utils;

import com.example.weather.models.DayWeather;
import com.example.weather.models.HourWeather;
import com.example.weather.models.onecall.Daily;
import com.example.weather.models.onecall.Hourly;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HelperMethods {

    public static String pressureToString(int pressure) {
        return Math.round((pressure / 1.333)) + " мм.рт.ст.";
    }

    public static String windSpeedToString(double windSpeed) {
        return Math.round(windSpeed) + " м/с";
    }

    public static String getTime(long decimal) {
        Date date = new java.util.Date(decimal * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm", Locale.ENGLISH);
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+3"));
        return sdf.format(date);
    }

    public static String getDate(long decimal) {
        Date date = new java.util.Date(decimal * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMM", Locale.getDefault());
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+3"));
        return sdf.format(date);
    }

    public static String temperatureToString(double temperature) {
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

    public static ArrayList<HourWeather> createHourWeatherList(List<Hourly> list) {
        ArrayList<HourWeather> hourWeathers = new ArrayList<>();
        for (int i = 0; i < list.toArray().length; i++) {
            hourWeathers.add(new HourWeather(
                    HelperMethods.getTime(list.get(i).getDt()),
                    HelperMethods.getDate(list.get(i).getDt()),
                    list.get(i).getWeather().get(0).getIcon(),
                    HelperMethods.temperatureToString(list.get(i).getTemp())
            ));
        }
        return hourWeathers;
    }

    public static ArrayList<DayWeather> createDayWeatherList(List<Daily> list) {
        ArrayList<DayWeather> dayWeathers = new ArrayList<>();
        for (int i = 0; i < list.toArray().length; i++) {
            dayWeathers.add(new DayWeather(
                    HelperMethods.getDate(list.get(i).getDt()),
                    list.get(i).getWeather().get(0).getIcon(),
                    HelperMethods.temperatureToString(list.get(i).getTemp().getDay()),
                    HelperMethods.temperatureToString(list.get(i).getTemp().getNight())
            ));
        }
        return dayWeathers;
    }

}