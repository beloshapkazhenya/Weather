package com.example.weather.models;

public class DayWeather {
    private final String currDate;
    private final String currIconId;
    private final String currDayTemperature;
    private final String currNightTemperature;

    public DayWeather(String date, String iconId, String dayTemperature, String nightTemperature) {
        currDate = date;
        currIconId = iconId;
        currDayTemperature = dayTemperature;
        currNightTemperature = nightTemperature;
    }

    public String getCurrDate() {
        return currDate;
    }

    public String getCurrIconId() {
        return currIconId;
    }

    public String getCurrDayTemperature() {
        return currDayTemperature;
    }

    public String getCurrNightTemperature() {
        return currNightTemperature;
    }
}
