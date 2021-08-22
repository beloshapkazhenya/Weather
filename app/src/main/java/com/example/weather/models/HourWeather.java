package com.example.weather.models;

public class HourWeather {
    private final String currHour;
    private final String currDate;
    private final String hourIconId;
    private final String hourTemperature;

    public HourWeather(String hour, String date, String iconId, String temperature){
        currHour = hour;
        currDate = date;
        hourIconId = iconId;
        hourTemperature = temperature;
    }

    public String getCurrHour(){
        return currHour;
    }

    public String getCurrDate(){
        return currDate;
    }

    public String getHourIconId(){
        return hourIconId;
    }

    public String getHourTemperature(){
        return hourTemperature;
    }
}
