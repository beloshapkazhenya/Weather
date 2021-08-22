package com.example.weather.models.realmweather;

import io.realm.RealmObject;

public class RealmWeather extends RealmObject {
    private String locationTitle;
    private double currentTemperature;
    private String currentWeatherDescription;
    private double currentWeatherFeelsLike;
    private long sunrise;
    private long sunset;
    private String currentWeatherIconId;

    public void setLocationTitle(String locationTitle) {
        this.locationTitle = locationTitle;
    }

    public String getLocationTitle() {
        return locationTitle;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setCurrentTemperature(double temperature) {
        this.currentTemperature = temperature;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public long getSunset() {
        return sunset;
    }

    public void setCurrentWeatherDescription(String description) {
        this.currentWeatherDescription = description;
    }

    public String getCurrentWeatherDescription() {
        return currentWeatherDescription;
    }

    public void setCurrentWeatherFeelsLike(double temperature) {
        this.currentWeatherFeelsLike = temperature;
    }

    public double getCurrentWeatherFeelsLike() {
        return currentWeatherFeelsLike;
    }

    public void setCurrentWeatherIconId(String iconId) {
        this.currentWeatherIconId = iconId;
    }

    public String getCurrentWeatherIconId() {
        return currentWeatherIconId;
    }
}